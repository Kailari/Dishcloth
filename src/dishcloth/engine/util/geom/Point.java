package dishcloth.engine.util.geom;

import dishcloth.engine.util.math.DishMath;

/**
 * Created by Lassi on 21.5.2015.
 */
public class Point {


	public float x, y;

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}


	public Point() {
		this.x = 0;
		this.y = 0;
	}


	public static float distance(float x1, float y1, float x2, float y2) {
		return distance( new Point( x1, y1 ), new Point( x2, y2 ) );
	}

	public static float distance(Point p1, Point p2) {
		return p1.distance( p2 );
	}

	public static boolean equals(Point p1, Point p2) {
		return p1.equals( p2 );
	}


	public float distance(float x, float y) {
		return distance( new Point( x, y ) );
	}

	public float distance(Point p) {
		return (float) Math.sqrt( Math.pow( x - p.x, 2 ) + Math.pow( y - p.y, 2 ) );
	}

	public boolean equals(Point p) {
		if (DishMath.approxSame( x, p.x ) && DishMath.approxSame( y, p.y )) return true;
		return false;
	}

	public String toString() {
		// We don't want to print (0.123456789,9.87654321), we want (0.123,9.876). That should be precise enough
		return "Point:(" + DishMath.cutDecimals( x ) + ";" + DishMath.cutDecimals( y ) + ")";
	}

}
