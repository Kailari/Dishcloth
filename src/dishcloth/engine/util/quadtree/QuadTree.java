package dishcloth.engine.util.quadtree;

import dishcloth.engine.util.geom.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * QuadTree.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 21.5.2015
 */

public class QuadTree<T extends IQuadTreeDataObject> {

	private QuadTreeCell<T> root;
	private List<T> data;

	public QuadTree(Rectangle bounds) {
		data = new ArrayList<>();

		root = new QuadTreeCell<T>( bounds, this );
	}

	/**
	 * Adds data to the QuadTree<br>
	 * NOTE: WILL NOT OVERRIDE DATA ALREADY PRESENT. If cell where we are trying to add data is already at it's max
	 * capacity, no data will be added and false is returned.
	 *
	 * @return true if operation is successful
	 */
	public boolean addData(T element) {

		// TODO: Check if operation can be executed and actually add data to hierarchy
		data.add( element );
		return true;
	}


	public void refresh() {
		for (int i = 0; i < data.size(); i++) {
			updateDataPosition( i );
		}
	}

	public void updateDirty() {
		for (int i = 0; i < data.size(); i++) {
			if (data.get( i ).isDirty()) {
				updateDataPosition( i );
			}
		}
	}

	/**
	 * Updates data's positions. If data moves from cell to another, it is automatically transferred in hierarchy.
	 */
	public void updateDataPosition(int index) {
		updateDataPosition( data.get( index ) );
	}

	/**
	 * Updates data's positions. If data moves from cell to another, it is automatically transferred in hierarchy.
	 */
	public void updateDataPosition(T data) {

		// Nothing to do.
		if (data.getContainer() == root) {
			return;
		}

		// Still in the same cell
		if (data.getContainer().getBounds().containsPoint( data.getPosition() )) {
			return;
		}

		// Find a new cell...

		QuadTreeCell<T> oldCell = data.getContainer();
		QuadTreeCell<T> newCell = root.findPhysicalContainerCellForPoint( data.getPosition() );

		// ...and find a common parent for old cell and newly found cell

		QuadTreeCell<T> oldCellParent = oldCell.getParent();
		QuadTreeCell<T> newCellParent = newCell.getParent();

		// Get depths to same level
		if (oldCellParent != newCellParent && oldCellParent.getDepth() != newCellParent.getDepth()) {

			for (int i = 0; i < Math.abs( oldCell.getDepth() - newCell.getDepth() ) - 1; i++) {
				if (oldCellParent.getDepth() > newCellParent.getDepth()) {
					oldCellParent = oldCellParent.getParent();
				} else if (newCellParent.getDepth() > oldCellParent.getDepth()) {
					newCellParent = newCellParent.getParent();
				}
			}
		}

		// Find a common parent
		while (oldCellParent.getParent() != newCellParent.getParent()) {
			oldCellParent = oldCellParent.getParent();
			newCellParent = newCellParent.getParent();
		}

		newCellParent.addData(data);
		oldCellParent.removeData(data);
	}
}
