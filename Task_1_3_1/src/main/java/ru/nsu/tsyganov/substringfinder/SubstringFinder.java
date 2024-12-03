package ru.nsu.tsyganov.substringfinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, для поиска подстроки с помощью алгоритма КМП.
 */
public class SubstringFinder {

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

    /**
     * Буфферизированное чтение файла и поиск всех вхождений подстроки в нем.
     */
    public List<Integer> find(String fileName, String substring) {
        List<Integer> indices = new ArrayList<>();
        StringBuilder previousLine = new StringBuilder();
        int currentIndex = 0;

        int[] prefix = buildPrefixFunction(substring);
        int m = substring.length();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            char[] buffer = new char[1024];
            StringBuilder combined = new StringBuilder(previousLine);
            int bytesRead;

            while ((bytesRead = reader.read(buffer)) != -1) {
                combined.append(buffer, 0, bytesRead);

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

                currentIndex += bytesRead;

                if (combined.length() > m) {
                    combined.delete(0, combined.length() - m);
                } else {
                    combined.setLength(0);
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return indices;
    }
}
