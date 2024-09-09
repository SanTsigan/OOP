package ru.nsu.tsyganov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void MainTest(){
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, Main.heapsort(new int[] {5, 3, 4, 1, 2}));
    }
}