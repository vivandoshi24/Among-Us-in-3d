package de.vd24.amongus.game;

import de.vd24.amongus.model.Location;
import de.vd24.neko.render.model.ModelBase;

public class DoorGameObject extends GameObject {

    private final ModelBase secondHalf;
    private final Location location;
    private final int index;

    public DoorGameObject(ModelBase firstHalf, ModelBase secondHalf, Location location, int index) {
        super(firstHalf);
        this.secondHalf = secondHalf;
        this.location = location;
        this.index = index;
    }

    @Override
    public boolean canInteract() {
        return false;
    }

}
