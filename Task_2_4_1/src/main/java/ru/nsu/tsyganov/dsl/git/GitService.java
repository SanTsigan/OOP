package ru.nsu.tsyganov.dsl.git;

import java.io.File;
import java.io.IOException;

public class GitService {
    public static void cloneOrUpdate(String repoUrl, String localName) {
        File dir = new File("repos/" + localName);
        try {
            if (!dir.exists()) {
                new ProcessBuilder("git", "clone", repoUrl, dir.getPath())
                        .inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("git", "-C", dir.getPath(), "pull")
                        .inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Ошибка Git: " + e.getMessage(), e);
        }
    }
}

