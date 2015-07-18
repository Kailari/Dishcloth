package dishcloth.engine.util.curve;

import dishcloth.engine.util.geom.Point;

import java.util.List;


/**
 * Created by Lassi on 18.7.2015.
 */
public class CosineCurve extends ACurve {

	public CosineCurve() {
		super();
	}

	public CosineCurve(List<Point> points) {
		super( points );
	}

	@Override
	public float valueAt(float f) {
		int index = getPositionIndex( f );

		if (index == POINTS_SIZE_ZERO) return 0F;
		//		if (index == X_LESS_THAN_SMALLEST) throw new IllegalArgumentException("f < smallest x");
		//		if (index == X_GREATER_THAN_GREATEST) throw new IllegalArgumentException("f > greatest x");

		if (index == getListSize() - 1) return (float) getPoint( getListSize() - 1 ).y;

		float ft = (float) (getNormalizedBetween( f, getPoint( index ).x, getPoint( index + 1 ).x ) * Math.PI);
		float a = (float) (1 - Math.cos( ft )) * 0.5F;

		return (float) (getPoint( index ).y * (1 - a) + getPoint( index + 1 ).y * a);
	}

}
