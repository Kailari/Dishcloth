package dishcloth.engine.world.block;

import dishcloth.engine.events.IEvent;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.render2d.SpriteBatch;
import dishcloth.engine.world.level.ITile;
import dishcloth.engine.world.level.TerrainRenderer;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * ABlock.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 11.6.2015
 */

public abstract class ABlock {

	private BlockID blockID;
	private int frameID;

	public BlockID getBlockID() {
		return blockID;
	}

	final void setBlockID(BlockID id) {
		this.blockID = id;
	}

	public int getFrameID() {
		return frameID;
	}

	final void setFrameID(int id) {
		this.frameID = id;
	}

	public void render(ITile tile) {
		TerrainRenderer.renderTile( getFrameID(), tile );
	}

	public abstract String getBlockTextureFilename();
}
