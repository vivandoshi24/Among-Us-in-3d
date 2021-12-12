package de.vd24.amongus.event;

import de.vd24.amongus.model.GameState;

public class StateChangeEvent {

    private final GameState prev;
    private final GameState next;

    public StateChangeEvent(GameState prev, GameState next) {
        this.prev = prev;
        this.next = next;
    }

    public GameState getPrev() {
        return prev;
    }

    public GameState getNext() {
        return next;
    }
}
