package ru.nsu.tsyganov.dsl.model;

import java.util.List;

public class CourseConfig {
    private List<Task> tasks;
    private List<Group> groups;
    private List<Check> checks;
    private List<Checkpoint> checkpoints;
    private Settings settings;

    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }

    public List<Group> getGroups() { return groups; }
    public void setGroups(List<Group> groups) { this.groups = groups; }

    public List<Check> getChecks() { return checks; }
    public void setChecks(List<Check> checks) { this.checks = checks; }

    public List<Checkpoint> getCheckpoints() { return checkpoints; }
    public void setCheckpoints(List<Checkpoint> checkpoints) { this.checkpoints = checkpoints; }

    public Settings getSettings() { return settings; }
    public void setSettings(Settings settings) { this.settings = settings; }
}