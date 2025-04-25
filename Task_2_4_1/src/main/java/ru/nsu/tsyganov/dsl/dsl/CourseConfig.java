package ru.nsu.tsyganov.dsl.dsl;

import ru.nsu.tsyganov.dsl.config.ControlPoint;
import ru.nsu.tsyganov.dsl.config.Task;
import ru.nsu.tsyganov.dsl.config.Group;
import ru.nsu.tsyganov.dsl.config.Settings;

import java.util.ArrayList;
import java.util.List;

public class CourseConfig {
    private final List<Task> tasks = new ArrayList<>();
    private final List<Group> groups = new ArrayList<>();
    private List<ControlPoint> controlPoints = new ArrayList<>();
    private Settings settings = new Settings();

    public void addTask(Task task) { tasks.add(task); }
    public void addGroup(Group group) { groups.add(group); }
    public void addControlPoint(ControlPoint cp) {controlPoints.add(cp); }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public List<ControlPoint> getControlPoints() {
        return controlPoints;
    }

    public void setControlPoints(List<ControlPoint> controlPoints) {
        this.controlPoints = controlPoints;
    }

}
