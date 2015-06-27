package dishcloth.engine.world.level;

import dishcloth.engine.util.geom.Rectangle;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.util.quadtree.QuadTree;
import dishcloth.engine.util.quadtree.QuadTreeCell;
import dishcloth.engine.world.Tile;
import dishcloth.engine.world.block.BlockID;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * TerrainQuadTreeCell.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * QuadTreeCell that automatically collapses the cell if all its children contain Tile with the same blockID
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 24.6.2015
 */

public class TerrainQuadTreeCell extends QuadTreeCell<Tile> {
	public TerrainQuadTreeCell(QuadTree tree, Rectangle bounds, int maxDepth) {
		super( tree, bounds, 0, maxDepth );
	}

	public TerrainQuadTreeCell(QuadTreeCell<Tile> parent, boolean top, boolean left) {
		super( parent, top, left );
	}

	@Override
	public void addData(Tile data) {
		super.addData( data );

		// If we are on the deepest level and there already was a tile in this cell,
		// then bucket now contains two tiles
		if (this.bucket.size() == 2) {
			// If they were the same block, just remove the new one
			if (data.getBlockID() == this.bucket.get( 0 ).getBlockID()) {
				this.bucket.remove( 1 );
				data.setContainer( null );
			}
			// New data had new blockID, override the old one
			else {
				this.bucket.remove( 0 );

				// Bucket size SHOULD be now 1
				assert this.bucket.size() == 1;
				this.parent.collapse();

				return;
			}
		}

		// Try to collapse (if not root)
		if (this.getParent() != null && this.isSplit) {
			this.collapse();
		}
	}

	/**
	 * Terrain cells can collapse if there is only one type of block in its children.
	 */
	@Override
	protected boolean canCollapse() {
		BlockID firstBlockID = children[0].getData().get( 0 ).getBlockID();
		for (int i = 1; i < 4; i++) {
			if (children[i].getIsSplit()
					|| children[i].getData().get( 0 ).getBlockID() != firstBlockID) {
				return false;
			}
		}

		return true;
	}

	@Override
	protected void split() {
		if (this.depth == this.maxDepth) {
			Debug.logErr( "TRIED TO SPLIT CELL WHICH ALREADY IS AT MAX DEPTH", this );
			return;
		}

		// Create children
		this.children[0] = new TerrainQuadTreeCell( this, true, true );
		this.children[1] = new TerrainQuadTreeCell( this, true, false );
		this.children[2] = new TerrainQuadTreeCell( this, false, true );
		this.children[3] = new TerrainQuadTreeCell( this, false, false );

		// All children receive the same blockID that this cell currently has
		// ...or if data is null, we'll just
		Tile data = this.bucket.get( 0 );
		for (int j = 0; j < 4; j++) {
			if (data != null) {
				this.children[j].addData( new Tile( this.children[j].getBounds(), data.getBlockID() ) );
			}
		}

		this.bucket.clear();

		this.isSplit = true;
	}

	@Override
	public void collapse() {
		if (canCollapse()) {

			// Create new tile
			Tile data = new Tile( this.getBounds(), children[0].getData().get( 0 ).getBlockID() );

			// Nullify children
			clearChildren();

			// Add data to self (!!! WARNING: DIRECT ADD --- ADDS DATA DIRECTLY TO THE BUCKET !!!)
			this.addDirect( data );
			data.setContainer( this );

			// Cell is no longer split
			this.isSplit = false;

			// Try to collapse (if not root)
			if (this.getParent() != null) {
				parent.collapse();
			}
		}
	}


}
