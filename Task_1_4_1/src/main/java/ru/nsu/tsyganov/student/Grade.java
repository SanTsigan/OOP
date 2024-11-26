package ru.nsu.tsyganov.student;

public class Grade {
    private final GradeType type;
    private final GradeValue value;

    public Grade(GradeType type, GradeValue value) {
        this.type = type;
        this.value = value;
    }

    public GradeType getType() {
        return type;
    }

    public GradeValue getValue() {
        return value;
    }

    public boolean isSatisfactory() {
        return value == GradeValue.SATISFACTORY;
    }

    public boolean isExcellent() {
        return value == GradeValue.EXCELLENT;
    }
}
