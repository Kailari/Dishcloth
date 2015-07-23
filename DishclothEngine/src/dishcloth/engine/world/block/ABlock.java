package dishcloth.engine.world.block;

import dishcloth.engine.rendering.render2d.sprites.batch.SpriteBatch;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.util.Color;
import dishcloth.engine.util.geom.Rectangle;
import dishcloth.engine.util.math.MatrixUtility;
import dishcloth.engine.util.math.Vector2;
import dishcloth.engine.world.level.ITile;
import dishcloth.engine.rendering.render2d.TerrainRenderer;
import dishcloth.engine.world.level.TerrainChunk;

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

	public void render(SpriteBatch spriteBatch, ITile tile) {
		if (tile.getBlockID() == null) {
			return;
		}

		Texture texture = BlockTextureAtlas.getTexture();

		int atlasSize = Math.round( texture.getWidth() / TerrainChunk.BLOCK_SIZE );

		int frameID = getFrameID();
		int row = (int) Math.floor( (float) frameID / (float) atlasSize );
		int column = frameID % atlasSize;

		Rectangle source = new Rectangle( 0f,
		                                  0f,
		                                  tile.getSize() * texture.getWidth(), // A bit hacky, eh?
		                                  tile.getSize() * texture.getHeight() );

		Rectangle destination = new Rectangle( tile.getX() * TerrainChunk.BLOCK_SIZE,
		                                       tile.getY() * TerrainChunk.BLOCK_SIZE,
		                                       tile.getSize() * TerrainChunk.BLOCK_SIZE,
		                                       tile.getSize() * TerrainChunk.BLOCK_SIZE );

		spriteBatch.queue( texture,
		                   destination,
		                   source,
		                   0f,
		                   Color.WHITE,
		                   Vector2.zero(),
		                   (float) column / (float) atlasSize,
		                   (float) row / (float) atlasSize );
	}

	public abstract String getBlockTextureFilename();
}
