package de.vd24.amongus.render;

import de.vd24.amongus.core.AmongUs;
import de.vd24.neko.render.overlay.IOverlay;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class CamOverlay implements IOverlay {

    private final CamShader shader;

    public CamOverlay() {
        shader = AmongUs.get().getShaderProvider().getShader(CamShader.class);
    }

    @Override
    public void setupShader() {
        shader.bind();
        shader.time.set((float) glfwGetTime());
    }
}
