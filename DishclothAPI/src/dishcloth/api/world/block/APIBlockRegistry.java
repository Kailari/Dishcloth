package dishcloth.api.world.block;

import dishcloth.api.abstractionlayer.world.block.IBlockID;
import dishcloth.api.abstractionlayer.world.block.IBlockRegistry;

import java.util.List;

/**
 * <b>APIBlockRegistry</b>
 * <p>
 * API-level BlockRegistry wrapper to provide BlockRegistry for modules.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 7.8.2015 at 10:56
 */

public class APIBlockRegistry {
	private static IBlockRegistry registry;

	public static void initialize(IBlockRegistry registry) {
		APIBlockRegistry.registry = registry;
	}

	public static ABlock getBlock(IBlockID blockID) {
		return registry.getBlock( blockID );
	}

	public static List<ABlock> getRegisteredBlocks() {
		return registry.getRegisteredBlocks();
	}

	public static void registerBlock(ABlock block, String blockID, String modID, String fallbackBlockID, String fallbackModID) {
		registry.registerBlock( block, blockID, modID, fallbackBlockID, fallbackModID );
	}

	public static void registerBlock(ABlock block, String blockID, String modID) {
		registry.registerBlock( block, blockID, modID );
	}
}
