package dishcloth.api.util.geom;

import dishcloth.api.util.math.DishMath;
import dishcloth.api.util.memory.PointCache;

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

	/**
	 * Returns the slope of the line
	 *
	 * @return NaN if line is vertical, 0 if line is horizontal. Otherwise returns the slope
	 */
	public float slope() {
		if (DishMath.approxSame( p1.x, p2.x )) return Float.NaN; // vertical line
		if (DishMath.approxSame( p1.y, p2.y )) return 0.0f; // horizontal line

		return (p2.y - p1.y) / (p2.x - p1.x);
	}
	
	public Line(Point p1, float slope) {
		this.p1 = p1;
		if (Double.isNaN( slope )) {
			this.p2 = PointCache.getPoint( p1.x, p1.y + 1 );
		} else {
			this.p2 = PointCache.getPoint( p1.x + slope, p1.y + 1 );
		}
		
	}

	/**
	 * @return point where two lines meet. If these two lines are parallel, result will be null
	 */
	public Point intersection(Line l) {
		if (Float.isNaN( slope() ) && Float.isNaN( l.slope() )) return null;
		if (DishMath.approxSame( slope(), l.slope() )) return null;

		/*
		 * y1=y2
		 * k1*x+c1=k2*x+c2
		 * x*(k1-k2)=c2-c1
		 * x=(c2-c1)/(k1-k2)
		 * y=x*k1+c1
		 */

		float x = (l.getConstant() - getConstant()) / (slope() - l.slope());
		float y = x * slope() + getConstant();

		Point p = PointCache.getPoint( x, y );
		return p;
	}

	/**
	 * Returns the y-coordinate where the line intersects with y-axis
	 *
	 * @return NaN if the line is horizontal
	 */
	public float getConstant() {
		/*
		 * y-y0=k(x-x0)
		 * y=k*x-k*x0+y0
		 * => y=k*x+c, c=y0-k*x0
		 */
		if (Double.isNaN( slope() ) && DishMath.approxSame( p1.x, 0 )) return Float.NaN;
		if (DishMath.approxSame( slope(), 0 )) return p1.y;

		return p1.y - slope() * p1.x;
	}

	@Override
	public String toString() {
		if (Double.isNaN( slope() )) return "Line: x=" + p1.x;
		if (DishMath.approxSame( slope(), 0 )) return "Line: y=" + getConstant();

		String s = "Line: y=" + DishMath.cutDecimals( slope() ) + "*x";
		float c = getConstant();
		if (DishMath.approxSame( c, 0 )) {
			return s; // return y=k*x (without +c)
		} else if (c > 0) {
			s += "+"; // if constant > 0, we need to add the plus sign. Otherwise the c already contains it
		}
		s += DishMath.cutDecimals( c );
		return s;
	}

	public boolean equals(Line l) {
		boolean v1 = Double.isNaN( slope() ); // is line #1 vertical
		boolean v2 = Double.isNaN( l.slope() ); // is line #2 vertical

		boolean sameCon = DishMath.approxSame( getConstant(), l.getConstant() ); // does c1 = c2

		if (!sameCon) return false; // c1 != c2

		if (v1 != v2) return false; // another one is vertical, and the other is not
		if (v1 && v2) return true; // Both are vertical, c1 = c2

		if (DishMath.approxSame( slope(), l.slope() )) return true; // c1 = c2, k1 = k2
		return false; // k1 != k2

	}
}
