package ru.nsu.tsyganov.dsl.dsl;

import groovy.lang.GroovyRuntimeException;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilationFailedException;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {
    public static CourseConfig load(String scriptPath) throws IOException, CompilationFailedException {
        GroovyShell shell = new GroovyShell();
        try {
            Script script = shell.parse(new File(scriptPath));
            CourseConfig config = new CourseConfig();
            script.setProperty("config", config);
            script.run(); // Может выбросить GroovyRuntimeException
            return config;
        } catch (GroovyRuntimeException e) {
            throw new IOException("Ошибка выполнения Groovy-скрипта: " + e.getMessage());
        }
    }
}
