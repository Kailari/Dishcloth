package dishcloth.engine.world.level;

import dishcloth.engine.util.geom.Rectangle;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.util.quadtree.QuadTree;
import dishcloth.engine.util.quadtree.QuadTreeCell;
import dishcloth.engine.world.block.BlockID;

import java.util.List;

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
		int size = Math.round( (float) Math.pow( 2f, (float) (this.maxDepth - this.depth) ) );
		super.addData( new Tile( data.getX(), data.getY(), size, data.getBlockID() ) );

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
		if (this.parent != null) {
			this.parent.collapse();
		}
	}

	@Override
	protected void split() {
		if (this.depth == this.maxDepth) {
			Debug.logErr( "TRIED TO SPLIT CELL WHICH ALREADY IS AT MAX DEPTH", this );
			return;
		}

		// Create children
		this.children.add( new TerrainQuadTreeCell( this, true, true ) );
		this.children.add( new TerrainQuadTreeCell( this, true, false ) );
		this.children.add( new TerrainQuadTreeCell( this, false, true ) );
		this.children.add( new TerrainQuadTreeCell( this, false, false ) );

		// All children receive the same blockID that this cell currently has
		// ...or if data is null, we'll just leave everything be
		Tile data = (this.bucket.size() != 0 ? this.bucket.get( 0 ) : null);
		if (data != null) {
			for (int j = 0; j < 4; j++) {
				Rectangle bounds = this.children.get( j ).getBounds();
				this.children.get( j ).addData( new Tile( Math.round( bounds.x ),
				                                          Math.round( bounds.y ),
				                                          Math.round( bounds.w ),
				                                          data.getBlockID() ) );
			}
		}

		this.bucket.clear();

		this.isSplit = true;
	}

	/**
	 * Terrain cells can collapse if there is only one type of block in its children.
	 */
	@Override
	public boolean canCollapse() {
		if (this.isSplit) {
			BlockID firstBlockID = findBlockIDFromChildren( 0 );

			for (int i = 0; i < 4; i++) {
				if (children.get( i ).getIsSplit()) {
					if (children.get( i ).canCollapse()) {
						children.get( i ).collapse();
					} else if (children.get( i ).getIsSplit()) {
						return false;
					}
				}

				if (findBlockIDFromChildren( i ) != firstBlockID) {
					return false;
				}
			}
		} else {
			//Debug.logErr( "Why on earth are we trying to collapse non-split cell?", this );
			return false;
		}

		return true;
	}

	private BlockID findBlockIDFromChildren(int index) {
		List<Tile> tileInCellZero = children.get( index ).getData();
		return (tileInCellZero.size() != 0 ? tileInCellZero.get( 0 ).getBlockID() : null);
	}

	@Override
	public void collapse() {

		if (canCollapse()) {

			// Create new tile
			Tile data = new Tile( Math.round( bounds.x ),
			                      Math.round( bounds.y ),
			                      Math.round( bounds.w ),
			                      findBlockIDFromChildren( 0 ) );

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
