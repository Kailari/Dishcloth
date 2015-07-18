package dishcloth.engine.util.curve;

import dishcloth.engine.util.geom.Point;

import java.util.List;


/**
 * *****************************************************
 * NOT COMPLETED YET. OR MAYBE IT IS I DON'T REMEMBER
 * *****************************************************
 * Created by Lassi on 18.7.2015.
 */
public class PointCurve extends ACurve {

	public PointCurve() {
		super();
	}

	public PointCurve(List<Point> points) {
		super( points );
	}

	@Override
	public float valueAt(float f) {
		int index = getPositionIndex( f );

		if (index == POINTS_SIZE_ZERO) return 0F;
//		if (index == X_LESS_THAN_SMALLEST) throw new IllegalArgumentException("f < smallest x");
//		if (index == X_GREATER_THAN_GREATEST) throw new IllegalArgumentException("f > greatest x");

		if (index == getListSize() - 1) return getPoint( getListSize() - 1 ).y;

		return getPoint( index ).y;
	}
}
