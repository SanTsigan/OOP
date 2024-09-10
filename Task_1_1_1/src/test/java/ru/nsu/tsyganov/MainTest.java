package ru.nsu.tsyganov;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class MainTest {
    @Test
    void checkMainOneIfBranch() {
        int[] expected = {1, 2, 3, 4, 5};
        int[] numbers = {1, 3, 4, 5, 2};

        Main.heapsort(numbers);
        assertArrayEquals(expected, numbers);
    }

    void checkMainOverIfBranch() {
        int[] expected = {1, 2, 3, 4, 5};
        int[] numbers = {1, 3, 5, 4, 2};

        Main.heapsort(numbers);
        assertArrayEquals(expected, numbers);
    }

    @Test
    void checkSmallSize() {
        int[] expected = {0, 1};
        int[] numbers = {1, 0};

        Main.heapsort(numbers);
        assertArrayEquals(expected, numbers);
    }

    @Test
    void checkOneElement() {
        int[] expected = {1};
        int[] numbers = {1};

        Main.heapsort(numbers);
        assertArrayEquals(expected, numbers);
    }
}