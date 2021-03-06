package de.vd24.amongus.game;

import de.vd24.amongus.core.AmongUs;
import de.vd24.neko.render.Color;
import de.vd24.neko.render.model.ModelBase;
import org.joml.Vector3f;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class GameObject {

    private static final AtomicInteger idCounter = new AtomicInteger(0);

    private final int id;

    private final ModelBase model;

    public GameObject(ModelBase model) {
        this.id = idCounter.incrementAndGet();
        this.model = model;
        this.model.setTag(id);
    }

    public int getId() {
        return id;
    }

    public ModelBase getModel() {
        return model;
    }

    public abstract boolean canInteract();

    public void onAdded() {
    }

    public void onRemoved() {
    }

    public void onUpdate() {
    }

    public void onClick() {
    }

    public boolean isHighlighted() {
        return canInteract() && isMouseOver();
    }

    public Color getHighlightColor() {
        return isMouseOver() ? new Color(1, 1, 0, 0.65f) : new Color(1, 1, 1, 0.55f);
    }

    public boolean isMouseOver() {
        return AmongUs.get().getPickEngine().getHoveringId() == id;
    }

    public float getRadius() {
        return model.getSize().length() / 2;
    }

    public Vector3f getPosition() {
        return model.getTransformedCenter();
    }

}
