package dishcloth.engine.world.level;

import dishcloth.api.abstractionlayer.world.block.IBlockID;
import dishcloth.api.util.geom.Rectangle;
import dishcloth.api.util.math.DishMath;
import dishcloth.api.util.memory.RectangleCache;
import dishcloth.api.world.block.ABlock;
import dishcloth.engine.world.block.BlockRegistry;
import dishcloth.engine.world.level.database.TerrainTree;
import dishcloth.engine.world.level.database.TerrainTreeCell;

import java.util.List;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * TerrainChunk.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 24.6.2015
 */

public class TerrainChunk {

	public static final float BLOCK_SIZE = 16f;
	public static final int CHUNK_SIZE = 256;

	private final int x;
	private final int y;

	private TerrainTree terrainTree;

	public TerrainChunk(int x, int y) {
		this.x = x;
		this.y = y;

		this.terrainTree = new TerrainTree();
	}

	public int getChunkX() {
		return x;
	}

	public int getChunkY() {
		return y;
	}

	public int getTileX() {
		return x * CHUNK_SIZE;
	}

	public int getTileY() {
		return y * CHUNK_SIZE;
	}

	public float getRenderX() {
		return getTileX() * BLOCK_SIZE;
	}

	public float getRenderY() {
		return getTileY() * BLOCK_SIZE;
	}

	public void setBlock(int x, int y, ABlock block) {
		setBlock( x, y, block.getBlockID() );
	}

	public void setBlock(int x, int y, IBlockID blockID) {
		x -= this.getTileX();
		y -= this.getTileY();

		this.terrainTree.setBlockID( (short) x, (short) y, blockID );
	}

	public IBlockID getBlockID(int x, int y) {
		x -= this.getTileX();
		y -= this.getTileY();
		return this.terrainTree.getBlockID( (short) x, (short) y );
	}

	public List<TerrainTreeCell> getCellsToRender(List<TerrainTreeCell> listToReuse, Rectangle viewport) {
		listToReuse.clear();

		float x = viewport.getX() - this.getTileX();
		float y = this.getTileY() - this.getTileY();

		if (x + viewport.getW() < 0 || x > TerrainChunk.CHUNK_SIZE
				|| y + viewport.getH() < 0 || y > TerrainChunk.CHUNK_SIZE) {
			return listToReuse;
		} else {
			short w = (short) Math.min( 256 - x, Math.ceil( viewport.getW() ) );
			short h = (short) Math.min( 256 - y, Math.ceil( viewport.getH() ) );

			return this.terrainTree.getCellsInRectangle(
					this.x,
					this.y,
					x,
					y,
					w,
					h );
		}
	}
}
