package de.vd24.amongus.game;

import de.vd24.amongus.core.AmongUs;
import de.vd24.amongus.model.Player;
import de.vd24.amongus.model.PlayerRole;
import de.vd24.amongus.net.NetMessage;
import de.vd24.amongus.render.PlayerShadingStrategy;
import de.vd24.neko.render.Color;
import de.vd24.neko.render.model.ModelBase;
import de.vd24.neko.render.model.ModelInstance;
import de.vd24.neko.res.ModelLoader;
import de.vd24.neko.util.Log;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerGameObject extends GameObject {

    private static ModelBase baseModel;

    private final Player trackedPlayer;

    private final PlayerShadingStrategy shadingStrategy;

    public PlayerGameObject(Player trackedPlayer) {
        super(newModelInstance());
        shadingStrategy = new PlayerShadingStrategy(trackedPlayer);
        getModel().setCascadeTransforms(true);
        getModel().overwriteShadingStrategy(shadingStrategy);
        this.trackedPlayer = trackedPlayer;
    }

    private void setPose(Vector3f position, float rotation) {


        if (position == null) position = new Vector3f();

        var transform = getModel().getTransform();
        transform.setScale(new Vector3f(0.25f, 0.25f, 0.25f));
        transform.setTranslation(new Vector3f(position.x, position.y - 0.05f, position.z));
        transform.setRotationOrigin(getModel().getCenter());
        transform.setRotation(new Vector3f(0, rotation, 0));
    }

    @Override
    public void onUpdate() {
        setPose(trackedPlayer.position, trackedPlayer.rotation);
    }

    @Override
    public void onAdded() {
        AmongUs.get().getScene().addModel(getModel());
    }

    @Override
    public void onRemoved() {
        AmongUs.get().getScene().removeModel(getModel());
    }

    @Override
    public boolean canInteract() {
        var me = AmongUs.get().getSession().getMyself();
        return me.alive && me.getRole() == PlayerRole.Impostor;
    }

    @Override
    public void onClick() {
        super.onClick();
        var me = AmongUs.get().getSession().getMyself();
        if (!me.canKill())
            return;
        AmongUs.get().getSoundFX().play("ImpostorKill.ogg");
        AmongUs.get().getClient().sendMessage(new NetMessage.Kill(trackedPlayer.id));
        me.resetKillCooldown();
    }

    @Override
    public Color getHighlightColor() {
        return new Color(1,0,0,0.75f);
    }

    private static ModelBase newModelInstance() {
        if (baseModel == null)
            baseModel = ModelLoader.loadModel("Astronaut.obj");
        return new ModelInstance(baseModel);
    }

    public Player getTrackedPlayer() {
        return trackedPlayer;
    }

    @Override
    public Vector3f getPosition() {
        return trackedPlayer.position;
    }
    @Override
    public float getRadius() {
        return super.getRadius() * 0.25f;
    }
}
