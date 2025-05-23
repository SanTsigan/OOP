package ru.nsu.tsyganov.dsl.report;

import ru.nsu.tsyganov.dsl.model.SubmissionResult;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportService {
    public static void generateHtmlReport(List<SubmissionResult> results, String outputPath) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Отчет OOPChecker</title></head><body>");
        html.append("<h1>Результаты проверки</h1>");
        html.append("<table border='1'><tr><th>GitHub</th><th>Task</th><th>Compiled</th><th>Tests Passed</th><th>Javadoc</th></tr>");
        for (SubmissionResult r : results) {
            html.append("<tr>")
                    .append("<td>").append(r.getGithub()).append("</td>")
                    .append("<td>").append(r.getTaskId()).append("</td>")
                    .append("<td>").append(r.isCompiled()).append("</td>")
                    .append("<td>").append(r.getTestsPassed()).append("</td>")
                    .append("<td>").append(r.isDocumentationGenerated()).append("</td>")
                    .append("</tr>");
        }
        html.append("</table></body></html>");

        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(html.toString());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи отчета: " + e.getMessage(), e);
        }
    }
}
