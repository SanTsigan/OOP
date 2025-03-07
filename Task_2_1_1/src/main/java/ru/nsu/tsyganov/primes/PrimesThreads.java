package ru.nsu.tsyganov.primes;

import java.util.concurrent.atomic.AtomicBoolean;

public class PrimesThreads {

    private static AtomicBoolean nonPrimeFound = new AtomicBoolean(false);

    // Метод для проверки, является ли число простым
    public static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    // Параллельное решение с использованием Thread
    public static boolean containsNonPrimeParallel(int[] numbers, int numThreads) throws InterruptedException {
        nonPrimeFound.set(false);
        Thread[] threads = new Thread[numThreads];
        int chunkSize = numbers.length / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? numbers.length : start + chunkSize;
            threads[i] = new Thread(new PrimeCheckerTask(numbers, start, end));
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        return nonPrimeFound.get();
    }

    private static class PrimeCheckerTask implements Runnable {
        private int[] numbers;
        private int start;
        private int end;

        public PrimeCheckerTask(int[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                if (!isPrime(numbers[i])) {
                    nonPrimeFound.set(true);
                    break;
                }
            }
        }
    }
}