package ru.nsu.tsyganov.dsl.build;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            List<String> javaFiles = Files.walk(Paths.get(projectDir, "src"))
                    .map(Path::toString)
                    .filter(string -> string.endsWith(".java"))
                    .toList();

            if (javaFiles.isEmpty()) {
                System.out.println("Нет .java файлов для компиляции в: " + projectDir);
                return false;
            }

            List<String> command = new ArrayList<>();
            command.add("javac");
            command.add("-d");
            command.add(projectDir + "/out");
            command.addAll(javaFiles);

            ProcessBuilder pb = new ProcessBuilder(command);
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
     *
     * @param projectDir директория проекта
     */
    public static void checkStyle(String projectDir) {
        try {
            int exitCode = new ProcessBuilder(
                    "google-java-format", "--replace", projectDir + "/src/**/*.java"
            ).directory(new File(projectDir)).inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Ошибка проверки стиля: " + e.getMessage(), e);
        }
    }
}
