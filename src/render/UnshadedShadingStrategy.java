package de.vd24.amongus.render;

import de.vd24.neko.render.Color;
import de.vd24.neko.render.model.ModelPart;
import de.vd24.neko.render.shading.IShadingStrategy;
import de.vd24.neko.res.cache.ShaderProvider;
import de.vd24.neko.res.cache.TextureProvider;

public class UnshadedShadingStrategy implements IShadingStrategy {

    public Color color;

    @Override
    public boolean prepareRender(ModelPart modelPart, ShaderProvider shaderProvider, TextureProvider textureProvider) {
        var shader = shaderProvider.getShader(UnshadedShader.class);
        shader.bind();
        shader.color.set(color);
        shader.modelMatrix.set(modelPart.getTransform().getMatrix());
        return true;
    }

    @Override
    public boolean mayOverwrite() {
        return false;
    }

}
