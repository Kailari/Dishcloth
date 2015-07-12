package dishcloth.engine.world;

import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.geom.Rectangle;
import dishcloth.engine.util.quadtree.AQuadTreeDataObject;
import dishcloth.engine.world.block.BlockID;

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
	private final int size;
	private final BlockID blockID;
	
	public Tile(int x, int y, int size, BlockID blockID) {
		super( new Point( x, y ) );
		this.x = x;
		this.y = y;
		this.size = size;
		this.blockID = blockID;
	}
	
	@Override
	public int getSize() {
		return size;
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
		return new Rectangle( x, y, size, size );
	}
	
	@Override
	public BlockID getBlockID() {
		return blockID;
	}
}
