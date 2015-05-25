package dishcloth.game;

import dishcloth.engine.AGame;
import dishcloth.engine.exception.ShaderUniformException;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.Renderer;
import dishcloth.engine.rendering.render2d.Anchor;
import dishcloth.engine.rendering.render2d.Sprite;
import dishcloth.engine.rendering.render2d.SpriteBatch;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vbo.VertexBufferObject;
import dishcloth.engine.rendering.vbo.shapes.Polygon;
import dishcloth.engine.rendering.vbo.shapes.Quad;
import dishcloth.engine.util.Color;
import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.geom.Rectangle;
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
	Sprite overlay;

	Point position;
	float t, angle;

	@Override
	public void initialize() {

		float halfW = screenWidth / 2f;
		float halfH = screenHeight / 2f;

		projectionMatrix = MatrixUtility.createOrthographicViewMatrix( -halfW, halfW,
		                                                               -halfH, halfH,
		                                                               -1.0f, 1.0f );
		viewMatrix = Matrix4.identity();

		position = new Point( 0, 0 );
	}

	@Override
	public void loadContent() {
		spriteBatch = new SpriteBatch( new ShaderProgram( "sprite", "default" ) );

		Texture uvGrid = new Texture( "/textures/debug/uv_checker.png" );
		sprite = new Sprite( uvGrid, 8, 8, 0, Anchor.CENTER );
		sprite2 = new Sprite( uvGrid, 1, 1, 0 );

		overlay = new Sprite( new Texture( "/textures/debug/800x600.png" ), 1, 1, 0 );
	}

	@Override
	public void update(float delta) {
		t += delta;

		sprite.setFrame( Math.round( t ) );

		position.x = (float) Math.cos( Math.toRadians( angle ) ) * 200f;
		position.y = (float) Math.sin( Math.toRadians( angle ) ) * 200f;

		angle = t * (360f / 10f);
	}

	@Override
	public void fixedUpdate() {

	}

	@Override
	public void render(IRenderer renderer) {

		sprite2.render( spriteBatch, new Point( -512f, 512f ), -angle, Color.WHITE, new Point( 512f, -512f ) );

		// "sprite" uses default origin set as "Anchor.CENTER", thus no pivot parameter is needed
		sprite.render( spriteBatch, new Point( -64 + position.x, 64 + position.y ), 0f, Color.CYAN );
		sprite.render( spriteBatch, new Point( -64 - position.x, 64 - position.y ), -angle, Color.GREEN );

		sprite.render( spriteBatch, new Point( -64f, 64f ), angle );

		// "overlay" has default pivot "TOPLEFT" and uses manual pivot (400, -300)
		overlay.render( spriteBatch, new Point( -400f, 300 ), 0f, Color.WHITE, new Point( 400, -300 ) );

		spriteBatch.render( renderer );
	}

	@Override
	public void unloadContent() {
		sprite.dispose();   // Both sprites use the same texture, dispose only one of them
		overlay.dispose();
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
