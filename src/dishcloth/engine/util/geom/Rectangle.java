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

	public Point getLeftTop() {
		return new Point( x, y );
	}

	public Point getRightTop() {
		return new Point( x + w, y );
	}

	public Point getLeftBottom() {
		return new Point( x, y + h );
	}

	public Point getRightBottom() {
		return new Point( x + w, y + h );
	}

	public boolean containsPoint(Point point) {
		return point.x > x
				&& point.x < x + w
				&& point.y > y
				&& point.y < y + h;
	}


	// TODO: Methods for "is point in rectangle", "Rectangles intersect", "rectangles overlap", etc.
}
