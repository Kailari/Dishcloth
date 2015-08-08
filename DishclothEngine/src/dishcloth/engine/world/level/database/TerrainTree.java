package dishcloth.engine.world.level.database;

import dishcloth.api.abstractionlayer.world.block.IBlockID;
import dishcloth.api.util.memory.SoftReferencedCache;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>TerrainTree</b>
 * <p>
 * A QuadTree-like database for storing terrain chunks.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 7.8.2015 at 15:18
 */

public class TerrainTree {

	private static SoftReferencedCache<TerrainTreeCell> cellCache = new SoftReferencedCache<>();
	private static List<TerrainTreeCell> tmp = new ArrayList<>();

	private TerrainTreeCell root;

	public TerrainTree() {
		TerrainTreeCell.currentTree = this;

		this.root = createCell( null, (byte) 8, (short) 0, (short) 0 );

		TerrainTreeCell.currentTree = null;
	}

	// ============================================================================================================ //
	//                                            Memory caching methods                                            //
	// ============================================================================================================ //

	protected static void cacheCell(TerrainTreeCell child) {
		cellCache.addReferencedItem( child );
	}

	protected static TerrainTreeCell createCell(IBlockID blockID, byte depth, short x, short y) {
		TerrainTreeCell cell = cellCache.getReferencedItem();
		if (cell == null) {
			cell = new TerrainTreeCell( blockID, depth, x, y );
		} else {
			cell.setValues( blockID, depth, x, y );
		}

		return cell;
	}

	// ============================================================================================================ //
	//                                         End of Memory caching methods                                        //
	// ============================================================================================================ //

	public boolean setBlockID(short x, short y, IBlockID blockID) {
		TerrainTreeCell.currentTree = this;
		boolean result = this.root.setBlockID( x, y, blockID );
		TerrainTreeCell.currentTree = null;
		return result;
	}

	public IBlockID getBlockID(short x, short y) {
		return this.root.getBlockID( x, y );
	}

	public List<TerrainTreeCell> getCellsInRectangle(int chunkX, int chunkY, float x, float y, short w, short h) {
		tmp.clear();
		return this.root.getRenderableCellsInArea( tmp, chunkX, chunkY, x, y, w, h );
	}
}
