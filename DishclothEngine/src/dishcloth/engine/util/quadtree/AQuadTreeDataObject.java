package dishcloth.engine.util.quadtree;

import dishcloth.engine.util.geom.Point;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * AQuadTreeDataObject.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 3.6.2015
 */

public abstract class AQuadTreeDataObject implements IQuadTreeDataObject {

	private QuadTreeCell containerCell;
	protected boolean dirty;

	@Override
	public void clearDirty() {
		dirty = false;
	}

	@Override
	public QuadTreeCell getContainer() {
		return containerCell;
	}

	@Override
	public void setContainer(QuadTreeCell cell) {
		this.containerCell = cell;
	}
}
