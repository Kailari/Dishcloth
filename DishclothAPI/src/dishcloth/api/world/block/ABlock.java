package dishcloth.api.world.block;

import dishcloth.api.abstractionlayer.world.block.IBlockID;

/**
 * <b>ABlock</b>
 * <p>
 * Block abstraction
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 7.8.2015 at 10:41
 */
public abstract class ABlock {

	private IBlockID blockID;
	protected int frameID;

	public final IBlockID getBlockID() {
		return blockID;
	}

	public int getFrameID() {
		return frameID;
	}

	public abstract String getBlockTextureFilename();
}
