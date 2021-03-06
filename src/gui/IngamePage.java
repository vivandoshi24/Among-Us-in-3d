package de.vd24.amongus.gui;

import de.vd24.amongus.core.AmongUs;
import de.vd24.amongus.event.SabotageEvent;
import de.vd24.amongus.event.UpdateEvent;
import de.vd24.amongus.game.DeadBodyGameObject;
import de.vd24.amongus.game.PlayerGameObject;
import de.vd24.amongus.game.VentGameObject;
import de.vd24.amongus.model.GameState;
import de.vd24.amongus.model.PlayerRole;
import de.vd24.amongus.model.Sabotage;
import de.vd24.amongus.net.NetMessage;
import de.vd24.neko.event.KeyPressedEvent;
import org.greenrobot.eventbus.Subscribe;
import org.lwjgl.glfw.GLFW;

public class IngamePage extends BasePage {

    private boolean tasksVisible = true;

    public IngamePage() {
        super("Ingame.html");
        var me = AmongUs.get().getSession().getMyself();
        if (me.killCooldown == -1)
            me.resetKillCooldown();
    }

    @Subscribe
    public void onSabotage(SabotageEvent e) {
        runOnUiThread(() -> {
            if (amongUs.getSession().currentSabotage == Sabotage.O2 || amongUs.getSession().currentSabotage == Sabotage.Reactor)
                context.call("setAlarm", true);
            else
                context.call("setAlarm", false);

            context.call("setCommsSabotaged", amongUs.getSession().currentSabotage == Sabotage.Comms);
        });
    }

    @Override
    public boolean isCursorVisible() {
        return false;
    }

    @Override
    public void onDomReady() {
        super.onDomReady();
        for (var task : amongUs.getSession().getMyself().tasks) {
            var state = task.getProgress() == 0 ? 0 : 1;
            if (task.isTimerRunning()) state = 1;
            if (task.isCompleted()) state = 2;

            context.call("addTask", task.toString(), state);
        }
        setProgress(amongUs.getSession().taskProgress);
        amongUs.getStateController().changeState(GameState.Ingame);

        var me = amongUs.getSession().getMyself();
        context.call("setImpostor", me.getRole() == PlayerRole.Impostor);
        context.call("setGhost", !me.alive);
        context.call("setKillCooldown", me.killCooldown);
        onSabotage(null);
    }

    private long lastUpdate = System.currentTimeMillis();

    private String lastAction = "Use";

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (System.currentTimeMillis() - lastUpdate > 1000) {
            var me = AmongUs.get().getSession().getMyself();

            var idx = 0;
            for (var task : me.tasks) {
                if (task.isTimerRunning()) {
                    context.call("setTask", idx, task.toString());
                }
                idx++;
            }

            lastUpdate = System.currentTimeMillis();

            if (AmongUs.get().getSession().currentSabotage != null) {
                context.call("setSabotage", TaskFormatter.formatSabotage(AmongUs.get().getSession().currentSabotage, AmongUs.get().getSession().currentSabotageDuration));
                AmongUs.get().getSession().currentSabotageDuration--;
            } else {
                context.call("setSabotage", "");
            }
            if (me.killCooldown > 0) {
                me.killCooldown--;
                context.call("setKillCooldown", me.killCooldown);
            }
        }

        var hovering = AmongUs.get().getHoveringGameObject();
        var action = "Use";
        if (hovering instanceof PlayerGameObject) {
            action = "Kill";
        } else if (hovering instanceof DeadBodyGameObject) {
            action = "Report";
        } else if (hovering instanceof VentGameObject) {
            action = "Vent";
        }
        if (!action.equals(lastAction)) {
            lastAction = action;
            context.call("setMainAction", action);
        }
    }

    @Subscribe
    public void onProgressChanged(NetMessage.OnTaskProgressChanged progressChanged) {
        runOnUiThread(() -> setProgress(progressChanged.progress));
    }

    private void setProgress(float progress) {
        context.call("setTaskProgress", progress);
    }

    @Subscribe
    public void onKeyPress(KeyPressedEvent event) {
        if (event.key == GLFW.GLFW_KEY_TAB) {
            tasksVisible = !tasksVisible;
            context.call("setTasksVisible", tasksVisible);
        }
    }

}

