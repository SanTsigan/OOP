package ru.nsu.tsyganov.dsl.build;

import java.io.File;
import java.io.IOException;

/**
 * Сервис для компиляции, генерации Javadoc и проверки стиля.
 */
public class BuildService {
    /**
     * Компиляция проекта.
     * @param projectDir директория проекта
     * @return true, если компиляция прошла успешно
     */
    public static boolean compile(String projectDir) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "javac", "-d", projectDir + "/out", projectDir + "/src/**/*.java"
            );
            pb.directory(new File(projectDir));
            return pb.inheritIO().start().waitFor() == 0;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Ошибка компиляции: " + e.getMessage(), e);
        }
    }

    /**
     * Генерация Javadoc для проекта.
     * @param projectDir директория проекта
     * @return true, если javadoc сгенерирован успешно
     */
    public static boolean generateJavadoc(String projectDir) {
        try {
            int exitCode = new ProcessBuilder(
                    "javadoc", "-d", projectDir + "/out/docs", projectDir + "/src/**/*.java"
            ).directory(new File(projectDir)).inheritIO().start().waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Ошибка Javadoc: " + e.getMessage(), e);
        }
    }

    /**
     * Применяет форматирование Google Java Format к исходникам.
     * @param projectDir директория проекта
     * @return true, если форматирование прошло успешно
     */
    public static boolean checkStyle(String projectDir) {
        try {
            int exitCode = new ProcessBuilder(
                    "google-java-format", "--replace", projectDir + "/src/**/*.java"
            ).directory(new File(projectDir)).inheritIO().start().waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Ошибка проверки стиля: " + e.getMessage(), e);
        }
    }
}
