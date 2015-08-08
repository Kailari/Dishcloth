package dishcloth.api.abstractionlayer.world.level;

import dishcloth.api.abstractionlayer.world.block.IBlockID;
import dishcloth.api.world.block.ABlock;

/**
 * <b>ITerrain</b>
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 7.8.2015 at 12:06
 */
public interface ITerrain {
	void setBlock(int x, int y, ABlock block);

	void setBlock(int x, int y, IBlockID blockID);

	ABlock getBlock(int x, int y);

	IBlockID getBlockID(int x, int y);
}
