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
                    .filter(p -> p.toString().endsWith(".java"))
                    .map(Path::toAbsolutePath)
                    .map(Path::toString)
                    .collect(Collectors.toList());

            if (javaFiles.isEmpty()) {
                System.out.println("Нет .java файлов для компиляции в: " + projectDir);
                return false;
            }

            System.out.println("Current classpath: " + System.getProperty("java.class.path"));


            // Создаём временный файл со списком исходников
            File sourcesFile = File.createTempFile("sources", ".txt");
            Files.write(sourcesFile.toPath(), javaFiles);

            List<String> command = new ArrayList<>();
            command.add("javac");
            command.add("-cp");
            command.add(getJUnitClasspath());
            command.add("-d");
            command.add(projectDir + "/out");
            command.add("@" + sourcesFile.getAbsolutePath());

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(new File(projectDir));
            boolean result = pb.inheritIO().start().waitFor() == 0;

            sourcesFile.delete(); // удалим временный файл

            return result;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Ошибка компиляции: " + e.getMessage(), e);
        }
    }

    public static String getJUnitClasspath() {
        String classpath = System.getProperty("java.class.path");
        // Добавим все .jar файлы из lib, если они есть
        File libDir = new File("build/libs");
        if (libDir.exists()) {
            File[] jars = libDir.listFiles((dir, name) -> name.endsWith(".jar"));
            if (jars != null) {
                for (File jar : jars) {
                    classpath += File.pathSeparator + jar.getAbsolutePath();
                }
            }
        }

        classpath += File.pathSeparator + System.getProperty("user.home") +
                "/.gradle/caches/modules-2/files-2.1/org.junit.jupiter/junit-jupiter/5.9.2/";

        return classpath;
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
