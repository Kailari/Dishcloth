package dishcloth.game;

import dishcloth.engine.AGame;
import dishcloth.engine.exception.ShaderUniformException;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.Renderer;
import dishcloth.engine.rendering.render2d.Sprite;
import dishcloth.engine.rendering.render2d.SpriteBatch;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vbo.VertexBufferObject;
import dishcloth.engine.rendering.vbo.shapes.Polygon;
import dishcloth.engine.rendering.vbo.shapes.Quad;
import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.util.math.Matrix4;
import dishcloth.engine.util.math.MatrixUtility;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUseProgram;

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

	SpriteBatch spriteBatch;
	Sprite sprite;
	Sprite sprite2;

	Point position;
	float t, angle;

	@Override
	public void initialize() {

		float halfW = screenWidth / 2f;
		float halfH = screenHeight / 2f;

		projectionMatrix = MatrixUtility.createOrthographicViewMatrix( -halfW, halfW,
		                                                               -halfH, halfH,
		                                                               -1.0f, 1.0f );
		viewMatrix = new Matrix4( Matrix4.IDENTITY );

		position = new Point( 0, 0 );
	}

	@Override
	public void loadContent() {
		spriteBatch = new SpriteBatch( new ShaderProgram( "sprite", "default" ) );
		Texture uvGrid = new Texture( "/textures/debug/uv_checker.png" );
		sprite = new Sprite( uvGrid, 8, 8, 0 );
		sprite2 = new Sprite( uvGrid, 1, 1, 0 );
	}

	@Override
	public void update(float delta) {
		t += delta;

		sprite.setFrame( Math.round( t ) );

		position.x = (float) Math.cos( t ) * 1.75f;
		position.y = (float) Math.sin( t ) * 1.75f;

		angle = t * (360f / 10f);
	}

	@Override
	public void fixedUpdate() {

	}

	@Override
	public void render(IRenderer renderer) {

		sprite2.render( spriteBatch, new Point( 0, 0 ), -angle );

		sprite.render( spriteBatch, position, 0f );
		sprite.render( spriteBatch, new Point( 0f, 0f ), angle );

		spriteBatch.render( renderer );
	}

	@Override
	public void unloadContent() {
		sprite.dispose();   // Both sprites use same texture, dispose only one of them
		spriteBatch.dispose();
	}

	@Override
	public void shutdown() {

	}

	@Override
	protected IRenderer createRenderer() {
		return new Renderer();
	}
}
