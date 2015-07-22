package dishcloth.engine.world.level;

import dishcloth.engine.rendering.ICamera;
import dishcloth.engine.rendering.IRenderable;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.render2d.TerrainRenderer;
import dishcloth.engine.rendering.render2d.sprites.batch.SpriteBatch;
import dishcloth.engine.util.geom.Rectangle;
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

// TODO: Move terrain generation from constructor to generator methods and implement disk I/O
public class Terrain {

	private final int w, h;
	/**
	 * Used to convert array indices to chunk coordinates. (Chunk coordinate 0,0 is at the center of the map, while
	 * array index 0 is at the left bottom corner.)
	 */
	private final int xModifier, yModifier;
	private TerrainChunk[] chunks;

	/**
	 * Creates (and generates) a new world.
	 *
	 * @param widthInChunks        World width in chunks
	 * @param heightInChunks       World height in chunks.
	 * @param chunksAboveZeroLevel How many chunks there are above zero-level
	 */
	public Terrain(int widthInChunks, int heightInChunks, int chunksAboveZeroLevel) {

		// Make sure that widthInChunks is dividable by 2 (for centering the world)
		this.w = (((float) widthInChunks % 2f == 0) ? widthInChunks : widthInChunks + 1);
		this.h = heightInChunks;
		this.chunks = new TerrainChunk[this.w * this.h];

		this.xModifier = -widthInChunks / 2;
		// TODO: Figure this thing out
		this.yModifier = -(heightInChunks - chunksAboveZeroLevel);

		DefaultTerrainGenerator generator = new DefaultTerrainGenerator();

		for (int x = 0; x < this.w; x++) {
			for (int y = 0; y < this.h; y++) {
				this.chunks[x + y * this.w] = generator.generate( x + this.xModifier, y + this.yModifier );
			}
		}
	}

	public void render(SpriteBatch spriteBatch, IRenderer renderer, ICamera camera) {
		// Find Tiles on screen and queue them
		Rectangle viewport = camera.getViewportRenderBounds();
		Rectangle viewportChunkBounds = new Rectangle(
				viewport.x / TerrainChunk.BLOCK_SIZE,
				viewport.y / TerrainChunk.BLOCK_SIZE,
				viewport.w / TerrainChunk.BLOCK_SIZE,
				viewport.h / TerrainChunk.BLOCK_SIZE );

		for (TerrainChunk chunk : this.chunks) {
			if (chunk.getRenderBounds().overlaps( viewportChunkBounds )) {
				chunk.getTiles()
						//.getAllData()
						.getDataInRectangle( viewportChunkBounds )
						.forEach( TerrainRenderer::queueTileForRendering );

			}
		}

		// Render everything
		TerrainRenderer.render( spriteBatch, renderer, camera );
	}
}
