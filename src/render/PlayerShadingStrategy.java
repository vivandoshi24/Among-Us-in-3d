package de.vd24.amongus.render;

import de.vd24.amongus.core.AmongUs;
import de.vd24.amongus.model.Player;
import de.vd24.amongus.model.PlayerRole;
import de.vd24.amongus.model.Sabotage;
import de.vd24.amongus.model.SessionConfig;
import de.vd24.neko.render.model.ModelPart;
import de.vd24.neko.render.shading.AbstractGeometryShadingStrategy;
import de.vd24.neko.res.cache.ShaderProvider;
import de.vd24.neko.res.cache.TextureProvider;

public class PlayerShadingStrategy extends AbstractGeometryShadingStrategy {

    private final Player player;

    public PlayerShadingStrategy(Player player) {
        this.player = player;
    }

    @Override
    public boolean prepareRender(ModelPart part, ShaderProvider shaders, TextureProvider textures) {
        var mat = part.getMaterial();

        var shader = shaders.getShader(PlayerShader.class);
        shader.bind();
        shader.modelColor.set(mat.getDiffuseColor());
        shader.modelMatrix.set(part.getTransform().getMatrix());
        shader.hasTexture.set(mat.hasTexture());
        shader.playerColor.set(player.color.getValue());
        shader.visionRadius.set(getVisionRadius());
        return true;
    }

    private float getVisionRadius() {
        var session = AmongUs.get().getSession();
        var me = session.getMyself();
        if (!me.alive)
            return 100.0f;
        else if (session.currentSabotage == Sabotage.Lights)
            return 0.75f;
        else if (me.role == PlayerRole.Impostor)
            return session.getConfig().getImpostorVision() * SessionConfig.PLAYER_VISION_BASE_RADIUS;
        else
            return session.getConfig().getPlayerVision() * SessionConfig.PLAYER_VISION_BASE_RADIUS;
    }

}
