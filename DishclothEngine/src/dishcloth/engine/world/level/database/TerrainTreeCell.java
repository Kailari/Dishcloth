package dishcloth.engine.world.level.database;

import dishcloth.api.abstractionlayer.world.block.IBlockID;
import dishcloth.api.abstractionlayer.world.level.ITile;
import dishcloth.engine.util.BitUtils;

import java.util.List;

/**
 * <b>TerrainTreeCell</b>
 * <p>
 * QuadTree cell.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 7.8.2015 at 15:19
 */

public class TerrainTreeCell implements ITile {
	private static final short[] SIZE = new short[]{1, 2, 4, 8, 16, 32, 64, 128, 256};

	// Child lookup indices (ex. left bot would be: LEFT + TOP = 0 + 2 = 2)
	private static final int LEFT = 0;
	private static final int RIGHT = 1;
	private static final int TOP = 2;
	private static final int BOTTOM = 0;

	// Static to avoid having to store it locally for each cell.
	// TerrainTree should set this to self always before any querying operation on the nodes.
	protected static TerrainTree currentTree;


	// TODO: Figure out some way to use bytes as coordinates.
	private byte depth;         // Depth-level. Level 0 is 1 block wide, 1 is 2 blocks and 8 is 256
	private short x;            // Top-left x coordinate in internal chunk coordinates [0, 255]
	private short y;            // Top-left y coordinate in internal chunk coordinates [0, 255]

	// Children
	private TerrainTreeCell[] children;
	private boolean isLeaf;

	// Data
	private IBlockID blockID;   // Current BlockID. null if air.

	public TerrainTreeCell(IBlockID blockID, byte depth, short x, short y) {
		this.children = new TerrainTreeCell[4];
		this.setValues( blockID, depth, x, y );
	}

	// ============================================================================================================ //
	//                                               Basic get/set                                                  //
	// ============================================================================================================ //

	/**
	 * Recursively proceeds to deepest depth-level and sets blockID at given coordinates
	 *
	 * @param x       X-coordinate in chunk internal coordinates
	 * @param y       Y-coordinate in chunk internal coordinates
	 * @param blockID New BlockID
	 * @return true if cell changed. False if not.
	 */
	public boolean setBlockID(int x, int y, IBlockID blockID) {
		if (this.depth != 0) {
			/*if (blockID != null && blockID == this.blockID) {
				// A large cell is already filled with given blockID
				return false;
			} else*/
			if (this.isLeaf) {
				split();
			}

			int childIndex = (x < (this.x + SIZE[this.depth - 1]) ? LEFT : RIGHT)
					+ (y < (this.y + SIZE[this.depth - 1]) ? BOTTOM : TOP);

			return this.children[childIndex].setBlockID( x, y, blockID ) && tryCollapse();
		} else {
			if (this.blockID == blockID) {
				return false;
			} else {
				this.blockID = blockID;
				return true;
			}
		}
	}

	/**
	 * Recursively proceeds down the tree until a leaf is found and returns blockID at given coordinates
	 */
	public IBlockID getBlockID(short x, short y) {

		// Check if we contain the point
		if (x > this.x
				&& x < this.x + SIZE[this.depth]
				&& y > this.y
				&& y < this.y + SIZE[this.depth]) {
			if (this.isLeaf) {
				return this.blockID;
			} else {
				IBlockID result;
				if ((result = this.children[0].getBlockID( x, y )) != null) return result;
				if ((result = this.children[1].getBlockID( x, y )) != null) return result;
				if ((result = this.children[2].getBlockID( x, y )) != null) return result;
				if ((result = this.children[3].getBlockID( x, y )) != null) return result;
			}
		}

		return null;
	}

	private boolean tryCollapse() {
		if (this.children[0].isLeaf
				&& this.children[1].isLeaf && this.children[1].blockID == this.children[0].blockID
				&& this.children[2].isLeaf && this.children[2].blockID == this.children[0].blockID
				&& this.children[3].isLeaf && this.children[3].blockID == this.children[0].blockID) {

			// Set data
			this.blockID = this.children[0].blockID;
			this.isLeaf = true;

			// Nullify children
			TerrainTree.cacheCell( this.children[0] );
			TerrainTree.cacheCell( this.children[1] );
			TerrainTree.cacheCell( this.children[2] );
			TerrainTree.cacheCell( this.children[3] );
			this.children[0] = null;
			this.children[1] = null;
			this.children[2] = null;
			this.children[3] = null;

			return true;
		}

		return false;
	}

	private void split() {
		int half = SIZE[this.depth - 1];

		this.children[TOP + LEFT] = TerrainTree.createCell( this.blockID,
		                                                    (byte) (this.depth - 1),
		                                                    this.x,
		                                                    (short) (this.y + half) );

		this.children[TOP + RIGHT] = TerrainTree.createCell( this.blockID,
		                                                     (byte) (this.depth - 1),
		                                                     (short) (this.x + half),
		                                                     (short) (this.y + half) );

		this.children[BOTTOM + LEFT] = TerrainTree.createCell( this.blockID,
		                                                       (byte) (this.depth - 1),
		                                                       this.x,
		                                                       this.y );

		this.children[BOTTOM + RIGHT] = TerrainTree.createCell( this.blockID,
		                                                        (byte) (this.depth - 1),
		                                                        (short) (this.x + half),
		                                                        this.y );

		this.blockID = null;
		this.isLeaf = false;
	}

	protected void setValues(IBlockID blockID, byte depth, short x, short y) {
		this.blockID = blockID;
		this.depth = depth;
		this.x = x;
		this.y = y;
		this.isLeaf = true;
	}

	// ============================================================================================================ //
	//                                            End of basic get/set                                              //
	// ============================================================================================================ //

	public List<TerrainTreeCell> getRenderableCellsInArea(List<TerrainTreeCell> list, int chunkX, int chunkY, float x, float y, short w, short h) {

		// TODO: Something in coordinate systems is wrong
		if (this.x <= x + w
				&& this.x + SIZE[this.depth] >= x
				&& this.y + SIZE[this.depth] >= y
				&& this.y <= y + h || true) {

			if (this.isLeaf) {
				if (this.blockID != null) {
					list.add( this );
				}
			} else {
				list = this.children[LEFT + TOP].getRenderableCellsInArea( list, chunkX, chunkY, x, y, w, h );
				list = this.children[RIGHT + TOP].getRenderableCellsInArea( list, chunkX, chunkY, x, y, w, h );
				list = this.children[LEFT + BOTTOM].getRenderableCellsInArea( list, chunkX, chunkY, x, y, w, h );
				list = this.children[RIGHT + BOTTOM].getRenderableCellsInArea( list, chunkX, chunkY, x, y, w, h );
			}
		}

		return list;
	}

	@Override
	public short getSize() {
		return SIZE[this.depth];
	}

	@Override
	public short getX() {
		return this.x;
	}

	@Override
	public short getY() {
		return this.y;
	}

	@Override
	public IBlockID getBlockID() {
		return this.blockID;
	}
}
