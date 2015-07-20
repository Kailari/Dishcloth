package dishcloth.game;

import dishcloth.engine.AGame;
import dishcloth.engine.AGameEvents;
import dishcloth.engine.events.EventHandler;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.util.logger.ANSIColor;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.world.block.BlockRegistry;
import dishcloth.engine.world.level.Terrain;
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
		BlockRegistry.registerBlock( new BlockDirt(), "dishcloth", "dirt" );
	}

	@Override
	public void loadContent() {
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void fixedUpdate() {
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
