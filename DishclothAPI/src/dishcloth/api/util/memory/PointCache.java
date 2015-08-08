package dishcloth.api.util.memory;

import dishcloth.api.util.geom.Point;
import dishcloth.api.util.geom.Point;

/**
 * <b>PointCache</b>
 * <p>
 * Cache for Points
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 6.8.2015 at 16:57
 */

public class PointCache {
	private static SoftReferencedCache<Point> cache = new SoftReferencedCache<>();

	public static Point getPoint(Point source) {
		return getPoint( source.getX(), source.getY() );
	}

	public static Point getPoint(float x, float y) {
		Point point = cache.getReferencedItem();

		if (point != null) {
			setPointValues( point, x, y );
		} else {
			point = createPoint( x, y );
		}

		return point;
	}

	public static void cachePoint(Point point) {
		cache.addReferencedItem( point );
	}

	private static Point createPoint(float x, float y) {
		return new InternalPoint( x, y );
	}

	private static Point setPointValues(Point point, float x, float y) {
		point.setX( x );
		point.setY( y );
		return point;
	}

	private static class InternalPoint extends Point {
		public InternalPoint(float x, float y) {
			super( x, y );
		}
	}
}
