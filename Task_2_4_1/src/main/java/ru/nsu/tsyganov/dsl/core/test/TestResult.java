package ru.nsu.tsyganov.dsl.core.test;

// TestResult.java
public class TestResult {
    private int passed;     // Успешные тесты
    private int failed;     // Проваленные тесты
    private int ignored;    // Пропущенные тесты

    // Конструктор
    public TestResult(int passed, int failed, int ignored) {
        this.passed = passed;
        this.failed = failed;
        this.ignored = ignored;
    }

    // Геттеры
    public int getPassed() { return passed; }
    public int getFailed() { return failed; }
    public int getIgnored() { return ignored; }
}
