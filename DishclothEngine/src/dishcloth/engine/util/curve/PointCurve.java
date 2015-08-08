package dishcloth.engine.util.curve;

import dishcloth.api.util.geom.Point;
import dishcloth.api.util.memory.PointCache;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lassi on 18.7.2015.
 */
public class PointCurve extends ACurve {


	public PointCurve() {
		super();
	}

	public PointCurve(List<Point> points) {
		super( points );
	}

	public List<Point> getPoints() {
		List<Point> result = new ArrayList<>();
		for (int i = 0; i < getListSize(); i++) {
			result.add( PointCache.getPoint( getPoint( i ) ) );
		}
		return result;
	}

	@Override
	public float valueAt(float f) {
		int index = getPositionIndex( f );

		if (index == POINTS_SIZE_ZERO) return 0F;

		if (index == getListSize() - 1) return getPoint( getListSize() - 1 ).getY();

		return getPoint( index ).getY();
	}
}
