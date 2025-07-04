package ru.nsu.tsyganov.dsl;

import ru.nsu.tsyganov.dsl.model.CourseConfig;
import ru.nsu.tsyganov.dsl.model.Student;
import ru.nsu.tsyganov.dsl.model.SubmissionResult;
import ru.nsu.tsyganov.dsl.config.ConfigLoader;
import ru.nsu.tsyganov.dsl.git.GitService;
import ru.nsu.tsyganov.dsl.build.BuildService;
import ru.nsu.tsyganov.dsl.build.GradleService;
import ru.nsu.tsyganov.dsl.test.TestService;
import ru.nsu.tsyganov.dsl.report.ReportService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0 || args[0].equals("check")) {
            System.out.println("Запуск проверки...");
            CourseConfig config = ConfigLoader.load("courseConfig.groovy");

            // 1) Клонирование/обновление репозиториев
            config.getChecks().forEach(ch -> {
                String repoUrl = config.getGroups().stream()
                        .flatMap(g -> g.getStudents().stream())
                        .filter(s -> s.getGithub().equals(ch.getGithub()))
                        .map(Student::getRepoUrl)
                        .findFirst().orElse(null);
                GitService.cloneOrUpdate(repoUrl, ch.getGithub());
            });

            // 2) Компиляция, Javadoc, проверка стиля, тестирование
            config.getTasks().forEach(task -> {
                config.getChecks().stream()
                        .filter(ch -> ch.getTaskId().equals(task.getId()))
                        .forEach(ch -> {
                            String workDir = "repos/" + ch.getGithub() + "/" + task.getId().replace('.', '_');
                            boolean compiled = GradleService.runTask(workDir, "build");
                            if (compiled) {
                                GradleService.checkStyle(workDir);
                                //BuildService.generateJavadoc(workDir);
                                GradleService.runTask(workDir, "javadoc");
                                //TestService.runTests(workDir, task.getId());
                                GradleService.runTask(workDir, "test");
                            }

                        });
            });

            // 3) Сбор результатов и генерация HTML-отчета
            List<SubmissionResult> results = new ArrayList<>();
            config.getTasks().forEach(task -> {
                config.getChecks().stream()
                        .filter(ch -> ch.getTaskId().equals(task.getId()))
                        .forEach(ch -> {
                            String workDir = "repos/" + ch.getGithub() + "/" + task.getId().replace('.', '_');
                            SubmissionResult res = new SubmissionResult(ch.getGithub(), task.getId());
                            res.setCompiled(GradleService.runTask(workDir, "build"));
                            if (res.isCompiled()) {
                                GradleService.checkStyle(workDir);
                                res.setTestsPassed(GradleService.runTestsAndGetPassed(workDir));;
                                res.setDocumentationGenerated(GradleService.runTask(workDir, "javadoc"));
                            }
                            results.add(res);
                        });
            });
            ReportService.generateHtmlReport(results, "reports/result.html");
        } else {
            System.out.println("Неизвестная команда. Используйте: check");
        }
    }
}