package dishcloth.engine.world.level;

import dishcloth.engine.rendering.IRenderable;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.OrthographicCamera;
import dishcloth.engine.rendering.render2d.SpriteBatch;
import dishcloth.engine.util.geom.Rectangle;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.util.quadtree.QuadTree;
import dishcloth.engine.world.IUpdatable;
import dishcloth.engine.world.Tile;
import javafx.scene.Camera;
import randomGen.generation.generator.DefaultTerrainGenerator;

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

public class Terrain implements IUpdatable, IRenderable {

	private TerrainChunk[] chunks;
	private int w, h;

	public Terrain(int w, int h) {
		this.chunks = new TerrainChunk[w * h];
		this.w = w;
		this.h = h;

		DefaultTerrainGenerator generator = new DefaultTerrainGenerator(1L);

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				this.chunks[x + y * w] = generator.generate( x, y );
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
				(float) Math.floor( viewport.x / TerrainChunk.BLOCK_SIZE ),
				(float) Math.floor( viewport.y / TerrainChunk.BLOCK_SIZE ),
				(float) Math.ceil( viewport.w / TerrainChunk.BLOCK_SIZE ),
				(float) Math.ceil( viewport.h / TerrainChunk.BLOCK_SIZE ) );
		for (TerrainChunk chunk : this.chunks) {
			if (chunk.getRenderBounds().overlaps( viewport )) {
				chunk.getTiles()
						.getDataInRectangle( viewportChunkBounds ).forEach( TerrainRenderer::queueTileForRendering );
			}
		}

		// Render everything
		TerrainRenderer.render( renderer );
	}
}
