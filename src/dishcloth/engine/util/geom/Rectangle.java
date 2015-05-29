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
	 * Point (x,y) is the <b>bottom left</b> corner.
	 */
	public Rectangle(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public Rectangle(Location location, float x, float y, float w, float h) {
		this( location.getX( x, w ), location.getY( y, h ), w, h );
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
		return new Point( x, y );
	}

	public Point getLeftTop() {
		return new Point( x, y + h );
	}

	public Point getRightBottom() {
		return new Point( x + w, y );
	}

	public Point getRightTop() {
		return new Point( x + w, y + h );
	}

	public boolean containsPoint(Point point) {
		return point.x >= x
				&& point.x < x + w
				&& point.y >= y
				&& point.y < y + h;
	}

	public boolean overlap(Rectangle r) {
		return Rectangle.AOverlapB( this, r ) || Rectangle.AOverlapB( r, this );
	}

	public Rectangle common(Rectangle r) {
		if (!overlap( r )) return null;

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

	public enum Location {
		LeftBot( 0, 0 ), LeftTop( 0, 1 ), RightBot( 1, 0 ), RightTop( 1, 1 );

		private final int xm, ym; // X and Y factors. xm, ym = 0,1

		Location(int xm, int ym) {
			this.xm = xm;
			this.ym = ym;
		}

		private float getX(float x, float w) {
			return x - w * xm;
		}

		private float getY(float y, float h) {
			return y - h * ym;
		}

	}


}
