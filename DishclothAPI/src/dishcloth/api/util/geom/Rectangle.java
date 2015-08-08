package dishcloth.api.util.geom;

import dishcloth.api.util.memory.PointCache;
import dishcloth.api.util.memory.RectangleCache;

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

	protected float x, y, w, h;

	protected Rectangle(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	private static boolean AOverlapB(Rectangle a, Rectangle b) {
		return a.getLeftX() < b.getRightX()
				&& a.getRightX() > b.getLeftX()
				&& a.getTopY() > b.getBottomY()
				&& a.getBottomY() < b.getTopY();

		/*
		boolean lt, rt, lb, rb;
		lt = a.containsPoint( b.getLeftX(), b.getTopY() );
		lb = a.containsPoint( b.getLeftX(), b.getBottomY() );
		rt = a.containsPoint( b.getRightX(), b.getTopY() );
		rb = a.containsPoint( b.getRightX(), b.getBottomY() );

		return lt || lb || rt || rb;
		*/
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}

	public Point getLeftBottom() {
		return PointCache.getPoint( getLeftX(), getBottomY() );
	}

	public Point getLeftTop() {
		return PointCache.getPoint( getLeftX(), getTopY() );
	}

	public Point getRightBottom() {
		return PointCache.getPoint( getRightX(), getBottomY() );
	}

	public Point getRightTop() {
		return PointCache.getPoint( getRightX(), getTopY() );
	}

	public float getLeftX() {
		return x;
	}

	public float getRightX() {
		return x + w;
	}

	public float getTopY() {
		return y + h;
	}

	public float getBottomY() {
		return y;
	}

	public boolean containsPoint(Point point) {
		return containsPoint( point.x, point.y );
	}

	public boolean containsPoint(float otherX, float otherY) {
		return otherX >= x
				&& otherX < x + w
				&& otherY >= y
				&& otherY < y + h;
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

	// TODO: Check if coordinates are correct with current ever-changing coordinate system
	public Rectangle commonArea(Rectangle r) {
		if (!overlaps( r )) return null;

		float top = Math.min( getTopY(), r.getTopY() );
		float bottom = Math.max( getBottomY(), r.getBottomY() );

		float left = Math.max( getLeftX(), r.getLeftX() );
		float right = Math.min( getRightX(), r.getRightX() );

		float dx = right - left;
		float dy = top - bottom;

		return RectangleCache.getRectangle( left, bottom, dx, dy );
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
