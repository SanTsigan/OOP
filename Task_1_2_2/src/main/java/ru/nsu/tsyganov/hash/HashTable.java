package ru.nsu.tsyganov.hash;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * HashTable class.
 *
 * @param <K> for keys.
 * @param <V> for values.
 */
public class HashTable<K, V> implements Iterable<HashTable.Entry<K, V>> {
    private Entry<K, V>[] table;
    private int size;
    private int capacity;
    private int threshold;
    private final float loadFactor;
    private int modCount;

    /**
     * Constructor.
     */
    public HashTable() {
        this.capacity = 4;
        this.loadFactor = 0.75f;
        this.threshold = (int) (capacity * loadFactor);
        this.table = new Entry[capacity];
        this.size = 0;
        this.modCount = 0;
    }

    static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Put value at key in hashtable.
     */
    public void put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }

        if (size >= threshold) {
            resize();
        }

        int index = getIndex(key);
        while (table[index] != null) {
            if (table[index].key.equals(key)) {
                table[index].value = value;
                return;
            }
            index = (index + 1) % capacity;
        }
        table[index] = new Entry<>(key, value);
        size++;
        modCount++;
    }

    public int getSize() {
        return size;
    }

    public V get(K key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }

        int index = getIndex(key);
        while (table[index] != null) {
            if (table[index].key.equals(key)) {
                return table[index].value;
            }
            index = (index + 1) % capacity;
        }
        return null; // ключ не найден
    }

    /**
     * Remove value at key.
     */
    public V remove(K key) {
        V rvalue;
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }

        int index = getIndex(key);
        while (table[index] != null) {
            if (table[index].key.equals(key)) {
                rvalue = table[index].value;
                table[index] = null;
                size--;
                modCount++;
                rehash(index);
                return rvalue;
            }
            index = (index + 1) % capacity;
        }
        return null;
    }

    public void update(K key, V value) {
        put(key, value);
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    private void resize() {
        capacity *= 2;
        threshold = (int) (capacity * loadFactor);
        Entry<K, V>[] oldTable = table;
        table = new Entry[capacity];
        size = 0;

        for (Entry<K, V> entry : oldTable) {
            if (entry != null) {
                put(entry.key, entry.value);
            }
        }
    }

    private void rehash(int emptyIndex) {
        int index = (emptyIndex + 1) % capacity;
        while (table[index] != null) {
            Entry<K, V> entryToRehash = table[index];
            table[index] = null;
            size--;
            put(entryToRehash.key, entryToRehash.value);
            index = (index + 1) % capacity;
        }
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {
            private int currentIndex = 0;
            private int lastReturnedIndex = -1;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                while (currentIndex < capacity && table[currentIndex] == null) {
                    currentIndex++;
                }
                return currentIndex < capacity;
            }

            @Override
            public Entry<K, V> next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                lastReturnedIndex = currentIndex;
                Entry<K, V> entry = table[currentIndex];
                currentIndex++;
                return entry;
            }

            @Override
            public void remove() {
                if (lastReturnedIndex == -1) {
                    throw new IllegalStateException("Next must be called before remove");
                }

                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }

                HashTable.this.remove(table[lastReturnedIndex].key);
                lastReturnedIndex = -1;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Entry<K, V> entry : this) {
            sb.append(entry.key).append("=").append(entry.value).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HashTable<?, ?>)) {
            return false;
        }

        HashTable<K, V> other = (HashTable<K, V>) obj;

        if (this.size != other.size) {
            return false;
        }

        for (Entry<K, V> entry : this) {
            V otherValue = other.get(entry.key);
            if (!Objects.equals(entry.value, otherValue)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        for (Entry<K, V> entry : this) {
            hash = 31 * hash + (entry.key == null ? 0 : entry.key.hashCode());
            hash = 31 * hash + (entry.value == null ? 0 : entry.value.hashCode());
        }
        return hash;
    }
}
