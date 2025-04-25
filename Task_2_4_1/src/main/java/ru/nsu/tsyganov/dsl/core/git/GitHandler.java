package ru.nsu.tsyganov.dsl.core.git;

import java.io.File;
import java.io.IOException;

public class GitHandler {
    public void cloneRepository(String repoUrl, String studentDir) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("git", "clone", repoUrl, studentDir);
        pb.directory(new File("repos/"));
        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("Ошибка клонирования: " + repoUrl);
        }
    }
}