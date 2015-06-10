package dishcloth.engine.util.quadtree;

import dishcloth.engine.util.geom.Point;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * IQuadTreeDataObject.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Any object stored in a QuadTree MUST IMPLEMENT IQuadTreeDataObject
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 21.5.2015
 */

public interface IQuadTreeDataObject {

	/**
	 * @return object's position. Used to determine in which cell the object currently is.
	 */
	Point getPosition();

	/**
	 * Normally, if QuadTreeCell contains data, it cannot be collapsed. However, if allowCollapse() returns true,
	 * this rule will be overridden and cell is collapsed anyways.
	 */
	boolean allowCollapse();

	/**
	 * @return true if position has changed
	 */
	boolean isDirty();

	/**
	 * Removes the dirty flag.
	 */
	void clearDirty();

	QuadTreeCell getContainer();

	void setContainer(QuadTreeCell cell);
}
