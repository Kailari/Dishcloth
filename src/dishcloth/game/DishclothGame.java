package dishcloth.game;

import dishcloth.engine.AGame;
import dishcloth.engine.exception.ShaderUniformException;
import dishcloth.engine.rendering.Renderer;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vbo.VertexBufferObject;
import dishcloth.engine.rendering.vbo.shapes.Polygon;
import dishcloth.engine.rendering.vbo.shapes.Quad;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.util.math.Matrix4;
import dishcloth.engine.util.math.MatrixUtility;

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

	Matrix4 projectionMatrix;
	Matrix4 viewMatrix;

	@Override
	public void initialize() {

		float halfW = screenWidth / 2f;
		float halfH = screenHeight / 2f;
		float aspectRatio = (float)screenHeight / (float)screenWidth;

		projectionMatrix = MatrixUtility.createOrthographicViewMatrix( -1.0f, 1.0f,
		                                                               -1.0f * aspectRatio, 1.0f * aspectRatio,
		                                                               -1.0f, 1.0f );
		viewMatrix = new Matrix4( Matrix4.IDENTITY );
	}

	@Override
	public void loadContent() {
		shaderProgram = new ShaderProgram( "default", "default" );
		uvGrid = new Texture( "/textures/debug/uv_checker.png" );

		Polygon p = new Quad( 1f, 1f );

		vbo = new VertexBufferObject( p );

	}

	float t;
	@Override
	public void update(float delta) {

		t += delta;
		viewMatrix = MatrixUtility.createTranslation( (float)Math.sin( t ) * 0.5f, 0f, 0f )
				.multiply( MatrixUtility.createRotationZ( t * 45f ) );

	}

	@Override
	public void fixedUpdate() {

	}

	@Override
	public void render(Renderer renderer) {

		shaderProgram.bind();
		uvGrid.bind();

		try {
			shaderProgram.setUniformMat4( "mat_project", projectionMatrix );
			shaderProgram.setUniformMat4( "mat_view", viewMatrix );
		} catch (ShaderUniformException e) {
			Debug.logException( e, this );
		}

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
