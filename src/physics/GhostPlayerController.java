package de.vd24.amongus.physics;

public class GhostPlayerController extends BasePlayerController {

    @Override
    boolean mayFly() {
        return true;
    }

    @Override
    public float getSpeed() {
        return 0.2f;
    }

}
