package ru.nsu.tsyganov.primes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PrimesTest {
    @Test
    public void testSeqCorrect() {
        int[] numbers1 = {6, 8, 7, 13, 5, 9, 4, 15};
        int[] numbers2 = {20319251, 6997901, 6997927, 6997937, 17858849, 6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053};

        assertTrue(PrimesSequential.containsNonPrimeSequential(numbers1));
        assertFalse(PrimesSequential.containsNonPrimeSequential(numbers2));
    }

    @Test
    public void testStreamsCorrect() {
        int[] numbers1 = {6, 8, 7, 13, 5, 9, 4, 15};
        int[] numbers2 = {20319251, 6997901, 6997927, 6997937, 17858849, 6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053};

        assertTrue(PrimesStreams.containsNonPrimeParallelStream(numbers1));
        assertFalse(PrimesStreams.containsNonPrimeParallelStream(numbers2));
    }

    @Test
    public void testThreadsCorrect() throws InterruptedException {
        int[] numbers1 = {6, 8, 7, 13, 5, 9, 4, 15};
        int[] numbers2 = {20319251, 6997901, 6997927, 6997937, 17858849, 6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053};

        assertTrue(PrimesThreads.containsNonPrimeParallel(numbers1, 4));
        assertFalse(PrimesThreads.containsNonPrimeParallel(numbers2, 4));
    }

    @Test
    public void testSeqTime() {
        int[] numbers = {6, 8, 7, 13, 5, 9, 4, 15};

        long start = System.nanoTime();
        assertTrue(PrimesSequential.containsNonPrimeSequential(numbers));
        long end = System.nanoTime();
        System.out.println("Sequential Time: " + (end - start) + " ns");
    }

    @Test
    public void testThrTwo() throws InterruptedException {
        int[] numbers = {6, 8, 7, 13, 5, 9, 4, 15};

        long start = System.nanoTime();
        assertTrue(PrimesThreads.containsNonPrimeParallel(numbers, 2));
        long end = System.nanoTime();
        System.out.println("Parallel time(2 threads): " + (end - start) + " ns");
    }

    @Test
    public void testThrFour() throws InterruptedException {
        int[] numbers = {6, 8, 7, 13, 5, 9, 4, 15};

        long start = System.nanoTime();
        assertTrue(PrimesThreads.containsNonPrimeParallel(numbers, 4));
        long end = System.nanoTime();
        System.out.println("Parallel time(4 threads): " + (end - start) + " ns");
    }

    @Test
    public void testThrEight() throws InterruptedException {
        int[] numbers = {6, 8, 7, 13, 5, 9, 4, 15};

        long start = System.nanoTime();
        assertTrue(PrimesThreads.containsNonPrimeParallel(numbers, 8));
        long end = System.nanoTime();
        System.out.println("Parallel time(8 threads): " + (end - start) + " ns");
    }

    @Test
    public void testStreamTime() {
        int[] numbers = {6, 8, 7, 13, 5, 9, 4, 15};

        long start = System.nanoTime();
        assertTrue(PrimesStreams.containsNonPrimeParallelStream(numbers));
        long end = System.nanoTime();
        System.out.println("Sequential Time: " + (end - start) + " ns");
    }
}