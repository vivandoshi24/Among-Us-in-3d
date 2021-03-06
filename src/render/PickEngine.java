package de.vd24.amongus.render;

import de.vd24.amongus.core.AmongUs;
import de.vd24.amongus.game.PlayerGameObject;
import de.vd24.neko.event.Events;
import de.vd24.neko.event.SizeChangedEvent;
import de.vd24.neko.gl.Framebuffer;
import de.vd24.neko.render.Color;
import org.greenrobot.eventbus.Subscribe;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL21.GL_PIXEL_PACK_BUFFER;

public class PickEngine {

    private Framebuffer pickBuffer;
    private int hoveringId;
    private int pbo;

    public void initialize() {
        Events.register(this);
        resizeBuffer();

        pbo = glGenBuffers();
        glBindBuffer(GL_PIXEL_PACK_BUFFER, pbo);
        glBufferData(GL_PIXEL_PACK_BUFFER, 4, GL_STREAM_READ);
        glBindBuffer(GL_PIXEL_PACK_BUFFER, 0);
    }

    @Subscribe
    public void resize(SizeChangedEvent e) {
        resizeBuffer();
    }

    private void resizeBuffer() {
        if (pickBuffer != null)
            pickBuffer.destroy();
        pickBuffer = Framebuffer.create(AmongUs.get().getWindow().getWidth() / 2, AmongUs.get().getWindow().getHeight() / 2)
                .withColorTexture(0, GL_RGB8, GL_RGB, GL_NEAREST, GL_UNSIGNED_BYTE)
                .withDepthBuffer()
                .finish();

    }

    public void render() {
        pickBuffer.bind();
        glBindBuffer(GL_PIXEL_PACK_BUFFER, pbo);
        retrievePixels();

        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        var strategy = new UnshadedShadingStrategy();
        AmongUs.get().getRenderManager().setShadingStrategy(strategy);
        for (var obj : AmongUs.get().getGameObjects()) {
            var radius = obj.getRadius();
            var distance = obj.getPosition().distance(AmongUs.get().getCamera().getInterpolatedPosition(AmongUs.get().getTimer().getPartial())) - radius;
            if (distance > 2)
                continue;

            strategy.color = obj.canInteract() ? new Color(obj.getId() / 255.0f, 0, 0) : Color.BLACK;
            obj.getModel().render();
        }


        glReadPixels(pickBuffer.getWidth() / 2, pickBuffer.getHeight() / 2, 1, 1, GL_BGR, GL_UNSIGNED_BYTE, 0);

        glBindBuffer(GL_PIXEL_PACK_BUFFER, 0);
        Framebuffer.unbind();
    }

    private void retrievePixels() {
        var buf = glMapBuffer(GL_PIXEL_PACK_BUFFER, GL_READ_ONLY);
        if (buf != null)
            hoveringId = buf.get(2);
        glUnmapBuffer(GL_PIXEL_PACK_BUFFER);
    }

    public int getHoveringId() {
        return hoveringId;
    }

}
