package ru.nsu.tsyganov.dsl.build;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Сервис для запуска Gradle-задач в студенческих проектах и анализа тестов.
 */
public class GradleService {

    public static boolean compileProject(String projectDir) {
        return runTask(projectDir, "build");
    }

    public static boolean runTask(String projectDir, String task) {
        try {
            boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
            String gradleCommand = isWindows ? "gradlew.bat" : "./gradlew";

            ProcessBuilder pb = new ProcessBuilder(gradleCommand, task);
            pb.directory(new File(projectDir));
            pb.inheritIO();
            Process process = pb.start();
            return process.waitFor() == 0;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Gradle task failed: " + e.getMessage(), e);
        }
    }

    public static boolean checkStyle(String projectDir) {
        try {
            File jar = new File("google-java-format.jar");
            if (!jar.exists()) {
                System.out.println("Файл google-java-format.jar не найден. Пропускаем проверку стиля.");
                return false;
            }

            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-jar", jar.getAbsolutePath(), "--replace", "--skip-javadoc-formatting", projectDir + "/src"
            );
            pb.directory(new File(projectDir));
            pb.inheritIO();
            return pb.start().waitFor() == 0;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Ошибка при запуске google-java-format: " + e.getMessage(), e);
        }
    }

    public static int runTestsAndGetPassed(String projectDir) {
        if (!runTask(projectDir, "test")) {
            return -1;
        }
        return parseTestResults(projectDir);
    }

    private static int parseTestResults(String projectDir) {
        try {
            File reportsDir = new File(projectDir, "build/test-results/test");
            if (!reportsDir.exists() || !reportsDir.isDirectory()) {
                System.out.println("Отчёты о тестах не найдены");
                return -1;
            }

            File[] xmlFiles = reportsDir.listFiles((d, name) -> name.endsWith(".xml"));
            if (xmlFiles == null || xmlFiles.length == 0) {
                System.out.println("Файлы с результатами тестов не найдены");
                return -1;
            }

            int totalPassed = 0;
            for (File xml : xmlFiles) {
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = builder.parse(xml);
                Element testsuite = (Element) doc.getElementsByTagName("testsuite").item(0);
                int tests = Integer.parseInt(testsuite.getAttribute("tests"));
                int failures = Integer.parseInt(testsuite.getAttribute("failures"));
                int errors = Integer.parseInt(testsuite.getAttribute("errors"));
                totalPassed += tests - failures - errors;
            }
            return totalPassed;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
