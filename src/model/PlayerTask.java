package de.vd24.amongus.model;

import de.vd24.amongus.gui.TaskFormatter;

import java.util.ArrayList;
import java.util.List;

public class PlayerTask {

    private final List<TaskStage> stages = new ArrayList<>();

    private int progress;

    private long timerEnd = -1;

    private PlayerTask() {
    }

    public int getProgress() {
        return progress;
    }

    public void startTimer(long timeout) {
        timerEnd = System.currentTimeMillis() + timeout * 1000;
    }

    public boolean isTimerRunning() {
        return timerEnd != -1 && !isTimerEnded();
    }

    public boolean isTimerEnded() {
        return timerEnd != -1 && timerEnd <= System.currentTimeMillis();
    }

    public long getEndTime() {
        return timerEnd;
    }

    public State getState() {
        if (progress == 0) return State.NotStarted;
        else if (progress == stages.size()) return State.Completed;
        else return State.InProgress;
    }

    public boolean isCompleted() {
        return getState() == State.Completed;
    }

    public TaskStage getNextStage() {
        if (progress >= stages.size()) return getLastStage();
        return stages.get(progress);
    }

    public TaskStage getLastStage() {
        return stages.get(stages.size() - 1);
    }

    public void advance() {
        if (getState() == State.Completed) throw new IllegalStateException("Cannot advance completed task");
        progress++;
    }

    public boolean isMultiStage() {
        return stages.size() > 1;
    }

    public int length() {
        return stages.size();
    }

    public enum State {
        NotStarted,
        InProgress,
        Completed
    }

    @Override
    public String toString() {
        return TaskFormatter.format(this);
    }

    public static class Builder {

        private final PlayerTask task = new PlayerTask();

        public Builder addStage(Location location, TaskType type) {
            addStage(new TaskStage(location, type));
            return this;
        }

        public Builder addStage(TaskStage stage) {
            task.stages.add(stage);
            return this;
        }

        public PlayerTask build() {
            return task;
        }

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof PlayerTask)) return false;

        var other = (PlayerTask) obj;
        if (other.stages.size() != stages.size()) return false;

        for (var i = 0; i < stages.size(); i++) {
            if (!other.stages.get(i).equals(stages.get(i)))
                return false;
        }

        return true;
    }
}
