package dishcloth.engine.rendering.render2d;

import dishcloth.engine.rendering.IRenderable;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.textures.Texture;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Sprite.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 21.5.2015
 */

public class Sprite implements IRenderable {
	private Texture atlas;

	private int nRows, nColumns, frameW, frameH;

	@Override
	public void Render(IRenderer renderer) {

	}
}
