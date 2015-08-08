package dishcloth.engine.rendering;

import dishcloth.api.abstractionlayer.rendering.IRenderer;

/**
 * ********************************************************************************************************************
 * IRenderable.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 14.5.2015
 */
public interface IRenderable {
    void render(IRenderer renderer);
}
