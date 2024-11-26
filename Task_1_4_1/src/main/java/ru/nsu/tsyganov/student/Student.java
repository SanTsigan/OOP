package ru.nsu.tsyganov.student;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private List<Grade> grades;
    private StudyForm studyForm;
    private boolean thesisGradeExcellent;

    public Student(String name, StudyForm studyForm) {
        this.name = name;
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

    public double calculateGPA() {
        if (grades.isEmpty()) return 0.0;

        int totalPoints = 0;
        for (Grade grade : grades) {
            switch (grade) {
                case EXCELLENT:
                    totalPoints += 5;
                    break;
                case GOOD:
                    totalPoints += 4;
                    break;
                case SATISFACTORY:
                    totalPoints += 3;
                    break;
                case UNSATISFACTORY:
                    totalPoints += 1; // предположим, что неудовлетворительная оценка - это 1
                    break;
            }
        }
        return (double) totalPoints / grades.size();
    }

    public boolean canTransferToBudget() {
        // Проверяем последние две сессии (предположим, что последние 4 оценки - это последние две сессии)
        if (grades.size() < 4) return false;

        int satisfactoryCount = 0;
        for (int i = grades.size() - 4; i < grades.size(); i++) {
            if (grades.get(i) == Grade.SATISFACTORY) {
                satisfactoryCount++;
            }
        }
        return satisfactoryCount == 0 && studyForm == StudyForm.PAID;
    }

    public boolean canGetRedDiploma() {
        if (grades.isEmpty()) return false;

        int excellentCount = 0;
        for (Grade grade : grades) {
            if (grade == Grade.EXCELLENT) {
                excellentCount++;
            }
            if (grade == Grade.SATISFACTORY || grade == Grade.UNSATISFACTORY) {
                return false; // Наличие неудовлетворительных оценок
            }
        }

        double percentage = (double) excellentCount / grades.size();
        return percentage >= 0.75 && thesisGradeExcellent;
    }

    public boolean canGetIncreasedScholarship() {
        // Предположим, что повышенная стипендия может быть получена, если GPA выше 4.5
        return calculateGPA() > 4.5;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name +
                ", grades=" + grades +
                ", studyForm=" + studyForm +
                '}';
    }
}
