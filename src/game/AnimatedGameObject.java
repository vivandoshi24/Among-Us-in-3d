package de.vd24.amongus.game;

import de.vd24.amongus.model.AnimationType;
import de.vd24.neko.render.model.ModelBase;
import de.vd24.neko.util.MathF;
import org.joml.Vector3f;

public class AnimatedGameObject extends GameObject {

    private float rot = MathF.rand() * 2 * MathF.PI;

    private final AnimationType animationType;

    public AnimatedGameObject(ModelBase model, AnimationType animationType) {
        super(model);
        this.animationType = animationType;
        model.setCascadeTransforms(true);
    }

    @Override
    public void onUpdate() {
        var transform = getModel().getTransform();
        transform.setRotationOrigin(getModel().getCenter());
        var vector = new Vector3f(0, 0, rot);
        transform.setRotation(vector);
        rot += animationType == AnimationType.Spin ? 0.15f : 0.075f;
    }

    @Override
    public boolean canInteract() {
        return false;
    }

}
