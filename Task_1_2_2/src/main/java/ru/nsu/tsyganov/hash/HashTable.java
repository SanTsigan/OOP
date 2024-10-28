package ru.nsu.tsyganov.hash;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashTable<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Entry<K, V>[] table;
    private int size;
    private int threshold;
    private int modCount;

    public HashTable() {
        table = new Entry[INITIAL_CAPACITY];
        threshold = (int) (INITIAL_CAPACITY * LOAD_FACTOR);
        size = 0;
        modCount = 0;
    }

    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private int hash(K key) {
        return key == null ? 0 : (key.hashCode() & 0x7FFFFFFF) % table.length;
    }

    public void put(K key, V value) {
        if (size >= threshold) {
            resize();
        }
        int index = hash(key);
        Entry<K, V> newEntry = new Entry<>(key, value);

        if (table[index] == null) {
            table[index] = newEntry;
            size++;
            modCount++;
            return;
        }

        Entry<K, V> current = table[index];
        while (true) {
            if (current.key.equals(key)) {
                current.value = value; // Update existing value
                return;
            }
            if (current.next == null) {
                current.next = newEntry; // Add new entry
                size++;
                modCount++;
                return;
            }
            current = current.next;
        }
    }

    public V get(K key) {
        int index = hash(key);
        Entry<K, V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null; // Key not found
    }

    public void remove(K key) {
        int index = hash(key);
        Entry<K, V> current = table[index];
        Entry<K, V> previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    table[index] = current.next; // Remove head
                } else {
                    previous.next = current.next; // Remove middle or end
                }
                size--;
                modCount++;
                return;
            }
            previous = current;
            current = current.next;
        }
    }

    public void update(K key, V value) {
        put(key, value); // Reuse put method for update
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {
            private int index = 0;
            private Entry<K, V> current = null;
            private int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                while (current == null && index < table.length) {
                    current = table[index++];
                }
                return current != null;
            }

            @Override
            public Entry<K, V> next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Entry<K, V> entry = current;
                current = current.next;
                return entry;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                sb.append(entry.key).append("=").append(entry.value).append(", ");
                entry = entry.next;
            }
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2); // Remove last comma and space
        }
        sb.append("}");
        return sb.toString();
    }

    private void resize() {
        Entry<K, V>[] oldTable = table;
        table = new Entry[oldTable.length * 2];
        threshold = (int) (table.length * LOAD_FACTOR);
        size = 0;

        for (Entry<K, V> entry : oldTable) {
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    public static void main(String[] args) {
        HashTable<String, Number> hashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.update("one", 1.0);
        System.out.println(hashTable.get("one")); // Output: 1.0
    }
}
