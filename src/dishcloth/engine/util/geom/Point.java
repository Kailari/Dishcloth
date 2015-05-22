package dishcloth.engine.util.geom;

import dishcloth.engine.util.DishMath;

/**
 * Created by Lassi on 21.5.2015.
 */
public class Point {


	public double x, y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}


	public Point() {
		this.x = 0;
		this.y = 0;
	}


	public static double distance(double x1, double y1, double x2, double y2) {
		return distance( new Point( x1, y1 ), new Point( x2, y2 ) );
	}

	public static double distance(Point p1, Point p2) {
		return p1.distance( p2 );
	}

	public static boolean equals(Point p1, Point p2) {
		return p1.equals( p2 );
	}


	public double distance(double x, double y) {
		return distance( new Point( x, y ) );
	}

	public double distance(Point p) {
		return Math.sqrt( Math.pow( x - p.x, 2 ) + Math.pow( y - p.y, 2 ) );
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
