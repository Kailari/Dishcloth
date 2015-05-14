package dishcloth.engine.world;

import dishcloth.engine.rendering.IRenderer;

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

    void DoRender(IRenderer renderer, double alpha);

    void Render(IRenderer renderer);
}
