package dishcloth.api.util.memory;

import dishcloth.api.util.geom.Rectangle;

/**
 * <b>RectangleCache</b>
 * <p>
 * Cache for rectangles
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 6.8.2015 at 16:57
 */

public class RectangleCache {
	private static SoftReferencedCache<Rectangle> cache = new SoftReferencedCache<>();

	public static Rectangle getRectangle(Rectangle source) {
		return getRectangle( source.getX(), source.getY(), source.getW(), source.getH() );
	}

	public static Rectangle getRectangle(float x, float y, float w, float h) {
		Rectangle rectangle = cache.getReferencedItem();

		if (rectangle != null) {
			setRectangleValues( rectangle, x, y, w, h );
		} else {
			rectangle = createRectangle( x, y, w, h );
		}

		return rectangle;
	}

	public static void cacheRectangle(Rectangle rectangle) {
		cache.addReferencedItem( rectangle );
	}

	private static Rectangle createRectangle(float x, float y, float w, float h) {
		return new InternalRectangle( x, y, w, h );
	}

	private static Rectangle setRectangleValues(Rectangle rectangle, float x, float y, float w, float h) {
		rectangle.setX( x );
		rectangle.setY( y );
		rectangle.setW( w );
		rectangle.setH( h );
		return rectangle;
	}

	private static class InternalRectangle extends Rectangle {
		public InternalRectangle(float x, float y, float w, float h) {
			super( x, y, w, h );
		}
	}
}
