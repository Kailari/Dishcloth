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

	public List<Transform> children;
	private Transform parent;
	private Point location;
	private float rotation; // in deg

	public Transform(float x, float y, float rotation) {
		this( new Point( x, y ), rotation );
	}

	public Transform(Point location, float rotation) {
		this.location = location;
		children = new ArrayList<Transform>();

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

		for (int i = 0; i < children.size(); i++) {
			children.get( i ).addRotation( deg );
		}
	}

	public void addRotationDeg(float rad) {
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
		child.parent = this;
		children.add( child );

		child.addRotation( this.rotation );

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
		float a = (float) (Math.acos( location.x / r ) + Math.toRadians( parent.rotation ));

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
