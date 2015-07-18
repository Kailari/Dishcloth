package dishcloth.engine.world.level;

import dishcloth.engine.rendering.IRenderable;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.util.geom.Rectangle;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.world.IUpdatable;
import dishcloth.engine.world.generation.generator.DefaultTerrainGenerator;

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

public class Terrain implements IUpdatable, IRenderable {

	private final int w, h;
	/**
	 * Used to convert array indices to chunk coordinates. (Chunk coordinate 0,0 is at the center of the map, while
	 * array index 0 is at the left bottom corner.)
	 */
	private final int xModifier, yModifier;
	private TerrainChunk[] chunks;

	public Terrain(int widthInChunks, int heightInChunks, int chunksAboveZeroLevel) {

		// Make sure that widthInChunks is dividable by 2 (for centering the world)
		this.w = (((float) widthInChunks % 2f == 0) ? widthInChunks : widthInChunks + 1);
		this.h = heightInChunks;
		this.chunks = new TerrainChunk[this.w * this.h];

		this.xModifier = -widthInChunks / 2;
		this.yModifier = -chunksAboveZeroLevel;

		DefaultTerrainGenerator generator = new DefaultTerrainGenerator();

		for (int x = 0; x < this.w; x++) {
			for (int y = 0; y < this.h; y++) {
				this.chunks[x + y * this.w] = generator.generate( x + this.xModifier, y + this.yModifier );
				Debug.log( "chunk generated: " + (x + this.xModifier) + ":" + (y + this.yModifier) + " == null?"
						           + (this.chunks[x + y * this.w] == null), this );
			}
		}
	}

	@Override
	public void update() {
	}

	@Override
	public void fixedUpdate() {
	}

	public void render(IRenderer renderer) {
		// Find Tiles on screen and queue them
		Rectangle viewport = TerrainRenderer.getCamera().getViewportBounds();
		Rectangle viewportChunkBounds = new Rectangle(
				viewport.x / TerrainChunk.BLOCK_SIZE,
				viewport.y / TerrainChunk.BLOCK_SIZE,
				viewport.w / TerrainChunk.BLOCK_SIZE,
				viewport.h / TerrainChunk.BLOCK_SIZE );
		for (TerrainChunk chunk : this.chunks) {
			// XXX: Where does the nullPointer come from?
			if (chunk.getRenderBounds().overlaps( viewport )) {
				chunk.getTiles()
						.getDataInRectangle( viewportChunkBounds ).forEach( TerrainRenderer::queueTileForRendering );
			}
		}

		// Render everything
		TerrainRenderer.render( renderer );
	}
}
