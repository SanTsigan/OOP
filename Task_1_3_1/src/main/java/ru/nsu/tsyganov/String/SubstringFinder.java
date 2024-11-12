package ru.nsu.tsyganov.String;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SubstringFinder {

    // Метод для построения массива префикс-функции
    private static int[] buildPrefixFunction(String pattern) {
        int[] prefix = new int[pattern.length()];
        int j = 0;

        for (int i = 1; i < pattern.length(); i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = prefix[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            prefix[i] = j;
        }

        return prefix;
    }

    // Метод для поиска подстроки с использованием алгоритма КМП
    public static List<Integer> find(String fileName, String substring) {
        List<Integer> indices = new ArrayList<>();
        StringBuilder previousLine = new StringBuilder();
        String line;
        int currentIndex = 0;

        // Построение префикс-функции для подстроки
        int[] prefix = buildPrefixFunction(substring);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            while ((line = reader.readLine()) != null) {
                String combined = previousLine.toString() + line;
                int m = substring.length();
                int n = combined.length();

                // Поиск подстроки в объединенной строке
                int j = 0; // Индекс для подстроки
                for (int i = 0; i < n; i++) {
                    while (j > 0 && combined.charAt(i) != substring.charAt(j)) {
                        j = prefix[j - 1];
                    }
                    if (combined.charAt(i) == substring.charAt(j)) {
                        j++;
                    }
                    if (j == m) {
                        // Найдено вхождение
                        if (i - m + 1 < previousLine.length()) {
                            indices.add(currentIndex + (i - m + 1));
                        } else {
                            indices.add(currentIndex + (i - m + 1 - previousLine.length()));
                        }
                        j = prefix[j - 1]; // Продолжаем поиск
                    }
                }

                currentIndex += line.length();
                previousLine.setLength(0);
                previousLine.append(line);
            }

            // Проверка последней строки
            String combined = previousLine.toString();
            int m = substring.length();
            int n = combined.length();
            int j = 0;

            for (int i = 0; i < n; i++) {
                while (j > 0 && combined.charAt(i) != substring.charAt(j)) {
                    j = prefix[j - 1];
                }
                if (combined.charAt(i) == substring.charAt(j)) {
                    j++;
                }
                if (j == m) {
                    indices.add(currentIndex + (i - m + 1));
                    j = prefix[j - 1];
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return indices;
    }
}
