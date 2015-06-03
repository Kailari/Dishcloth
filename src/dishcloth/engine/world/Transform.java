package dishcloth.engine.world;

import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.util.math.DishMath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lassi on 2.6.2015.
 */
public class Transform {

	private List<Transform> children;
	private Transform parent;
	private Point location;
	private float rotation; // in deg

	public Transform(float x, float y, float rotation) {
		this( new Point( x, y ), rotation );
	}

	public Transform(Point location, float rotation) {
		this.location = location;
		children = new ArrayList<>();

		addRotation( rotation );
	}


	/**
	 * Use only while calling addChild() method.
	 */
	private Transform(Transform parent, float x, float y, float rotation) {
		this( x, y, rotation );
		this.parent = parent;
	}

	/**
	 * Move the location <b>by</b> values given in parameters.
	 *
	 * @param xa x-amount
	 * @param ya y-amount
	 */
	public void addLocation(float xa, float ya) {
		location.add( xa, ya );
	}

	public void addRotation(float deg) {
		rotation += deg;

		// TODO: Clamp angle to [ 0, 359 ]. Might be a good idea to make DishMath method for it.

		// No. Instead, of adding rotation directly to the children, calculate global rotation
		// separately by .getGlobalRotation()
		// for (int i = 0; i < children.size(); i++) {
		//	children.get( i ).addRotation( deg );
		//}
	}

	public void addRotationRad(float rad) {
		addRotation( (float) Math.toDegrees( rad ) );
	}

	/**
	 * @param x        Relative position to parent
	 * @param y        Relative position to parent
	 * @param rotation Relative rotation to parent
	 */
	public void addChild(float x, float y, float rotation) {
		addChild( new Transform( this, x, y, rotation ) );
	}

	public void addChild(Transform child) {
		// Won't work! Pass-by-value-oddity prevents it (AFAIK)
		// child.parent = this;

		// ...instead, we must call setter. Somehow, this ensures that we have reference to the right Transform
		// and allows us to change the instance passed to us as a parameter. Calling just "parameter.variable = value"
		// changes instance locally, and everything would still be the same outside of this method. By calling setter,
		// however, we change THE instance and not just local instance.
		child.setParent( this );
		children.add( child );
	}

	/**
	 * Detaches child from its parent
	 * @param transform    child to detach
	 */
	public void detachChild(Transform transform) {
		int index;
		if ((index = children.indexOf( transform )) != -1) {
			children.remove( index );
		}
	}

	/**
	 * Detaches child from its parent
	 * @param index    index of child to detach.
	 */
	public void detachChild(int index) {
		if (index > 0 && index < getChildCount()) {
			children.get( index ).setParent( null );

			children.remove( index );
		}
	}

	/**
	 * @return the number of children of this transform
	 */
	public int getChildCount() {
		return children.size();
	}

	/**
	 * Returns child with given index
	 * @param index    index where to look for the child.
	 * @return null if index is out of bounds. Otherwise, the child.
	 */
	public Transform getChild(int index) {
		if (index > 0 && index < getChildCount()) {
			return children.get( index );
		}

		return null;
	}

	/**
	 * Sets parent of a transform. If you need to set parent from public context, use parent.addChild(...) instead.
 	 * NOTE: THIS METHOD IS PRIVATE FOR A REASON. THIS METHOD DOES NOT INCLUDE HANDLING CHANGE IN
	 * CHILD-PARENT-RELATIONSHIP ON THE PARENT-SIDE AND CHILD NEEDS TO BE REMOVED SEPARATELY.
	 * @param parent    new parent.
	 */
	private void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * Checks if given transform is child of this transform.
	 *
	 * @param transform Possible child.
	 * @return true if transform is child.
	 */
	public boolean isChild(Transform transform) {
		return children.contains( transform );
	}

	public Point getGlobalPosition(boolean printDebug) {
		if (parent == null) {
			return new Point( location );
		}

		/*
		* The point 'location' is a point on a circle with radius r and center (0,0) [because the location is relative
		* to parent]. By dividing 'location' by r we get a point which is on a circle with radius 1.
		* We can then figure out the angle a:
		*    location.x = r * cos(a) <=> a = acos(loc.x/r)
		*    location.y = r * sin(a) <=> a = asin(loc.y/r)
		* After calculating the angle a, we will add parent transform's rotation:
		*    angle b = a + parent.rotation
		* And now we can calculate the new position by adding the new coordinates to parent's global position:
		*    result.x += r * cos(b)
		*    result.y += r * sin(b)
		*/

		// I decided to keep par (=parent) and result separate for easier debugging
		// If we remove debugging, we can combine these 3 lines:
		// Point result = parent.getGlobalPosition( false );
		Point par = parent.getGlobalPosition( false );
		Point result = new Point();
		result.add( par );


		float r = location.distance( 0f, 0f );
		float a = (float) (Math.acos( location.x / r ) + Math.toRadians( parent.getGlobalRotation() ));

		// new (relative) location
		float xc = (float) (r * Math.cos( a ));
		float yc = (float) (r * Math.sin( a )) * DishMath.sign( location.y );
		Point relative = new Point( xc, yc );


		result.add( relative );


		if (printDebug) {
			Debug.logNote( "Debug info for transform:", "Transform" );
			Debug.logNote( "Parent: " + par, "Transform" );
			Debug.logNote( "Radius: " + r, "Transform" );
			Debug.logNote( "Total angle: " + a, "Transform" );
			Debug.logNote( "Relative location: " + relative, "Transform" );
			Debug.logNote( "Result: " + result, "Transform" );
		}


		return result;
	}

	public float getGlobalRotation() {
		return (this.parent == null ? rotation : parent.getGlobalRotation() + rotation);
	}

	/**
	 * @return  local location relative to the parent's location and rotation
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * Sets local location. Local location is relative to parent's location and rotation.
	 * @param location    new location
	 */
	public void setLocation(Point location) {
		this.location = location;
	}

	/**
	 * @return  local rotation relative to the parent's rotation
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * Sets local rotation. Local rotation is relative to parent's rotation.
	 * @param rotation    new rotation
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public Transform getParent() {
		return parent;
	}

	@Override
	public String toString() {
		String pr = "yes";
		if (parent == null) pr = "no";
		return "Transform:{parent:" + pr +
				", children=" + children.size() +
				", location=" + location +
				", rotation=" + DishMath.cutDecimals( rotation ) +
				"}";
	}
}
