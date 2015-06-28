package dishcloth.engine.world.level;

import dishcloth.engine.rendering.IRenderable;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.render2d.SpriteBatch;
import dishcloth.engine.util.quadtree.QuadTree;
import dishcloth.engine.world.IUpdatable;
import dishcloth.engine.world.Tile;

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

public class Terrain implements IUpdatable {

	private TerrainChunk[] chunks;
	private int w, h;

	public Terrain(int w, int h) {
		this.chunks = new TerrainChunk[w * h];
		this.w = w;
		this.h = h;

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				this.chunks[x + y * w] = new TerrainChunk( x, y );
			}
		}
	}

	@Override
	public void update() {

	}

	@Override
	public void fixedUpdate() {
	}

	public void render(IRenderer renderer, SpriteBatch spriteBatch) {
		/*
			Rendering the terrain happens as follows:
				1. Calculate camera view-rectangle
					- 1 tile is TILE_W/H pixels
					- camera X/Y and W/H need to be divided by TILE_W/H
				2. Get all cells overlapping the view-rectangle from the QuadTree
				3. Queue them for rendering based on their blockIDs
					- Every cell with the same blockID will be rendered with same UV-coordinates
					  thus, for the optimization's sake, they tiles are rendered ordered by blockID

			FUCK THAT! - Rendering is currently moved to blocks' task. This allows special blocks to render
			whatever they want on the screen. Keeps things nice and simple, though it puts a shitload of weight on the
			SpriteBatch. Let's just hope it doesn't get too slow. (If it does, then some kind of block rendering
			queries could be good idea. That kind of caching blocks' render states and reusing them for other blocks
			might speed up things a lot.)
		 */

		// TODO: SpriteBatch sprite scaling (or at least something close to it.)
		// TODO: Render the fucking terrain already.

		// 1. Find Chunks on screen
		List<TerrainChunk> onScreenChunks = new ArrayList<>();
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				// TODO: Test if chunk is on-screen
				onScreenChunks.add( this.chunks[x + y * w] );
			}
		}

		// 2. Find Tiles on screen
		for (TerrainChunk chunk : onScreenChunks) {

		}

		// 3. Render tiles
	}
}
