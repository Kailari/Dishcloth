package dishcloth.game;

import dishcloth.engine.AGame;
import dishcloth.engine.events.EventAction;
import dishcloth.engine.events.EventHandler;
import dishcloth.engine.events.events.KeyEvent;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.render2d.Anchor;
import dishcloth.engine.rendering.render2d.Sprite;
import dishcloth.engine.rendering.render2d.SpriteBatch;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.util.Color;
import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.world.block.BlockRegistry;
import dishcloth.engine.world.level.Terrain;
import dishcloth.game.world.DishclothGameEvents;
import dishcloth.game.world.blocks.BlockDirt;

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

	private static final float CAMERA_SPEED = 10f;

	SpriteBatch spriteBatch;
	Sprite sprite, sprite2, overlay;

	Terrain terrain;

	EventAction<KeyEvent> moveCameraLeft = eventTrigger ->
			cameraVelocityX = -CAMERA_SPEED * (eventTrigger.wasRepeat() ? 1f : 0f);

	EventAction<KeyEvent> moveCameraRight = eventTrigger ->
			cameraVelocityX = CAMERA_SPEED * (eventTrigger.wasRepeat() ? 1f : 0f);

	EventAction<KeyEvent> moveCameraUp = eventTrigger ->
			cameraVelocityY = CAMERA_SPEED * (eventTrigger.wasRepeat() ? 1f : 0f);

	EventAction<KeyEvent> moveCameraDown = eventTrigger ->
			cameraVelocityY = -CAMERA_SPEED * (eventTrigger.wasRepeat() ? 1f : 0f);

	Point position;
	float t, angle, cameraVelocityX, cameraVelocityY;

	@Override
	public void initialize() {
		position = new Point( 0, 0 );

		EventHandler.registerEvents( DishclothGameEvents.class );

		EventHandler.bindAction( DishclothGameEvents.leftArrowKeyEvent, moveCameraLeft );
		EventHandler.bindAction( DishclothGameEvents.rightArrowKeyEvent, moveCameraRight );
		EventHandler.bindAction( DishclothGameEvents.upArrowKeyEvent, moveCameraUp );
		EventHandler.bindAction( DishclothGameEvents.downArrowKeyEvent, moveCameraDown );
	}

	@Override
	public void loadContent() {
		spriteBatch = new SpriteBatch( new ShaderProgram( "engine/shaders/sprite", "engine/shaders/default" ),
		                               this );

		Texture uvGrid = new Texture( "engine/textures/debug/uv_checker.png" );
		sprite = new Sprite( uvGrid, 8, 8, 0, Anchor.CENTER );
		sprite2 = new Sprite( uvGrid, 1, 1, 0, Anchor.CENTER );

		overlay = new Sprite( new Texture( "engine/textures/debug/800x600.png" ), 1, 1, 0, Anchor.CENTER );

		BlockRegistry.registerBlock( new BlockDirt(), "dishcloth", "dirt" );
		/*BlockRegistry.registerBlock( new BlockDirt(), "dishcloth", "dirt2" );
		BlockRegistry.registerBlock( new BlockDirt(), "dishcloth", "dirt3" );
		BlockRegistry.registerBlock( new BlockDirt(), "dishcloth", "dirt4" );
		BlockRegistry.registerBlock( new BlockDirt(), "dishcloth", "dirt5" );*/
	}

	@Override
	public void update(float delta) {
		if (terrain == null) {
			terrain = new Terrain( 2, 10, 3 );
			Debug.log( "Terrain generated successfully!", this );
		}

		t += delta;

		sprite.setFrame( Math.round( t ) );

		position.x = (float) Math.cos( Math.toRadians( angle ) ) * 200f;
		position.y = (float) Math.sin( Math.toRadians( angle ) ) * 200f;

		angle = t * (360f / 10f);
	}

	@Override
	public void fixedUpdate() {
		Point cameraPosition = getViewportCamera().getPosition();
		cameraPosition.x += cameraVelocityX;
		cameraPosition.y += cameraVelocityY;

		getViewportCamera().setPosition( cameraPosition );
	}

	@Override
	public void render(IRenderer renderer) {

		sprite2.render( spriteBatch, new Point( 0f, 0f ), -angle, Color.WHITE );

		sprite.render( spriteBatch, new Point( position.x, position.y ), 0f, Color.CYAN );
		sprite.render( spriteBatch, new Point( -position.x, -position.y ), -angle, Color.GREEN );

		sprite.render( spriteBatch, new Point( 0f, 0f ), angle );

		overlay.render( spriteBatch, new Point( 0f, 0f ), 0f, Color.WHITE );

		spriteBatch.render( renderer );

		terrain.render( renderer );
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
}
