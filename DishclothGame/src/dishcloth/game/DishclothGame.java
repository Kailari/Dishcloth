package dishcloth.game;

import dishcloth.engine.AGame;
import dishcloth.engine.AGameEvents;
import dishcloth.engine.events.EventHandler;
import dishcloth.engine.input.controllers.CameraController;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.util.logger.ANSIColor;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.world.block.BlockRegistry;
import dishcloth.engine.world.level.Terrain;
import dishcloth.engine.world.objects.actor.CameraActor;
import dishcloth.game.world.blocks.BlockDirt;
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

	CameraActor cameraActor;
	CameraController cameraController;
	Terrain terrain;

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
	}

	@Override
	public void loadContent() {
	}

	@Override
	public void update(float delta) {
		cameraController.update();
		cameraActor.update();
	}

	@Override
	public void fixedUpdate() {
		cameraController.fixedUpdate();
		cameraActor.fixedUpdate();
	}

	@Override
	public void render(IRenderer renderer) {
		terrain.render( renderer );
	}

	@Override
	public void unloadContent() {
	}

	@Override
	public void shutdown() {

	}
}
