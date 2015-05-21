package dishcloth.engine.util.quadtree;

import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.geom.Rectangle;

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
class QuadTreeCell<T extends IQuadTreeData> {

	private int depth;
	private Rectangle bounds;
	private List<T> hierarchyData;
	private T data;


	private List<QuadTreeCell<T>> children; // NOTE: Needs to be a list for generics to work
											// (AFAIK, Java can't handle generic arrays)
	private QuadTreeCell<T> parent;
	private QuadTree<T> tree;

	/**
	 * Private constructor
	 */
	private QuadTreeCell(Rectangle bounds, QuadTreeCell<T> parent, QuadTree<T> tree, int depth) {
		this.parent = parent;
		this.tree = tree;
		this.depth = depth;
		this.bounds = bounds;

		children = new ArrayList<>();
		hierarchyData = new ArrayList<>();
	}

	/**
	 * Creates a new child cell to given parent cell.
	 *
	 * @param bounds Rectangle describing size and location of the cell
	 * @param parent Parent cell
	 */
	public QuadTreeCell(Rectangle bounds, QuadTreeCell<T> parent) {
		this( bounds, parent, parent.tree, parent.depth + 1 );
	}

	/**
	 * Creates root cell
	 *
	 * @param bounds Rectangle describing size and location of the cell
	 * @param tree   Reference to the QuadTree this cell belongs to.
	 */
	public QuadTreeCell(Rectangle bounds, QuadTree<T> tree) {
		this( bounds, null, tree, 0 );
	}


	public boolean isLeaf() {
		return this.children == null;
	}

	public T getData() {
		return data;
	}

	private void setData(T data) {
		this.data = data;
	}

	public void addData(T dataToAdd) {
		if (isLeaf()) {
			if (getData() == null) {
				setData( dataToAdd );
				return;
			} else {
				if (!split()) {
					// TODO: throw exception
				}
			}
		}

		// NOTE: Leafs with data should have been split
		if (!isLeaf()) {
			for (int i = 0; i < 4; i++) {
				if (children.get( i ).getBounds().containsPoint( dataToAdd.getPosition() )) {
					children.get( i ).addData( dataToAdd );

					hierarchyData.add( dataToAdd );
					return;
				}
			}

			// TODO: Throw exception
		}
	}

	/**
	 * Removes data recursively from the hierarchy.
	 *
	 * @param dataToRemove Data instance to remove from hierarchy. Ignored on leafs.
	 */
	public void removeData(T dataToRemove) {
		if (isLeaf()) {
			if (getData() == null) {
				// Nothing to remove
			} else {
				setData( null );
			}
		} else {
			if (hierarchyData.contains( dataToRemove )) {
				for (int i = 0; i < 4; i++) {
					if (children.get( i ).getBounds().containsPoint( dataToRemove.getPosition() )) {
						children.get( i ).removeData( dataToRemove );

						hierarchyData.remove( dataToRemove );
						break;
					}
				}
			} else {
				// TODO: Throw exception
			}
		}

		// Attempt collapse on parent
		parent.collapse();
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public QuadTreeCell<T> getParent() {
		return parent;
	}

	public int getDepth() {
		return depth;
	}

	/**
	 * Returns deepest cell in hierarchy where point is contained
	 *
	 * @param point
	 * @return cell where point is located
	 */
	public QuadTreeCell<T> findPhysicalContainerCellForPoint(Point point) {
		if (!bounds.containsPoint( point )) return null;

		if (isLeaf()) {
			return this;
		} else {
			QuadTreeCell<T> result = null;
			for (int i = 0; i < 4; i++) {
				result = children.get( i ).findPhysicalContainerCellForPoint( point );
				if (result != null) {
					return result;
				}
			}
		}

		// None of the children contained point. Getting to this point is a bug.
		return null;
	}

	/**
	 * Returns the child nodes of this cell.
	 *
	 * @return child nodes of this root
	 */
	public List<QuadTreeCell<T>> getChildren() {
		return children;
	}


	/**
	 * Splits cell into four smaller cells.
	 *
	 * @return true if success
	 */
	public boolean split() {
		if (this.children != null) {
			System.err.println( "Tried to split cell which already has children!" );
			return false;
		}

		children = new ArrayList<>();

		double halfSize = bounds.w / 2d;
		Point dataPos = data.getPosition();
		boolean dataAdded = false;

		for (int i = 0; i <= 1; i++) {
			for (int j = 0; j <= 1; j++) {
				// Horrendous single-line child creation, but meh. It works! (hopefully...)
				children.add(
						new QuadTreeCell<T>(
								new Rectangle(
										this.bounds.x + halfSize * i,
										this.bounds.y + halfSize * j,
										halfSize,
										halfSize ),
								this
						)
				);

				// If contained data's position is in created child, move the data to it.
				if (!dataAdded && children.get( i + j * 2 ).bounds.containsPoint( dataPos )) {
					children.get( i + j * 2 ).setData( data );
					this.hierarchyData.add( data );
					this.data = null;

					dataAdded = true;
				}
			}
		}

		// If data was not added to any of the children during split.
		if (!dataAdded) {
			System.err.println( "Could not add data to children during Split()!" );
			return false;
		}

		// Success!
		return true;
	}

	/**
	 * Merges all children into this. Takes contained data in account and only collapses if only one data instance is
	 * present. ie. if there is data in two of the children, or if one or more child has not set "can collapse" -flag
	 * no merging will be done.
	 *
	 * @return true if operation was successful
	 */
	public boolean collapse() {
		if (isLeaf()) return false;

		// Find data contained in children
		//  Priority goes as follows:
		//      1. the only non-allowing data
		//      2. first allowing data found

		T containedData = null;

		// THIS is what we call descriptive variable names! :D
		boolean alreadyFoundDataWhichDoesNotAllowCollapse = false;
		for (int i = 0; i < 4; i++) {
			QuadTreeCell<T> c = children.get( i );

			// If Child is split...
			if (!c.isLeaf()) {
				// ...attempt to collapse it
				if (!c.collapse()) {
					return false;
				}
			}

			// If child contains data...
			if (c.getData() != null) {
				// If we haven't found data with allowCollapse false...
				if (!alreadyFoundDataWhichDoesNotAllowCollapse
						&& !c.getData().allowCollapse()
						&& containedData == null) {

					alreadyFoundDataWhichDoesNotAllowCollapse = true;

					containedData = c.getData();
				}
				// If we haven't found data at all...
				else if (containedData == null) {
					containedData = c.getData();
				}
				// Nope. Not gonna collapse.
				else {
					return false;
				}
			}
		}

		// Clear hierarchy
		this.hierarchyData.clear();
		this.hierarchyData = null;
		this.children.clear();
		this.children = null;

		// Add data to self (or set own data null if containedData is null)
		this.setData( containedData );

		// Success!
		return true;
	}
}
