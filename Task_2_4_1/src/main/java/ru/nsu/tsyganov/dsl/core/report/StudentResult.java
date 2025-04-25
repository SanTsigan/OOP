package ru.nsu.tsyganov.dsl.core.report;

import java.util.HashMap;
import java.util.Map;

// StudentResult.java
public class StudentResult {
    private String studentName; // ФИО студента
    private int totalScore;     // Итоговый балл
    private Map<String, Integer> taskScores = new HashMap<>(); // Баллы по задачам

    // Геттеры и сеттеры
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public int getTotalScore() { return totalScore; }
    public void setTotalScore(int totalScore) { this.totalScore = totalScore; }

    public void addTaskScore(String taskId, int score) {
        taskScores.put(taskId, score);
    }
}
