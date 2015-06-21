package dishcloth.engine.world;

import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.geom.Rectangle;
import dishcloth.engine.util.quadtree.AQuadTreeDataObject;

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

	private final int x;
	private final int y;
	private final int widthInTiles;
	private final int heightInTiles;
	private final int blockID;

	public Tile(int x, int y, int w, int h, int blockID) {
		super(new Point( x, y ));
		this.x = x;
		this.y = y;
		this.widthInTiles = w;
		this.heightInTiles = h;
		this.blockID = blockID;
	}

	@Override
	public int getWidth() {
		return widthInTiles;
	}

	@Override
	public int getHeight() {
		return heightInTiles;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle( x, y, widthInTiles, heightInTiles );
	}

	@Override
	public int getBlockID() {
		return blockID;
	}
}
