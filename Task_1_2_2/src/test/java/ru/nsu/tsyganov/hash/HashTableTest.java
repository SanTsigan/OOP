package ru.nsu.tsyganov.hash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class HashTableTest {
    private HashTable<String, Integer> hashTable;

    @BeforeEach
    public void setUp() {
        hashTable = new HashTable<>();
    }

    @Test
    public void testPutAndGet() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        assertEquals(1, hashTable.get("one"));
        assertEquals(2, hashTable.get("two"));
        assertNull(hashTable.get("three"));
    }

    @Test
    public void testOverwriteValue() {
        hashTable.put("one", 1);
        hashTable.put("one", 10); // Перезаписываем значение

        assertEquals(10, hashTable.get("one"));
    }

    @Test
    public void testRemove() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        assertEquals(1, hashTable.remove("one"));
        assertNull(hashTable.get("one"));
        assertEquals(2, hashTable.get("two"));
    }

    @Test
    public void testEquals() {
        HashTable<String, Integer> anotherHashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        anotherHashTable.put("one", 1);
        anotherHashTable.put("two", 2);

        assertEquals(hashTable, anotherHashTable);

        anotherHashTable.put("three", 3);
        assertNotEquals(hashTable, anotherHashTable);
    }

    @Test
    public void testHashCode() {
        HashTable<String, Integer> anotherHashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        anotherHashTable.put("one", 1);
        anotherHashTable.put("two", 2);

        assertEquals(hashTable.hashCode(), anotherHashTable.hashCode());

        anotherHashTable.put("three", 3);
        assertNotEquals(hashTable.hashCode(), anotherHashTable.hashCode());
    }

    @Test
    public void testIteratorEmpty() {
        Iterator<HashTable.Entry<String, Integer>> iterator = hashTable.iterator();
        assertFalse(iterator.hasNext()); // Итератор должен быть пустым
    }

    @Test
    public void testIteratorSingleElement() {
        hashTable.put("one", 1);
        Iterator<HashTable.Entry<String, Integer>> iterator = hashTable.iterator();

        assertTrue(iterator.hasNext());
        HashTable.Entry<String, Integer> entry = iterator.next();
        assertEquals("one", entry.key);
        assertEquals(1, entry.value);
        assertFalse(iterator.hasNext()); // После получения элемента итератор должен быть пустым
    }

    @Test
    public void testIteratorMultipleElements() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);

        Iterator<HashTable.Entry<String, Integer>> iterator = hashTable.iterator();
        int count = 0;

        while (iterator.hasNext()) {
            HashTable.Entry<String, Integer> entry = iterator.next();
            count++;
            assertTrue(entry.key.equals("one") || entry.key.equals("two") || entry.key.equals("three"));
        }

        assertEquals(3, count); // Должно быть три элемента
    }

    @Test
    public void testIteratorRemove() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        Iterator<HashTable.Entry<String, Integer>> iterator = hashTable.iterator();

        assertTrue(iterator.hasNext());
        HashTable.Entry<String, Integer> entry = iterator.next();
        iterator.remove();

        assertNull(hashTable.get(entry.key));
        assertEquals(1, hashTable.getSize());
    }

    @Test
    public void testIteratorRemoveWithoutNext() {
        Iterator<HashTable.Entry<String, Integer>> iterator = hashTable.iterator();
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    public void testIteratorConcurrentModification() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        Iterator<HashTable.Entry<String, Integer>> iterator = hashTable.iterator();

        assertTrue(iterator.hasNext());

        hashTable.put("three", 3);

        assertThrows(ConcurrentModificationException.class, iterator::next);
    }
}
