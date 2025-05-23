package ru.nsu.tsyganov.dsl.test;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import java.io.PrintWriter;

/**
 * Сервис для запуска тестов JUnit 5 через JUnit Platform Launcher и получения результатов.
 */
public class TestService {
    private static final SummaryGeneratingListener listener = new SummaryGeneratingListener();

    /**
     * Запускает тесты и выводит количество успешно пройденных тестов.
     * @param projectDir директория проекта (не используется для поиска тестов)
     * @param taskId идентификатор задачи, например "2.4.1"
     */
    public static void runTests(String projectDir, String taskId) {
        int passed = runTestsWithResult(projectDir, taskId);
        System.out.println("Tests passed: " + passed);
    }

    /**
     * Запускает тесты и возвращает количество успешно пройденных тестов.
     * @param projectDir директория проекта (не используется для поиска тестов)
     * @param taskId идентификатор задачи, например "2.4.1"
     * @return количество успешно пройденных тестов или -1, если тестовый класс не найден
     */
    public static int runTestsWithResult(String projectDir, String taskId) {
        try {
            String packageName = "tests.task_" + taskId.replace('.', '_');
            String className = packageName + ".AllTests";
            Class<?> testClass = Class.forName(className);

            LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                    .selectors(selectClass(testClass))
                    .build();

            Launcher launcher = LauncherFactory.create();
            launcher.registerTestExecutionListeners(listener);
            launcher.execute(request);

            TestExecutionSummary summary = listener.getSummary();
            summary.printFailuresTo(new PrintWriter(System.out, true));
            long passedCount = summary.getTestsFoundCount() - summary.getTestsFailedCount();
            return (int) passedCount;
        } catch (ClassNotFoundException e) {
            System.out.println("Тесты для задачи " + taskId + " не найдены (класс не найден).");
            return -1;
        }
    }
}
