package dishcloth.game;

import dishcloth.engine.AGame;
import dishcloth.engine.rendering.Renderer;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vbo.VertexBufferObject;
import dishcloth.engine.rendering.vbo.shapes.Polygon;
import dishcloth.engine.rendering.vbo.shapes.Quad;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * DishclothGame.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 21.5.2015
 */

public class DishclothGame extends AGame {

	ShaderProgram shaderProgram;
	Texture uvGrid;
	VertexBufferObject vbo;

	@Override
	public void initialize() {

	}

	@Override
	public void loadContent() {
		shaderProgram = new ShaderProgram( "default", "default" );
		uvGrid = new Texture( "/textures/debug/uv_checker.png" );

		Polygon p = new Quad(1f, 1f);

		vbo = new VertexBufferObject( p );

	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void fixedUpdate() {

	}

	@Override
	public void render(Renderer renderer) {

		shaderProgram.bind();
		uvGrid.bind();

		vbo.render();

		uvGrid.unbind();
		shaderProgram.unbind();
	}

	@Override
	public void unloadContent() {
		shaderProgram.dispose();
		uvGrid.dispose();

		vbo.dispose();
	}

	@Override
	public void shutdown() {

	}
}
