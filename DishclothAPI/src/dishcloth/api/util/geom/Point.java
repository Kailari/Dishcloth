package dishcloth.api.util.geom;

import dishcloth.api.util.math.DishMath;

/**
 * Created by Lassi on 21.5.2015.
 */
public class Point {

	protected float x, y;

	protected Point(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public static float distance(float x, float y, float x1, float y1) {
		return (float) Math.sqrt( (x - x1) * (x - x1) + (y - y1) * (y - y1) );
	}

	public static float distance(Point p1, Point p2) {
		return p1.distance( p2 );
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float distance(Point p) {
		return distance( x, y, p.x, p.y );
	}

	public float distance(float x1, float y1) {
		return distance( x, y, x1, y1 );
	}

	/**
	 * Move the point <b>by</b> amount in parameters
	 */
	public void add(float xa, float ya) {
		x += xa;
		y += ya;
	}

	public void add(Point p) {
		add( p.x, p.y );
	}

	public boolean equals(Point p) {
		if (DishMath.approxSame( x, p.x ) && DishMath.approxSame( y, p.y )) return true;
		return false;
	}

	@Override
	public String toString() {
		return "Point:(" + DishMath.cutDecimals( x ) + ";" + DishMath.cutDecimals( y ) + ")";
	}

}
