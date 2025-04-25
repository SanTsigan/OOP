package ru.nsu.tsyganov.dsl.config;

import java.time.LocalDate;

public class Task {
    private String id;          // Уникальный идентификатор задачи (например, "lab1")
    private String name;        // Название задачи ("Калькулятор")
    private int maxScore;       // Максимальный балл (10)
    private LocalDate softDeadline; // Мягкий дедлайн
    private LocalDate hardDeadline; // Жёсткий дедлайн

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public LocalDate getHardDeadline() {
        return hardDeadline;
    }

    public void setHardDeadline(LocalDate hardDeadline) {
        this.hardDeadline = hardDeadline;
    }

    public LocalDate getSoftDeadline() {
        return softDeadline;
    }

    public void setSoftDeadline(LocalDate softDeadline) {
        this.softDeadline = softDeadline;
    }
}
