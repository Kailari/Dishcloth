package dishcloth.engine.world.level;

import dishcloth.engine.util.geom.Rectangle;
import dishcloth.engine.util.quadtree.QuadTree;
import dishcloth.engine.world.Tile;
import dishcloth.engine.world.block.IBlock;

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

	// XXX: EXTREMELY TEMPORARY
	public static final float BLOCK_SIZE = 1f;
	public static final int CHUNK_SIZE = 256;

	private int x;
	private int y;

	private QuadTree<Tile> tiles;

	public TerrainChunk(int x, int y) {
		this.tiles = new QuadTree<>( 1, -1, new TerrainQuadTreeCell( null, new Rectangle( x, y, CHUNK_SIZE, CHUNK_SIZE ), -1 ) );
		this.x = x;
		this.y = y;
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

	public void setBlock(int x, int y, IBlock block) {
		// Tile size can be anything, it will be overridden without getting read once.
		this.tiles.addData( new Tile( x, y, 0, 0, block.getBlockID() ) );
	}

	public Tile getTile(int x, int y) {
		return this.tiles.getDataAt( x, y ).get( 0 );
	}
}
