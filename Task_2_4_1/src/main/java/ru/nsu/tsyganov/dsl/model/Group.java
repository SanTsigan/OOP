package ru.nsu.tsyganov.dsl.model;

import java.util.List;

public class Group {
    private String name;
    private List<Student> students;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
}

