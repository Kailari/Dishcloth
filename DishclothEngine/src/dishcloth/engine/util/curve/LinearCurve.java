package dishcloth.engine.util.curve;

import dishcloth.api.util.geom.Point;

import java.util.List;

/**
 * Created by Lassi on 18.7.2015.
 */
public class LinearCurve extends ACurve {

	public LinearCurve() {
		super();
	}

	public LinearCurve(List<Point> points) {
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

		return getPoint( index ).getY() * (1 - n) + getPoint( index + 1 ).getY() * n;
	}

}
