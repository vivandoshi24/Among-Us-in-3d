package de.vd24.amongus.model;

import de.vd24.amongus.core.AmongUs;
import org.joml.Vector3f;

import java.util.List;

public class Player implements PlayerBehavior {

    public static final int SKIP_PLAYER = -1;

    public int id;

    public String username;

    public Vector3f position;

    public float rotation;

    public PlayerRole role;

    public PlayerColor color;

    public List<PlayerTask> tasks;

    public boolean alive = true;

    public int emergencyMeetings = 0;

    public int killCooldown = -1;

    public void resetKillCooldown() {
        killCooldown = AmongUs.get().getSession().getConfig().getKillCooldown();
    }

    public boolean canKill() {
        return killCooldown == 0;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public PlayerRole getRole() {
        return role;
    }

    public PlayerColor getColor() {
        return color;
    }

    public boolean canDoTask(Location location, TaskType taskType) {
        if (role == PlayerRole.Impostor) return false;
        var task = findRunningTaskByStage(location, taskType);
        return task != null && !task.isTimerRunning();
    }

    public PlayerTask findRunningTaskByStage(Location location, TaskType taskType) {
        for (var task : tasks) {
            var stage = task.getNextStage();
            if (stage.getTaskType() == taskType && stage.getLocation() == location && !task.isCompleted())
                return task;
        }
        return null;
    }

}
