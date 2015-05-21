package dishcloth.engine.util.geom;

import dishcloth.engine.util.DishMath;

/**
 * Created by Lassi on 21.5.2015.
 */
public class Line {


	public Point p1, p2;

	public Line(Point p1, Point p2) {
		if (p1.equals( p2 )) throw new IllegalArgumentException( "Point 1 can't equal point 2." );
		this.p1 = p1;
		this.p2 = p2;
	}

	public Line(Point p1, double slope) {
		this.p1 = p1;
		if (Double.isNaN( slope )) {
			this.p2 = new Point( p1.x, p1.y + 1 );
		} else {
			this.p2 = new Point( p1.x + slope, p1.y + 1 );
		}

	}

	public double slope() {
		if (DishMath.approxSame( p1.x, p2.x )) return Double.NaN; // vertical line
		if (DishMath.approxSame( p1.y, p2.y )) return 0.0; // horizontal line

		return (p2.y - p1.y) / (p2.x - p1.x);
	}
}
