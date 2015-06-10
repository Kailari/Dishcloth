package dishcloth.engine.util.geom;

import dishcloth.engine.util.math.DishMath;

/**
 * Created by Lassi on 21.5.2015.
 */
public class Point {


	public float x, y;

	public Point(Point point) {
		this.x = point.x;
		this.y = point.y;
	}

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}


	public Point() {
		this.x = 0;
		this.y = 0;
	}

	public Point (IntPoint point) {
		this.x = point.x;
		this.y = point.y;
	}

	public IntPoint toIntPoint() {
		return new IntPoint( Math.round( this.x ), Math.round( this.y ) );
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
		// We don't want to print (0.123456789,9.87654321), we want (0.123,9.876). That should be precise enough
		return "Point:(" + DishMath.cutDecimals( x ) + ";" + DishMath.cutDecimals( y ) + ")";
	}

}