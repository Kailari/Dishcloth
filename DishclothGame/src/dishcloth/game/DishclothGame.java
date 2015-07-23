package dishcloth.game;

import dishcloth.engine.AGame;
import dishcloth.engine.AGameEvents;
import dishcloth.engine.events.EventHandler;
import dishcloth.engine.input.controllers.CameraController;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.render2d.sprites.Anchor;
import dishcloth.engine.rendering.render2d.sprites.Sprite;
import dishcloth.engine.rendering.render2d.sprites.batch.SpriteBatch;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vbo.ColorTextureVertex;
import dishcloth.engine.util.Color;
import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.logger.ANSIColor;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.util.math.Vector2;
import dishcloth.engine.world.block.BlockRegistry;
import dishcloth.engine.world.level.Terrain;
import dishcloth.engine.world.objects.actor.CameraActor;
import dishcloth.game.world.blocks.DishclothBlocks;

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
	public static final String DEFAULT_MOD_ID = "dishcloth";

	Texture uvGrid;
	Sprite sprite, sprite2, overlay;
	
	SpriteBatch<ColorTextureVertex> spriteBatch;
	ShaderProgram spriteShader;
	
	CameraActor cameraActor;
	CameraController cameraController;
	Terrain terrain;

	Point position;
	float t, angle;

	@EventHandler
	public void onPostInitializeEvent(AGameEvents.GamePostInitializationEvent event) {
		Debug.log( "Creating new terrain!", this );
		terrain = new Terrain( 2, 3, 2 );
		Debug.log( "Terrain generated successfully!", this );
	}

	@Override
	public void initialize() {
		Debug.log( "Registering blocks in "
				           + ANSIColor.YELLOW
				           + "DishclothGame"
				           + ANSIColor.RESET, this );

		BlockRegistry.registerBlock( DishclothBlocks.DIRT, DEFAULT_MOD_ID, "dirt" );
		BlockRegistry.registerBlock( DishclothBlocks.GRASS, DEFAULT_MOD_ID, "grass" );
		BlockRegistry.registerBlock( DishclothBlocks.CLAY, DEFAULT_MOD_ID, "clay" );
		BlockRegistry.registerBlock( DishclothBlocks.STONE, DEFAULT_MOD_ID, "stone" );
		BlockRegistry.registerBlock( DishclothBlocks.BRICKS, DEFAULT_MOD_ID, "bricks" );

		cameraActor = new CameraActor( getViewportCamera() );
		cameraController = new CameraController();
		cameraController.setActiveCamera( cameraActor );

		position = new Point( 0, 0 );
	}

	@Override
	public void loadContent() {
		spriteBatch = new SpriteBatch<>(ColorTextureVertex.class);
		spriteShader = new ShaderProgram( "/engine/shaders/sprite", "/engine/shaders/default" );
		
		Texture uvGrid = new Texture( "engine/textures/debug/uv_checker.png" );
		sprite = new Sprite( uvGrid, 8, 8, 0, Anchor.CENTER );
		sprite2 = new Sprite( uvGrid, 1, 1, 0, Anchor.CENTER );

		overlay = new Sprite( new Texture( "engine/textures/debug/800x600.png" ), 1, 1, 0, Anchor.CENTER );
	}

	@Override
	public void update(float delta) {
		cameraController.update();
		cameraActor.update();

		t += delta;

		sprite.setFrame( Math.round( t ) );

		position.x = (float) Math.cos( Math.toRadians( angle ) ) * 200f;
		position.y = (float) Math.sin( Math.toRadians( angle ) ) * 200f;

		angle = t * (360f / 10f);
	}

	@Override
	public void fixedUpdate() {
		cameraController.fixedUpdate();
		cameraActor.fixedUpdate();
	}

	@Override
	public void render(IRenderer renderer) {

		/*spriteBatch.begin( spriteShader, getViewportCamera(), renderer );

		sprite2.render( spriteBatch, new Point( 0f, 0f ), -angle, Color.WHITE );

		sprite.render( spriteBatch, new Point( position.x, position.y ), 0f, Color.CYAN );
		sprite.render( spriteBatch, new Point( -position.x, -position.y ), -angle, Color.GREEN );

		sprite.render( spriteBatch, new Point( 0f, 0f ), angle );

		overlay.render( spriteBatch, new Point( 0f, 0f ), 0f, Color.WHITE );

		spriteBatch.end();*/

		terrain.render( renderer, getViewportCamera() );
	}

	@Override
	public void unloadContent() {
		sprite.dispose();   // Both sprites use the same texture, dispose only one of them
		overlay.dispose();
	}

	@Override
	public void shutdown() {

	}
}
