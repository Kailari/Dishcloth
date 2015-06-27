package dishcloth.engine.util.quadtree;

import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.geom.Rectangle;
import dishcloth.engine.util.logger.Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * QuadTreeCell.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by Lassi on 20.5.2015.
 */
public class QuadTreeCell<T extends AQuadTreeDataObject> {

	protected final int maxDepth;

	protected int depth;
	protected int bucketSize;
	protected Rectangle bounds;

	protected QuadTree<T> tree;
	protected QuadTreeCell<T> parent;
	protected QuadTreeCell<T>[] children;
	protected boolean isSplit;

	protected List<T> bucket;

	/**
	 * Creates root-cell
	 *
	 * @param bounds     bounds
	 * @param bucketSize bucket size
	 * @param maxDepth   maximum depth
	 */
	public QuadTreeCell(QuadTree tree, Rectangle bounds, int bucketSize, int maxDepth) {
		this.bounds = new Rectangle( bounds );
		this.bucketSize = bucketSize;
		this.depth = 0;
		this.maxDepth = maxDepth;

		this.bucket = new ArrayList<>();
		this.parent = null;
		this.tree = tree;

		this.initializeChildrenArray();
	}

	/**
	 * Creates new child for given parent
	 *
	 * @param parent parent
	 * @param top    Created cell should be one of the top children
	 * @param left   Created cell should be one of the children on the left side
	 */
	protected QuadTreeCell(QuadTreeCell<T> parent, boolean top, boolean left) {
		this.parent = parent;
		this.tree = parent.getTree();

		this.maxDepth = parent.maxDepth;
		this.depth = parent.depth + 1;
		// If at max depth, set bucket size to infinite. Otherwise, use parent's bucket size
		this.bucketSize = (this.depth == this.maxDepth ? -1 : parent.bucketSize);

		// Create bucket
		this.bucket = new ArrayList<>();
		this.initializeChildrenArray();

		// Calculate bounds
		float w = parent.bounds.w / 2f;
		float h = parent.bounds.h / 2f;
		this.bounds = new Rectangle( (left ? parent.bounds.x : parent.bounds.x + w),
		                             (top ? parent.bounds.y + h : parent.bounds.y), w, h );
	}

	// TODO: Bucket data handling and updating dirty DataObjects
	public QuadTreeCell<T> getParent() {
		return parent;
	}

	protected QuadTreeCell<T>[] getChildren() {
		return this.children;
	}

	public QuadTree<T> getTree() {
		return tree;
	}

	public void setTree(QuadTree<T> tree) {
		this.tree = tree;
	}

