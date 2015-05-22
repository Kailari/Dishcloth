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

	public double x, y, w, h;

	public Rectangle(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	private static boolean AOverlapB(Rectangle a, Rectangle b) {
		boolean lt, rt, lb, rb;
		lt = a.containsPoint( b.getLeftTop() );
		lb = a.containsPoint( b.getLeftBottom() );
		rt = a.containsPoint( b.getRightTop() );
		rb = a.containsPoint( b.getRightBottom() );

		return lt || lb || rt || rb;
	}

	public Point getLeftTop() {
		return new Point( x, y );
	}

	public Point getRightTop() {
		return new Point( x + w, y );
	}

	public Point getLeftBottom() {
		return new Point( x, y + h );
	}

	public Point getRightBottom() { return new Point( x + w, y + h ); }

	public boolean containsPoint(Point point) {
		return point.x >= x //
				&& point.x < x + w //
				&& point.y >= y //
				&& point.y < y + h; //
	}

	public boolean overlap(Rectangle r) {
		return Rectangle.AOverlapB( this, r ) || Rectangle.AOverlapB( r, this );
	}

	public Rectangle common(Rectangle r) {
		if (!overlap( r )) return null;

		double top = Math.min( getLeftTop().y, r.getLeftTop().y );
		double bottom = Math.max( getLeftBottom().y, r.getLeftBottom().y );

		double left = Math.max( getLeftTop().x, r.getLeftTop().x );
		double right = Math.min( getRightTop().x, r.getRightTop().x );

		return null;
	}


	// TODO: Methods for "is point in rectangle", "Rectangles intersect", "rectangles overlap", etc.
}
