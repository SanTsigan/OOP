package ru.nsu.tsyganov.primes;

import java.util.Arrays;

public class PrimesStreams {
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

    // Параллельное решение с использованием parallelStream
    public static boolean containsNonPrimeParallelStream(int[] numbers) {
        return Arrays.stream(numbers).parallel().anyMatch(n -> !isPrime(n));
    }

}
