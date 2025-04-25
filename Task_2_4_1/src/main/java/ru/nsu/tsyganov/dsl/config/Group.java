package ru.nsu.tsyganov.dsl.config;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String name;
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
