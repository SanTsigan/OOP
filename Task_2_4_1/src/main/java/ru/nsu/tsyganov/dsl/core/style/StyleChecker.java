package ru.nsu.tsyganov.dsl.core.style;

import java.io.IOException;

public class StyleChecker {
    public boolean checkStyle(String sourceDir) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                "java", "-jar", "checkstyle.jar",
                "-c", "checkstyle.xml",
                sourceDir
        );
        Process process = pb.start();
        return process.waitFor() == 0;
    }
}
