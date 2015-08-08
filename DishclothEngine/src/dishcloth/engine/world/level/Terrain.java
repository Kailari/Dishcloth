package dishcloth.engine.world.level;

import dishcloth.api.abstractionlayer.rendering.ICamera;
import dishcloth.api.abstractionlayer.rendering.IRenderer;
import dishcloth.api.abstractionlayer.world.block.IBlockID;
import dishcloth.api.abstractionlayer.world.level.ITerrain;
import dishcloth.api.util.memory.RectangleCache;
import dishcloth.api.world.block.ABlock;
import dishcloth.engine.rendering.render2d.TerrainRenderer;
import dishcloth.api.util.geom.Rectangle;
import dishcloth.engine.util.debug.Debug;
import dishcloth.engine.world.block.BlockIDHelper;
import dishcloth.engine.world.block.BlockRegistry;
import dishcloth.engine.world.generation.generator.ATerrainGenerator;
import dishcloth.engine.world.generation.generator.DefaultTerrainGenerator;
import dishcloth.engine.world.level.database.TerrainTreeCell;

import java.util.ArrayList;
import java.util.List;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Terrain.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 3.6.2015
 */

// TODO: Move terrain generation from constructor to generator methods and implement disk I/O
public class Terrain implements ITerrain {

	private static final int LEFT = 0;
	private static final int RIGHT = 1;
	private static final int TOP = 0;
	private static final int BOTTOM = 2;

	private static List<TerrainTreeCell> tmp = new ArrayList<>();

	private final int w, h;
	private final int halfW, hTop, hBot;
	private final ATerrainGenerator generator;
	private final boolean oddWidth;
	int counter;
	private TerrainChunk[][] chunks;

	/**
	 * Creates (and generates) a new world.
	 *
	 * @param widthInChunks        World width in chunks
	 * @param heightInChunks       World height in chunks.
	 * @param chunksAboveZeroLevel How many chunks there are above zero-level
	 */
	public Terrain(int widthInChunks, int heightInChunks, int chunksAboveZeroLevel) {

		this.w = widthInChunks;
		this.h = heightInChunks;

		this.halfW = (int) Math.ceil( widthInChunks / 2f );
		int leftW;
		if (widthInChunks % 2 != 0) {
			this.oddWidth = true;
			leftW = halfW - 1;
		} else {
			this.oddWidth = false;
			leftW = halfW;
		}
		this.hBot = heightInChunks - chunksAboveZeroLevel;
		this.hTop = chunksAboveZeroLevel;

		this.chunks = new TerrainChunk[4][];
		this.chunks[TOP + LEFT] = new TerrainChunk[leftW * this.hTop];
		this.chunks[BOTTOM + LEFT] = new TerrainChunk[leftW * this.hBot];
		this.chunks[TOP + RIGHT] = new TerrainChunk[halfW * this.hTop];
		this.chunks[BOTTOM + RIGHT] = new TerrainChunk[halfW * this.hBot];

		this.generator = new DefaultTerrainGenerator();

		for (int x = -this.halfW + (this.oddWidth ? 1 : 0); x < this.halfW; ++x) {
			for (int y = -hBot; y < this.hTop; ++y) {
				generateChunk( x, y );
			}
		}
	}

	private void generateChunk(int chunkX, int chunkY) {
		int target = (chunkX < 0 ? LEFT : RIGHT) + (chunkY < 0 ? BOTTOM : TOP);
		this.chunks[target][getChunkIndex( chunkX, chunkY )] = generator.generate( chunkX, chunkY );
		/*int chunkIndex = getChunkIndex( chunkX, chunkY );
		IBlockID id = BlockIDHelper.getBlockID( (short) (counter++ % 7) );
		Debug.log( "chunkXY:" + chunkX + ":" + chunkY + ", counter: " + counter + ", id: " + id, this );
		this.chunks[target][chunkIndex] = new TerrainChunk( chunkX, chunkY );
		for (int x = 0; x < TerrainChunk.CHUNK_SIZE; x++) {
			for (int y = 0; y < TerrainChunk.CHUNK_SIZE; y++) {
				this.chunks[target][chunkIndex].setBlock( chunkX * TerrainChunk.CHUNK_SIZE + x,
				                                          chunkY * TerrainChunk.CHUNK_SIZE + y,
				                                          id );
			}
		}*/
	}

	public TerrainChunk getChunk(int chunkX, int chunkY) {
		int target = (chunkX < 0 ? LEFT : RIGHT) + (chunkY < 0 ? BOTTOM : TOP);
		return this.chunks[target][getChunkIndex( chunkX, chunkY )];
	}

	private int getChunkIndex(int chunkX, int chunkY) {
		int x = (chunkX < 0 ? -(chunkX + 1) : chunkX);
		int y = (chunkY < 0 ? -(chunkY + 1) : chunkY);
		int w = (!this.oddWidth ? halfW : (chunkX < 0 ? halfW - 1 : halfW));
		return x + y * w;
	}

	@Override
	public void setBlock(int x, int y, ABlock block) {
		setBlock( x, y, block.getBlockID() );
	}

	@Override
	public void setBlock(int x, int y, IBlockID blockID) {
		int chunkX =
				(x < 0 ? (int) Math.ceil( x / TerrainChunk.CHUNK_SIZE )
						: (int) Math.floor( x / TerrainChunk.CHUNK_SIZE ));

		int chunkY =
				(y < 0 ? (int) Math.ceil( y / TerrainChunk.CHUNK_SIZE )
						: (int) Math.floor( y / TerrainChunk.CHUNK_SIZE ));
		getChunk( chunkX, chunkY ).setBlock( x, y, blockID );
	}

	@Override
	public ABlock getBlock(int x, int y) {
		return BlockRegistry.getBlock( getBlockID( x, y ) );
	}

	@Override
	public IBlockID getBlockID(int x, int y) {
		int chunkX =
				(x < 0 ? (int) Math.ceil( x / TerrainChunk.CHUNK_SIZE )
						: (int) Math.floor( x / TerrainChunk.CHUNK_SIZE ));

		int chunkY =
				(y < 0 ? (int) Math.ceil( y / TerrainChunk.CHUNK_SIZE )
						: (int) Math.floor( y / TerrainChunk.CHUNK_SIZE ));
		return getChunk( chunkX, chunkY ).getBlockID( x, y );
	}

	// TODO: Create TerrainBatcher to get rid of SpriteBatch limitations
	public void render(IRenderer renderer, ICamera camera) {
		// Find Tiles on screen and queue them

		// Get viewport bounds and scale it to match block coordinates
		Rectangle viewport = camera.getViewportRenderBounds();
		viewport.setX( viewport.getX() / TerrainChunk.BLOCK_SIZE );
		viewport.setY( viewport.getY() / TerrainChunk.BLOCK_SIZE );
		viewport.setW( viewport.getW() / TerrainChunk.BLOCK_SIZE );
		viewport.setH( viewport.getH() / TerrainChunk.BLOCK_SIZE );

		TerrainRenderer.beginRender( renderer, camera );

		for (int x = -this.halfW + (this.oddWidth ? 1 : 0); x < this.halfW; ++x) {
			for (int y = -hBot; y < this.hTop; ++y) {
				TerrainChunk chunk = getChunk( x, y );
				tmp = chunk.getCellsToRender( tmp, viewport );

				for (TerrainTreeCell cell : tmp) {
					TerrainRenderer.queueTileForRendering( cell, x, y );
				}
			}
		}

		RectangleCache.cacheRectangle( viewport );

		// Render everything
		TerrainRenderer.endRender();
	}
}
