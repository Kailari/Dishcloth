package dishcloth.engine.util.geom;


import static java.lang.Math.*;

import dishcloth.engine.util.DishMath;

/**
 * Created by Lassi on 21.5.2015.
 */
public class IntPoint {

	public int x, y;

	public IntPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}


	public IntPoint() {
		this.x = 0;
		this.y = 0;
	}


	public static int distance(int x1, int y1, int x2, int y2) {
		return distance( new IntPoint( x1, y1 ), new IntPoint( x2, y2 ) );
	}

	public static int distance(IntPoint p1, IntPoint p2) {
		return p1.distance( p2 );
	}

	public static boolean equals(IntPoint p1, IntPoint p2) {
		return p1.equals( p2 );
	}


	public int distance(int x, int y) {
		return distance( new IntPoint( x, y ) );
	}

	public int distance(IntPoint p) {
		return (int) round( sqrt( pow( x - p.x, 2 ) + pow( y - p.y, 2 ) ) );
	}

	public boolean equals(IntPoint p) {
		if (x == p.x && y == p.y) {
			return true;
		}
		return false;
	}

}
