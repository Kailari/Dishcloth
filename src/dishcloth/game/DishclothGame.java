package dishcloth.game;

import dishcloth.engine.AGame;
import dishcloth.engine.rendering.Renderer;
import dishcloth.engine.rendering.ShaderProgram;
import dishcloth.engine.rendering.VertexBufferObject;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

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
	VertexBufferObject vbo;

	@Override
	public void initialize() {

	}

	@Override
	public void loadContent() {
		shaderProgram = new ShaderProgram( "default", "default" );

		float[] vertices = new float[]{
				+0.0f, +0.5f,
				-0.5f, -0.5f,
				+0.5f, -0.5f
		};

		int[] indices = new int[] {
			0, 2, 1
		};

		vbo = new VertexBufferObject( vertices, indices );
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

		vbo.render();

		shaderProgram.unbind();
	}

	@Override
	public void unloadContent() {
		shaderProgram.dispose();

		vbo.dispose();
	}

	@Override
	public void shutdown() {

	}
}
