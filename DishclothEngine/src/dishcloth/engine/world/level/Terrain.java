package dishcloth.engine.world.level;

import dishcloth.engine.rendering.IRenderable;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.util.quadtree.QuadTree;
import dishcloth.engine.world.ITile;
import dishcloth.engine.world.IUpdatable;

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

	private QuadTree<ITile> tiles;



	@Override
	public void update() {

	}

	@Override
	public void fixedUpdate() {

	}

	@Override
	public void render(IRenderer renderer) {

	}
}
