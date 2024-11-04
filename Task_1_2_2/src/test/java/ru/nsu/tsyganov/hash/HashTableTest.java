package ru.nsu.tsyganov.hash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

}
