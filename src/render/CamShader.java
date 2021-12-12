package de.vd24.amongus.render;

import de.vd24.neko.gl.Shader;
import de.vd24.neko.gl.Uniform;

public class CamShader extends Shader {

    public Uniform<Float> time;

    public CamShader() {
        super("PostVert.glsl", "PostCam.glsl");
    }

}
