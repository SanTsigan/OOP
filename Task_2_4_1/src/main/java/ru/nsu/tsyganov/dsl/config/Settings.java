package ru.nsu.tsyganov.dsl.config;

import java.util.HashMap;
import java.util.Map;

public class Settings {
    private Map<String, Integer> gradingCriteria = new HashMap<>(); // Критерии оценок (A=90, B=75)
    private int testTimeout = 30; // Таймаут тестов в секундах
    // Доп. настройки...

    public void setGradingCriteria(String grade, int threshold) {
        gradingCriteria.put(grade, threshold);
    }

    public int getTestTimeout() {
        return testTimeout;
    }

    public void setTestTimeout(int testTimeout) {
        this.testTimeout = testTimeout;
    }
}
