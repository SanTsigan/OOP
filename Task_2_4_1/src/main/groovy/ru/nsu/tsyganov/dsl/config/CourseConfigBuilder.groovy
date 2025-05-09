package ru.nsu.tsyganov.dsl.config

import groovy.transform.CompileStatic;
import ru.nsu.tsyganov.dsl.model.*;
import java.time.LocalDate;

@CompileStatic
class CourseConfigBuilder {
    private CourseConfig config = new CourseConfig();

    def course(Closure<?> cl) {
        cl.delegate = this; cl.resolveStrategy = Closure.DELEGATE_FIRST; cl();
    }

    def tasks(Closure<?> cl) {
        def list = [] as List<Task>;
        def delegate = new Object() { def task(Closure<?> tcl) { Task t = new Task(); tcl.delegate = t; tcl.resolveStrategy = Closure.DELEGATE_FIRST; tcl(); list << t }};
        cl.delegate = delegate; cl.resolveStrategy = Closure.DELEGATE_FIRST; cl();
        config.setTasks(list);
    }

    def groups(Closure<?> cl) {
        def list = [] as List<Group>;
        def delegate = new Object() {
            def group(Map attrs, Closure<?> gcl) {
                Group g = new Group(); g.setName(attrs.name as String);
                def students = [] as List<Student>;
                def sd = new Object() { def student(Closure<?> scl) { Student s = new Student(); scl.delegate = s; scl.resolveStrategy = Closure.DELEGATE_FIRST; scl(); students << s }};
                gcl.delegate = sd; gcl.resolveStrategy = Closure.DELEGATE_FIRST; gcl();
                g.setStudents(students); list << g;
            }
        };
        cl.delegate = delegate; cl.resolveStrategy = Closure.DELEGATE_FIRST; cl();
        config.setGroups(list);
    }

    def checks(Closure<?> cl) {
        def list = [] as List<Check>;
        def delegate = new Object() { def check(Closure<?> ccl) { Check c = new Check(); ccl.delegate = c; ccl.resolveStrategy = Closure.DELEGATE_FIRST; ccl(); list << c }};
        cl.delegate = delegate; cl.resolveStrategy = Closure.DELEGATE_FIRST; cl();
        config.setChecks(list);
    }

    def checkpoints(Closure<?> cl) {
        def list = [] as List<Checkpoint>;
        def delegate = new Object() { def checkpoint(Closure<?> cpcl) { Checkpoint cp = new Checkpoint(); cpcl.delegate = cp; cpcl.resolveStrategy = Closure.DELEGATE_FIRST; cpcl(); list << cp }};
        cl.delegate = delegate; cl.resolveStrategy = Closure.DELEGATE_FIRST; cl();
        config.setCheckpoints(list);
    }

    def settings(Closure<?> cl) {
        Settings s = new Settings(); cl.delegate = s; cl.resolveStrategy = Closure.DELEGATE_FIRST; cl();
        config.setSettings(s);
    }

    CourseConfig build() { return config; }
}

