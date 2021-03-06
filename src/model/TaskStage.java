package de.vd24.amongus.model;

import java.util.Objects;

public class TaskStage {

    private Location location;

    private TaskType taskType;

    private TaskStage() {
    }

    public TaskStage(Location location, TaskType taskType) {
        this.location = location;
        this.taskType = taskType;
    }

    public Location getLocation() {
        return location;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskStage taskStage = (TaskStage) o;
        return location == taskStage.location &&
                taskType == taskStage.taskType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, taskType);
    }

}
