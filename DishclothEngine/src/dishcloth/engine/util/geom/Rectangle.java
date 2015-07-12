package dishcloth.engine.util.geom;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Rectangle.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 21.5.2015
 */

public class Rectangle {

	public float x, y, w, h;

	/**
	 * Point (x,y) is the <b>top left</b> corner.
	 */
	public Rectangle(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public Rectangle(Rectangle source) {
		this.x = source.x;
		this.y = source.y;
		this.w = source.w;
		this.h = source.h;
	}

	private static boolean AOverlapB(Rectangle a, Rectangle b) {
		boolean lt, rt, lb, rb;

		lt = a.containsPoint( b.getLeftTop() );
		lb = a.containsPoint( b.getLeftBottom() );
		rt = a.containsPoint( b.getRightTop() );
		rb = a.containsPoint( b.getRightBottom() );

		return lt || lb || rt || rb;
	}

	public Point getLeftBottom() {
		return new Point( x, y + h );
	}

	public Point getLeftTop() {
		return new Point( x, y );
	}

	public Point getRightBottom() {
		return new Point( x + w, y + h );
	}

	public Point getRightTop() {
		return new Point( x + w, y );
	}

	public boolean containsPoint(Point point) {
		return point.x >= x
				&& point.x < x + w
				&& point.y >= y
				&& point.y < y + h;
	}

	public boolean containsRectangle(Rectangle rectangle) {
		// A bit hacky way of doing it, but who cares :P

		// If rectangle overlaps other, but the other rectangle does not, it means that the other is completely
		// inside the first rectangle.
		return Rectangle.AOverlapB( this, rectangle ) && !Rectangle.AOverlapB( rectangle, this );
	}

	public boolean overlaps(Rectangle r) {
		return Rectangle.AOverlapB( this, r ) || Rectangle.AOverlapB( r, this );
	}

	public Rectangle commonArea(Rectangle r) {
		if (!overlaps( r )) return null;

		float top = Math.min( getLeftTop().y, r.getLeftTop().y );
		float bottom = Math.max( getLeftBottom().y, r.getLeftBottom().y );

		float left = Math.max( getLeftBottom().x, r.getLeftBottom().x );
		float right = Math.min( getRightBottom().x, r.getRightBottom().x );

		float dx = right - left;
		float dy = top - bottom;

		return new Rectangle( left, bottom, dx, dy );
	}

	@Override
	public String toString() {
		return "Rectangle:[" +
				"x=" + x +
				"; y=" + y +
				"; w=" + w +
				"; h=" + h +
				"]";
	}
}
