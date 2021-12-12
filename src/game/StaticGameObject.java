package de.vd24.amongus.game;

import de.vd24.neko.render.model.ModelBase;

public class StaticGameObject extends GameObject {

    public StaticGameObject(ModelBase model) {
        super(model);
    }

    @Override
    public boolean canInteract() {
        return false;
    }

}
