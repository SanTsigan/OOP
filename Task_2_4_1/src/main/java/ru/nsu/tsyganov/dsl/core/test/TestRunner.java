package ru.nsu.tsyganov.dsl.core.test;

import java.io.IOException;

// TestRunner.java (дополнено)
public class TestRunner {
    public TestResult runTests(String classpath) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                "java", "-cp", classpath,
                "org.junit.runner.JUnitCore",
                "com.oop.tests.Lab1Test"
        );
        Process process = pb.start();

        // Чтение вывода тестов
        String output = new String(process.getInputStream().readAllBytes());
        String error = new String(process.getErrorStream().readAllBytes());

        // Парсинг результатов (пример для JUnit 4)
        int passed = parsePassedTests(output);
        int failed = parseFailedTests(output);
        int ignored = parseIgnoredTests(output);

        return new TestResult(passed, failed, ignored);
    }

    //Добавить парсинг
}
