package ru.nsu.tsyganov.dsl.model;

public class SubmissionResult {
    private String github;
    private String taskId;
    private boolean compiled;
    private int testsPassed;
    private boolean documentationGenerated;

    public SubmissionResult(String github, String taskId) {
        this.github = github;
        this.taskId = taskId;
    }

    public String getGithub() { return github; }
    public String getTaskId() { return taskId; }

    public boolean isCompiled() { return compiled; }
    public void setCompiled(boolean compiled) { this.compiled = compiled; }

    public int getTestsPassed() { return testsPassed; }
    public void setTestsPassed(int testsPassed) { this.testsPassed = testsPassed; }

    public boolean isDocumentationGenerated() { return documentationGenerated; }
    public void setDocumentationGenerated(boolean documentationGenerated) { this.documentationGenerated = documentationGenerated; }
}
