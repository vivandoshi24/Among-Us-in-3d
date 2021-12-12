package de.vd24.amongus.core;

import de.vd24.amongus.event.StateChangeEvent;
import de.vd24.amongus.model.GameState;
import de.vd24.neko.event.Events;
import de.vd24.neko.util.Log;

public class StateController {

    private GameState state = GameState.Loading;

    public void changeState(GameState state) {
        var prev = this.state;
        this.state = state;
        if (prev != state)
            handleChange(prev, state);
    }

    public GameState getState() {
        return state;
    }

    public boolean isRunning() {
        return state == GameState.Ingame;
    }

    private void handleChange(GameState prev, GameState next) {
        Log.i("Changed state from " + prev + " to " + next);
        Events.post(new StateChangeEvent(prev, next));
    }

}
