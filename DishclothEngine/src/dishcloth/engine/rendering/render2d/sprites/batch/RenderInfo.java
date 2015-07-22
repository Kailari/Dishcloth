package dishcloth.engine.rendering.render2d.sprites.batch;

import dishcloth.engine.rendering.ICamera;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.shaders.ShaderProgram;

/**
 * RenderInfo.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 21.7.2015
 */

class RenderInfo {
	ShaderProgram shader;
	ICamera camera;
	IRenderer renderer;

	public RenderInfo(ShaderProgram shader, ICamera camera, IRenderer renderer) {
		this.shader = shader;
		this.camera = camera;
		this.renderer = renderer;
	}
}
