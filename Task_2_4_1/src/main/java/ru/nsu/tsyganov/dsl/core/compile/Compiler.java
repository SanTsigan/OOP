package ru.nsu.tsyganov.dsl.core.compile;

import java.io.IOException;

public class Compiler {
    public boolean compile(String sourceDir) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("javac", "-d", "out/", sourceDir + "/*.java");
        Process process = pb.start();
        return process.waitFor() == 0;
    }
}
