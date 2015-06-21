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

public abstract class AQuadTreeDataObject {

	private QuadTreeCell containerCell;
	private Point position;

	public AQuadTreeDataObject(Point position) {
		this.position = position;
	}

	public final Point getPosition() {
		return position;
	}

	@SuppressWarnings( "unchecked" )
	public final void setPosition(Point position) {

		if (getContainer() != null) {
			getContainer().getTree().addDirty( this );
		}

		this.position = position;
	}

	public QuadTreeCell getContainer() {
		return containerCell;
	}

	public void setContainer(QuadTreeCell cell) {
		this.containerCell = cell;
	}
}
