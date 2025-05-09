package ru.nsu.tsyganov.dsl.model;

import java.util.Map;

public class Settings {
    private Map<Integer, String> gradeConversion; // баллы->оценка
    private Map<String, Map<String, Integer>> bonusPoints; // student->(task->bonus)

    public Map<Integer, String> getGradeConversion() { return gradeConversion; }
    public void setGradeConversion(Map<Integer, String> gradeConversion) { this.gradeConversion = gradeConversion; }

    public Map<String, Map<String, Integer>> getBonusPoints() { return bonusPoints; }
    public void setBonusPoints(Map<String, Map<String, Integer>> bonusPoints) { this.bonusPoints = bonusPoints; }
}
