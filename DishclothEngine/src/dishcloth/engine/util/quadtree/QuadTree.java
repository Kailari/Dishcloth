package dishcloth.engine.util.quadtree;

import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.geom.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
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

public class QuadTree<T extends AQuadTreeDataObject> {

	/**
	 * How many DataObjects can one cell contain before being automagically split
	 * -1 means infinite
	 */
	private final int bucketSize;
	/**
	 * How many levels deep can QuadTree be before starting to special-case data. (Storing data ignoring bucket-size)
	 * -1 means infinite
	 */
	private final int maxDepth;
	private QuadTreeCell<T> root;
	private List<T> data;
	private List<T> dirty;

	public QuadTree(int bucketSize, int maxDepth, QuadTreeCell<T> cell) {
		this.root = cell;
		this.data = new ArrayList<>();
		this.dirty = new ArrayList<>();
		this.maxDepth = maxDepth;
		this.bucketSize = bucketSize;
		this.root.setTree( this );
	}

	public QuadTree(Rectangle bounds, int bucketSize, int maxDepth) {
		this.root = new QuadTreeCell<>( this, bounds, bucketSize, maxDepth );
		this.data = new ArrayList<>();
		this.dirty = new ArrayList<>();
		this.maxDepth = maxDepth;
		this.bucketSize = bucketSize;
	}

	public QuadTree(float x, float y, float width, float height, int bucketSize, int maxDepth) {
		this( new Rectangle( x, y, width, height ), bucketSize, maxDepth );
	}

	public QuadTree(Rectangle bounds) {
		this( bounds, 1, -1 );
	}

	public QuadTree(float x, float y, float width, float height) {
		this( x, y, width, height, 1, -1 );
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public int getBucketSize() {
		return bucketSize;
	}


	public void addData(T d) {
		root.addData( d );
		this.data.add( d );
	}

	public void addDirty(T d) {
		if (this.data.contains( d )) {
			this.dirty.add( d );
		}
	}

	@SuppressWarnings("unchecked")
	public void removeData(T data) {
		data.getContainer().removeData( data );
	}

	public void updateDirty() {
		dirty.forEach( this::updateDirty );
		dirty.clear();
	}

	@SuppressWarnings("unchecked")
	private void updateDirty(T d) {
		QuadTreeCell<T> cell = root.getCellInLocation( d.getPosition() );
		if (cell != d.getContainer()) {
			d.getContainer().moveData( d, cell );
		}
	}

	public List<T> getDataAt(float x, float y) {
		return root.getCellInLocation( new Point(x, y) ).getData();
	}

	public List<T> getDataInRectangle(Rectangle rectangle) {
		List<T> result = new ArrayList<>();
		root.getCellsInRectangle( rectangle ).forEach( cell -> result.addAll( cell.getData() ) );
		return result;
	}

	public List<T> getAllData() {
		return root.getAllData();
	}

	public QuadTreeCell<T> getRoot() {
		return root;
	}
}
