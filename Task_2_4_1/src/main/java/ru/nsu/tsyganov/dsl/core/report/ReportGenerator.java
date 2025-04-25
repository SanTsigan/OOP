package ru.nsu.tsyganov.dsl.core.report;

import java.util.List;

// ReportGenerator.java (дополнено)
public class ReportGenerator {
    public String generateHtml(List<StudentResult> results) {
        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        html.append("<h1>Отчёт по студентам</h1>");
        html.append("<table border='1'>");
        html.append("<tr><th>Студент</th><th>Общий балл</th></tr>");

        for (StudentResult result : results) {
            html.append("<tr>")
                    .append("<td>").append(result.getStudentName()).append("</td>")
                    .append("<td>").append(result.getTotalScore()).append("</td>")
                    .append("</tr>");
        }

        html.append("</table></body></html>");
        return html.toString();
    }
}