	/**
	 * Java cannot initialize generic arrays directly due to limitations of generics implementation.
	 * Therefore, generic arrays need to be initialized via detours. Here is a ugly and dirty array
	 * initialization by exploiting ArrayList.toArray().
	 */
	@SuppressWarnings("unchecked")
	private void initializeChildrenArray() {
		List<QuadTreeCell<T>> tmp = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			tmp.add( null );
		}
		this.children = (QuadTreeCell<T>[]) tmp.toArray();
	}

	protected void split() {
		if (this.depth == this.maxDepth) {
			Debug.logErr( "TRIED TO SPLIT CELL WHICH ALREADY IS AT MAX DEPTH", this );
			return;
		}

		// Create children
		this.children[0] = new QuadTreeCell<>( this, true, true );
		this.children[1] = new QuadTreeCell<>( this, true, false );
		this.children[2] = new QuadTreeCell<>( this, false, true );
		this.children[3] = new QuadTreeCell<>( this, false, false );

		// Move data to children
		T data;
		for (int i = 0; i < this.bucket.size(); i++) {
			for (int j = 0; j < 4; j++) {
				if (this.children[j].bounds.containsPoint( (data = this.bucket.get( i )).getPosition() )) {
					this.children[j].addData( data );
					break;
				}
			}
		}

		this.bucket.clear();

		this.isSplit = true;
	}

	public void collapse() {
		if (canCollapse()) {

			List<T> allData = getAllData();
			clearChildren();

			// Add data to self
			this.bucket.addAll( allData );
			for (T data : allData) {
				data.setContainer( this );
			}

			// Cell is no longer split
			this.isSplit = false;

			// Try to collapse parent if not null
			if (this.parent != null) {
				parent.collapse();
			}
		}
	}

	protected void clearChildren() {
		// Nullify children
		this.children[0] = null;
		this.children[1] = null;
		this.children[2] = null;
		this.children[3] = null;
	}

	protected boolean canCollapse() {
		return getAllData().size() <= this.bucketSize;
	}

	public QuadTreeCell<T> getCellInLocation(Point location) {
		if (this.bounds.containsPoint( location )) {
			if (this.isSplit) {
				QuadTreeCell<T> result;
				for (int i = 0; i < 4; i++) {
					if ((result = this.children[i].getCellInLocation( location )) != null) {
						return result;
					}
				}

				Debug.logErr( "This line should not have been reached under any circumstances.", this );
			} else {
				return this;
			}
		}

		return null;
	}

	public List<QuadTreeCell<T>> getCellsInRectangle(Rectangle rectangle) {
		List<QuadTreeCell<T>> list = new ArrayList<>();

		// Optimization. If a cell is completely inside a rectangle, all of its children must be too.
		// This allows us to potentially skip a shitload of getCellsInRectangle-calls
		if (this.isCompletelyInsideRectangle( rectangle )) {
			if (this.isSplit) {
				for (int i = 0; i < 4; i++) {
					list.addAll( this.children[i].getAllChildren() );
				}
			}
			// No children, add self
			else {
				list.add( this );
			}

		} else if (overlapsRectangle( rectangle )) {
			if (this.isSplit) {
				for (int i = 0; i < 4; i++) {
					list.addAll( this.children[i].getCellsInRectangle( rectangle ) );
				}
			}
			// No children, add self
			else {
				list.add( this );
			}
		}

		return list;
	}

	protected boolean isCompletelyInsideRectangle(Rectangle rectangle) {
		return rectangle.containsRectangle( rectangle );
	}

	protected boolean overlapsRectangle(Rectangle rectangle) {
		return this.bounds.overlaps( rectangle );
	}

	public List<QuadTreeCell<T>> getAllChildren() {
		List<QuadTreeCell<T>> list = new ArrayList<>();

		if (isSplit) {
			for (int i = 0; i < 4; i++) {
				list.addAll( this.children[i].getAllChildren() );
			}
		}
		// Only if there's no children, will cell get added to the list
		else {
			list.add( this );
		}

		return list;
	}

	/**
	 * @return Data from whole hierarchy from here downwards
	 */
	public List<T> getAllData() {
		List<T> list = new ArrayList<>();

		if (isSplit) {
			for (int i = 0; i < 4; i++) {
				list.addAll( this.children[i].getAllData() );
			}
		} else {
			list.addAll( this.bucket );
		}

		return list;
	}

	/**
	 * Adds data to hierarchy
	 *
	 * @param data data to add
	 * @return Cell where data was added
	 */
	@SuppressWarnings("unchecked")
	public void addData(T data) {
		if (!this.isSplit) {
			if (this.bucket.size() < this.bucketSize || this.depth == maxDepth) {
				if (data.getContainer() != null) {
					data.getContainer().moveData( data, this );
				} else {

					data.setContainer( this );
					this.bucket.add( data );
					return;
				}
			} else {
				split();
			}
		}

		for (int i = 0; i < 4; i++) {
			if (this.children[i].bounds.containsPoint( data.getPosition() )) {
				this.children[i].addData( data );
				return;
			}
		}

		Debug.logErr( "Data was not added. For some reason, addData() could not find suitable cell to add data to.",
		              this );
	}

	/**
	 * !!! SKIPS ALL VALIDITY AND BUCKET/SPLIT/CHILD -CHECKS. UNSAFE, DO NOT USE UNLESS ABSOLUTELY SURE ABOUT WHAT
	 * YOU ARE DOING !!!
	 *
	 * @param data data to add.
	 */
	protected void addDirect(T data) {
		this.bucket.add( data );
	}

	public void removeData(T data) {
		data.setContainer( null );
		this.bucket.remove( data );

		if (this.parent != null) {
			// Try to collapse parent
			parent.collapse();
		}
	}

	/**
	 * Moves data from cell to another
	 *
	 * @param data    data to be moved
	 * @param newCell target cell
	 */
	public void moveData(T data, QuadTreeCell<T> newCell) {
		/*
			Moving happens as follows:
				1. Find depth where both 'newCell' and 'oldCell' have a common parent-cell
				2. Issue removeData on oldCell
					- Remove data takes care of possible collapses to the tree.
					- This happens by calling method on parent
				3. Issue addData to newCell's side of the hierarchy
		 */

		int commonDepth = Math.min( this.depth, newCell.depth );

		QuadTreeCell<T> oldParent = this.parentOnDepth( commonDepth );
		QuadTreeCell<T> commonParent = newCell.parentOnDepth( commonDepth );

		while (oldParent != commonParent) {
			oldParent = oldParent.parent;
			commonParent = commonParent.parent;
		}

		this.removeData( data );
		commonParent.addData( data );
	}

	public List<T> getData() {
		return this.bucket;
	}

	private QuadTreeCell<T> parentOnDepth(int depth) {
		if (this.depth == depth) {
			return parent;
		}

		QuadTreeCell<T> p = this.parent;
		while (p.depth != depth) {
			p = p.parent;
		}

		return p;
	}

	public boolean getIsSplit() {
		return isSplit;
	}

	protected void setIsSplit(boolean isSplit) {
		this.isSplit = isSplit;
	}

	public Rectangle getBounds() {
		return bounds;
	}
}
