package ru.nsu.tsyganov.student;

import java.util.ArrayList;
import java.util.List;

/**
 * Главный класс зачётной книжки.
 */
public class Student {
    private String name;
    private String group;
    private int semester;
    private List<Grade> grades;
    private StudyForm studyForm;
    private boolean thesisGradeExcellent;
    private GradeValue qualificationWorkGrade;

    /**
     * Конструктор.
     */
    public Student(String name, int course, String group, StudyForm studyForm) {
        this.name = name;
        this.group = group;
        this.studyForm = studyForm;
        this.grades = new ArrayList<>();
        this.thesisGradeExcellent = false; // По умолчанию квалификационная работа не оценена
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public void setThesisGradeExcellent(boolean excellent) {
        this.thesisGradeExcellent = excellent;
    }

    /**
     * Расчситывает среднюю оценку студента.
     */
    public double calculateAverage() {
        return grades.stream()
                .filter(grade -> !grade.isSatisfactory()) // исключаем "удовлетворительно"
                .mapToInt(grade -> switch (grade.getValue()) {
                    case EXCELLENT -> 5;
                    case GOOD -> 4;
                    default -> 0; // Неправильная оценка
                })
                .average()
                .orElse(0.0);
    }

    /**
     * Возможность перехода на бюджетную форму обучения.
     */
    public boolean canTransferToBudget() {
        long lastTwoSessions = grades.stream()
                .filter(g -> g.getType() == GradeType.EXAM)
                .skip(Math.max(0, grades.size() - 2))
                .filter(Grade::isSatisfactory)
                .count();
        return lastTwoSessions == 0;
    }

    /**
     * Возможность получения красного диплома.
     */
    public boolean canGetRedDiploma() {
        long excellentCount = grades.stream()
                .filter(g -> g.getType() == GradeType.EXAM || g.getType() == GradeType.DIFF_CREDIT)
                .filter(Grade::isExcellent)
                .count();

        long totalCount = grades.stream()
                .filter(g -> g.getType() == GradeType.EXAM || g.getType() == GradeType.DIFF_CREDIT)
                .count();

        boolean hasSatisfactory = grades.stream()
                .anyMatch(Grade::isSatisfactory);

        boolean qualificationWorkExcellent = (qualificationWorkGrade == GradeValue.EXCELLENT);

        return totalCount > 0 && (excellentCount * 100.0 / totalCount >= 75)
                && !hasSatisfactory && qualificationWorkExcellent;
    }

    public boolean canGetIncreasedScholarship() {
        // Предположим, что повышенная стипендия может быть получена, если GPA выше 4.5
        return calculateAverage() >= 4.5;
    }

    @Override
    public String toString() {
        return "Student{"
                + "name='" + name + "'"
                + "group='" + group + "'"
                + ", grades=" + grades
                + ", studyForm=" + studyForm
                + '}';
    }

    public void setQualificationWorkGrade(GradeValue grade) {
        this.qualificationWorkGrade = grade;
    }
}
