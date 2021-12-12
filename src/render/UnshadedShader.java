package de.vd24.amongus.render;

import de.vd24.neko.api.Dimensions;
import de.vd24.neko.api.Inject;
import de.vd24.neko.api.UniformInject;
import de.vd24.neko.gl.Shader;
import de.vd24.neko.gl.Uniform;
import de.vd24.neko.render.Color;
import org.joml.Matrix4f;

public class UnshadedShader extends Shader {

    @Inject(UniformInject.ViewMatrix)
    public Uniform<Matrix4f> viewMatrix;

    @Inject(UniformInject.ProjMatrix)
    public Uniform<Matrix4f> projMatrix;

    public Uniform<Matrix4f> modelMatrix;

    @Dimensions(4)
    public Uniform<Color> color;

    public UnshadedShader() {
        super("Unshaded");
    }

}
