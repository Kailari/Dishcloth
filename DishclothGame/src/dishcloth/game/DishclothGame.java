package dishcloth.game;

import dishcloth.api.GameInfo;
import dishcloth.api.abstractionlayer.content.IContentManager;
import dishcloth.api.events.GameEvents;
import dishcloth.api.util.memory.PointCache;
import dishcloth.api.world.WorldInfo;
import dishcloth.engine.AGame;
import dishcloth.api.abstractionlayer.events.EventHandler;
import dishcloth.engine.input.controllers.CameraController;
import dishcloth.api.abstractionlayer.rendering.IRenderer;
import dishcloth.engine.rendering.render2d.sprites.Anchor;
import dishcloth.engine.rendering.render2d.sprites.Sprite;
import dishcloth.engine.rendering.render2d.sprites.batch.SpriteBatch;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.text.bitmapfont.BitmapFont;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.api.util.Color;
import dishcloth.api.util.geom.Point;
import dishcloth.api.util.ANSIColor;
import dishcloth.engine.util.debug.Debug;
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
	public WorldInfo worldInfo;
	BitmapFont font;
	Texture uvGrid;
	Sprite sprite, sprite2, overlay;
	SpriteBatch spriteBatch;
	ShaderProgram spriteShader;
	CameraActor cameraActor;
	CameraController cameraController;
	Terrain terrain;
	Point position, position2;
	float t, angle;
	private Point pointStr1 = PointCache.getPoint( 0f, 0f );
	private Point pointStr2 = PointCache.getPoint( 100f, 100f );
	private Point pointStr3 = PointCache.getPoint( 200f, 200f );

	private Point pointZero = PointCache.getPoint( 0f, 0f );

	@EventHandler
	public void onPostInitializeEvent(GameEvents.GamePostInitializationEvent event) {
		Debug.log( "Creating new terrain!", this );
		terrain = new Terrain( 2, 3, 2 );
		Debug.log( "Terrain generated successfully!", this );

		worldInfo = new WorldInfo();
		worldInfo.terrain = terrain;
		GameInfo.worldInfo = worldInfo;
	}

	@Override
	public void initialize() {
		Debug.log( "Registering blocks in "
				           + ANSIColor.YELLOW
				           + "DishclothGame"
				           + ANSIColor.RESET, this );

		DishclothBlocks.registerBlocks();

		cameraActor = new CameraActor( getViewportCamera() );
		cameraController = new CameraController();
		cameraController.setActiveCamera( cameraActor );

		position = PointCache.getPoint( 0, 0 );
		position2 = PointCache.getPoint( 0, 0 );
	}

	@Override
	public void loadContent(IContentManager contentManager) {
		spriteBatch = new SpriteBatch();
		spriteShader = contentManager.loadContent( "/engine/shaders/sprite.shader" );
		
		uvGrid = contentManager.loadContent( "/engine/textures/debug/uv_checker.png" );
		sprite = new Sprite( uvGrid, 8, 8, 0, Anchor.CENTER );
		sprite2 = new Sprite( uvGrid, 1, 1, 0, Anchor.CENTER );

		overlay = new Sprite( contentManager.loadContent( "engine/textures/debug/800x600.png" ), 1, 1, 0, Anchor.CENTER );

		font = contentManager.loadContent( "/engine/fonts/default.fnt" );
	}

	@Override
	public void update(float delta) {
		cameraController.update();
		cameraActor.update();

		t += delta;

		sprite.setFrame( Math.round( t ) );

		position.setX( (float) Math.cos( Math.toRadians( angle ) ) * 200f );
		position.setY( (float) Math.sin( Math.toRadians( angle ) ) * 200f );
		position2.setX( -position.getX() );
		position2.setY( -position.getY() );

		angle = t * (360f / 10f);
	}

	@Override
	public void fixedUpdate() {
		cameraController.fixedUpdate();
		cameraActor.fixedUpdate();
	}

	@Override
	public void render(IRenderer renderer) {

		spriteBatch.begin( spriteShader, getViewportCamera(), renderer );

		sprite2.render( spriteBatch, pointZero, -angle, Color.WHITE );

		sprite.render( spriteBatch, position, 0f, Color.CYAN );
		sprite.render( spriteBatch, position2, -angle, Color.GREEN );

		sprite.render( spriteBatch, pointZero, angle );

		overlay.render( spriteBatch, pointZero );

		spriteBatch.end();

		terrain.render( renderer, getViewportCamera() );

		spriteBatch.begin( spriteShader, getViewportCamera(), renderer );

		//spriteBatch.queueString( font, pointStr1, Color.GREEN, "Hello World!" );
		//spriteBatch.queueString( font, pointStr2, Color.GREEN, "__--HelloWorld!ggjj" );
		//spriteBatch.queueString( font, pointStr3, Color.BLUE, "This is a...\nmulti-line string!" );

		spriteBatch.end();

	}

	@Override
	public void unloadContent() {
		spriteBatch.dispose();
	}

	@Override
	public void shutdown() {

	}
}
