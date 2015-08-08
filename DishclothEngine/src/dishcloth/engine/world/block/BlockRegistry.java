package dishcloth.engine.world.block;

import dishcloth.api.abstractionlayer.world.block.IBlockID;
import dishcloth.api.world.block.ABlock;
import dishcloth.api.abstractionlayer.world.block.IBlockRegistry;

import java.util.List;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * BlockRegistry.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Simplified registry for registering/storing/getting blocks by IDs or whatever you want to do with them.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 11.6.2015
 */

public final class BlockRegistry {

	public static IBlockRegistry getInstance() {
		return BlockRegistrySingleton.getInstance();
	}

	public static ABlock getBlock(IBlockID blockID) {
		return getInstance().getBlock( blockID );
	}

	public static void registerBlock(ABlock block, String blockID, String modID) {
		// TODO: Blocks are registered one mod at a time. This allows us to auto-detect modID
		getInstance().registerBlock( block, blockID, modID );
	}

	public static void registerBlock(ABlock block,
	                                 String blockID, String modID,
	                                 String fallbackBlockID, String fallbackModID) {
		getInstance().registerBlock( block, blockID, modID, fallbackBlockID, fallbackModID );
	}

	public static List<ABlock> getRegisteredBlocks() {
		return getInstance().getRegisteredBlocks();
	}
}
