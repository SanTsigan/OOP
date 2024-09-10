package ru.nsu.tsyganov;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class MainTest {
    @Test
    void checkMain() {
        int[] numbers = {1, 2, 3, 4, 5};
        int[] expected = {5, 3, 4, 1, 2};

        Main.heapsort(expected);
        assertArrayEquals(numbers, expected);
    }
}