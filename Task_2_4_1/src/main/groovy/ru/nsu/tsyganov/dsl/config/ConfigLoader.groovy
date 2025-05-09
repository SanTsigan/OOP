package ru.nsu.tsyganov.dsl.config


import groovy.transform.CompileStatic;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import ru.nsu.tsyganov.dsl.model.CourseConfig;

/**
 * Загружает конфигурацию курса из DSL на Groovy через CourseConfigBuilder
 */
@CompileStatic
class ConfigLoader {
    static CourseConfig load(String path) {
        def builder = new CourseConfigBuilder();
        def binding = new Binding([builder: builder]);
        def shell = new GroovyShell(this.class.classLoader, binding);
        shell.parse(new File(path)).run();
        return builder.build();
    }
}