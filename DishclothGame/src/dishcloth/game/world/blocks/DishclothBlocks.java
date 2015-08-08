package dishcloth.game.world.blocks;

import dishcloth.api.world.block.ABlock;
import dishcloth.engine.world.block.BlockHelper;
import dishcloth.engine.world.block.BlockRegistry;
import dishcloth.game.DishclothGame;

/**
 * DishclothBlocks.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 20.7.2015
 */

public class DishclothBlocks {
	public static final ABlock DIRT = new BlockDirt();
	public static final ABlock GRASS = new BlockGrass();
	public static final ABlock CLAY = new BlockClay();
	public static final ABlock STONE = new BlockStone();
	public static final ABlock BRICKS = new BlockBricks();

	public static void registerBlocks() {
		BlockRegistry.registerBlock( DIRT, DishclothGame.DEFAULT_MOD_ID, "dirt" );
		BlockRegistry.registerBlock( GRASS, DishclothGame.DEFAULT_MOD_ID, "grass" );
		BlockRegistry.registerBlock( CLAY, DishclothGame.DEFAULT_MOD_ID, "clay" );
		BlockRegistry.registerBlock( STONE, DishclothGame.DEFAULT_MOD_ID, "stone" );
		BlockRegistry.registerBlock( BRICKS, DishclothGame.DEFAULT_MOD_ID, "bricks" );
	}
}
