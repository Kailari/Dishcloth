package dishcloth.engine.world.level;

import dishcloth.engine.rendering.IRenderable;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.util.quadtree.QuadTree;
import dishcloth.engine.world.IUpdatable;
import dishcloth.engine.world.Tile;

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

public class Terrain implements IRenderable, IUpdatable {

	private QuadTree<Tile> tiles;

	public Terrain() {
		this.tiles = new QuadTree<>( 0, 0, 16, 16, 1, -1 );
	}

	@Override
	public void update() {

	}

	@Override
	public void fixedUpdate() {
		// TODO: Trigger this only after terrain update has occurred.
		tiles.updateDirty();
	}

	@Override
	public void render(IRenderer renderer) {
		/*
			Rendering the terrain happens as follows:
				1. Calculate camera view-rectangle
					- 1 tile is TILE_W/H pixels
					- camera X/Y and W/H need to be divided by TILE_W/H
				2. Get all cells overlapping the view-rectangle from the QuadTree
				3. Queue them for rendering based on their blockIDs
					- Every cell with the same blockID will be rendered with same UV-coordinates
					  thus, for the optimization's sake, they tiles are rendered ordered by blockID

			FUCK THAT! - Rendering is currently moved to blocks' tasks. This allows special blocks to render
			whatever they want on the screen. Keeps things nice and simple, though it puts a shitload of weight on the
			SpriteBatch. Let's just hope it doesn't get too slow. (If it does, then some kind of block rendering
			queries could be good idea. That kind of caching blocks' render states and reusing them for other blocks
			might speed up things a lot.)
		 */
	}
}
