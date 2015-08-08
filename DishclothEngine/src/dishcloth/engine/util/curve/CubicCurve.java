package dishcloth.engine.util.curve;


import dishcloth.api.util.geom.Point;

import java.util.List;

/**
 * Created by Lassi on 18.7.2015.
 */
public class CubicCurve extends ACurve {
	
	public CubicCurve() {
		super();
	}
	
	public CubicCurve(List<Point> points) {
		super( points );
	}
	
	@Override
	public float valueAt(float f) {
		int index = getPositionIndex( f );
		
		if (index == POINTS_SIZE_ZERO) return 0F;
//		if (index == X_LESS_THAN_SMALLEST) throw new IllegalArgumentException("f < smallest x");
//		if (index == X_GREATER_THAN_GREATEST) throw new IllegalArgumentException("f > greatest x");
		
		if (index == getListSize() - 1) return getPoint( getListSize() - 1 ).getY();
		
		float n = getNormalizedBetween( f, getPoint( index ).getX(), getPoint( index + 1 ).getX() );
		
		float v1 = getPoint( index ).getY(); // the point a
		float v2 = getPoint( index + 1 ).getY(); // the point b
		
		float v0 = getPoint( index ).getY(); // the point before a
		float v3 = getPoint( index + 1 ).getY(); // the point after b
		if (index > 0) v0 = getPoint( index - 1 ).getY();
		if (index < getListSize() - 2) v3 = getPoint( index + 2 ).getY();
		
		float p = (v3 - v2) - (v0 - v1);
		float q = (v0 - v1) - p;
		float r = v2 - v0;
		//float s = v1;

		return (float) (p * Math.pow( n, 3 ) + q * Math.pow( n, 2 ) + r * n + v1);
	}
	/**
	 * v0 = the point before a <br>
	 * v1 = the point a <br>
	 * v2 = the point b <br>
	 * v3 = the point after b <br>
	 *
	 * function Cubic_Interpolate(v0, v1, v2, v3,x) <br>
	 * P = (v3 - v2) - (v0 - v1) <br>
	 * Q = (v0 - v1) - P <br>
	 * R = v2 - v0 <br>
	 * S = v1 <br>
	 *
	 * return Px3 + Qx2 + Rx + S end of function
	 */
	
}
