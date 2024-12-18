package ru.nsu.tsyganov.hash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class.
 */
public class HashTableTest {
    private HashTable<String, Integer> hashTable;
    private HashTable<Integer, Float> otherHashTable;

    @BeforeEach
    public void setUp() {
        hashTable = new HashTable<>();
        otherHashTable = new HashTable<>();
    }

    @Test
    public void testPutAndGet() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        otherHashTable.put(1, 0.1f);
        otherHashTable.put(2, 0.2f);

        assertEquals(1, hashTable.get("one"));
        assertEquals(2, hashTable.get("two"));
        assertNull(hashTable.get("three"));
        assertEquals(0.1f, otherHashTable.get(1));
        assertEquals(0.2f, otherHashTable.get(2));
        assertNull(otherHashTable.get(3));
    }

    @Test
    public void testOverwriteValue() {
        hashTable.put("one", 1);
        hashTable.update("one", 10); // Перезаписываем значение

        assertEquals(10, hashTable.get("one"));
    }

    @Test
    public void testRemove() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);
        hashTable.put("four", 4);
        hashTable.put("five", 5);

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
    public void testContains() {
        hashTable.put("one", 1);
        assertTrue(hashTable.containsKey("one"));
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
            assertTrue(entry.key.equals("one")
                    || entry.key.equals("two")
                    || entry.key.equals("three"));
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
    public void testIteratorConcurrentModificationOnPut() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        Iterator<HashTable.Entry<String, Integer>> iterator = hashTable.iterator();

        assertTrue(iterator.hasNext());

        hashTable.put("three", 3);

        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    public void testIteratorConcurrentModificationOnRemove() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        Iterator<HashTable.Entry<String, Integer>> iterator = hashTable.iterator();

        assertThrows(IllegalStateException.class, iterator::remove);

        iterator.next();

        hashTable.put("three", 3);

        assertThrows(ConcurrentModificationException.class, iterator::remove);
    }

    @Test
    public void testRemoveDuringIteration() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        assertThrows(ConcurrentModificationException.class, () -> {
            for (var a : hashTable) {
                hashTable.remove(a.key);
            }
        });
    }

    @Test
    public void testToString() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);
        hashTable.put("four", 4);
        hashTable.put("five", 5);
        System.out.println(hashTable.toString());
    }
}
