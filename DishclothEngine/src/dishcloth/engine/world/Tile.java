package dishcloth.engine.world;

import dishcloth.engine.util.geom.IntPoint;
import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.geom.Rectangle;
import dishcloth.engine.util.quadtree.AQuadTreeDataObject;
import dishcloth.engine.util.quadtree.QuadTreeCell;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Tile.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 3.6.2015
 */

public class Tile extends AQuadTreeDataObject implements ITile {

	private IntPoint position;
	private int blockID;

	@Override
	public float getWidth() {
		return 0;
	}

	@Override
	public float getHeight() {
		return 0;
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

	@Override
	public int getBlockID() {
		return blockID;
	}

	@Override
	public Point getPosition() {
		return null;
	}

	@Override
	public boolean allowCollapse() {
		return false;
	}

	@Override
	public boolean isDirty() {
		return false;
	}
}
