package dishcloth.api.abstractionlayer.world.block;

import dishcloth.api.world.block.ABlock;

import java.util.List;

/**
 * <b>IBlockRegistry</b>
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 7.8.2015 at 10:57
 */
public interface IBlockRegistry {
	ABlock getBlock(IBlockID blockID);

	void registerBlock(ABlock block, String blockID, String modID);

	void registerBlock(ABlock block,
	                   String blockID, String modID,
	                   String fallbackBlockID, String fallbackModID);

	List<ABlock> getRegisteredBlocks();
}
