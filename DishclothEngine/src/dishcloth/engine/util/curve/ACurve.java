package dishcloth.engine.util.curve;

import dishcloth.engine.util.geom.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lassi on 13.7.2015.
 */
public abstract class ACurve {
	
	// TODO: looping curve
	
	/**
	 * @see #getPositionIndex(float)
	 */
	public static final int X_LESS_THAN_SMALLEST = -1, X_GREATER_THAN_GREATEST = -2, POINTS_SIZE_ZERO = -3;
	
	private List<Point> points;
	
	public ACurve() {
		points = new ArrayList<>();
	}
	
	public ACurve(List<Point> points) {
		this.points = new ArrayList<>(); // do NOT assign 'this.points = points', because we want to arrange the constructor points
		points.forEach( this::addPoint );
		/*
		for (int i = 0; i < points.size(); i++) {
			addPoint(points.get(i));
		}
		 */
	}
	
	protected final int getListSize() {
		return points.size();
	}
	
	protected final Point getPoint(int index) {
		// TODO: return new Point(points.get(index));
		return points.get( index );
	}
	
	public void debug() {
		points.forEach( System.out::println );
		/*
		for (Point point : points) {
			System.out.println( point );
		}
		 */
	}
	
	/**
	 * {@code #getPositionIndex(Point point)} defaults to
	 * {@link #getPositionIndex(float)}.
	 *
	 * @see #getPositionIndex(float)
	 */
	protected int getPositionIndex(Point point) {
		return getPositionIndex( point.x );
	}
	
	/**
	 * <p>
	 * This method gets the two points where the parameter's point is located
	 * between. Example: <br>
	 * Points '<code>p1(x=1)</code>' and '<code>p2(x=3)</code>' are in 'points'
	 * list. <br>
	 * <code>p1</code> has a index 4 and <code>p2</code> has a index 5. <br>
	 * So when calling this method with '<code>point(x=2)</code>', it will
	 * return 4.
	 * </p>
	 * If the parameter's x-value is smaller than smallext x-value, the result
	 * will be -1. ({@link #X_LESS_THAN_SMALLEST}) <br>
	 * If the parameter's x-value is greater than greatest x-value, the result
	 * will be -2.
	 *
	 * @param f yes this method takes a float as a parameter. Don't know what
	 *          to write in this comment
	 * @return the smaller index of the two points that are next to parameter's
	 * point<br>
	 * -1 if the parameter's x-value is smaller than smallext x-value <br>
	 * -2 if the parameter's x-value is greater than greatest x-value <br>
	 * -3 if the size of the points-list is zero
	 */
	protected int getPositionIndex(float f) {
		if (points.size() == 0) return POINTS_SIZE_ZERO;
		if (f > points.get( points.size() - 1 ).x) return X_GREATER_THAN_GREATEST;
		
		int index = 0;
		
		if (f < points.get( 0 ).x) index = X_LESS_THAN_SMALLEST;
		
		for (int i = 0; i < points.size(); i++) {
			if (f == points.get( i ).x) {
				index = i;
				break;
			}
			if (f < points.get( i ).x) {
				index = i - 1;
				break;
			}
		}
		
		return index;
	}
	
	public float getXMin() {
		if (points.size() == 0) return 0F;
		return points.get( 0 ).x;
	}
	
	public float getXMax() {
		if (points.size() == 0) return 0F;
		return points.get( points.size() - 1 ).x;
	}
	
	public float getYMin() {
		if (points.size() == 0) return 0F;
		float smallest = points.get( 0 ).y;
		for (Point point : points) {
			if (point.y < smallest) smallest = point.y;
		}
		return smallest;
	}
	
	public float getYMax() {
		if (points.size() == 0) return 0F;
		float greatest = points.get( 0 ).y;
		for (Point point : points) {
			if (point.y > greatest) greatest = point.y;
		}
		return greatest;
	}
	
	public final void clear() {
		points.clear();
	}
	
	public final boolean removePoint(float x, float y) {
		return removePoint( new Point( x, y ) );
	}
	
	public final boolean removePoint(Point point) {
		return points.remove( point );
	}
	
	public final void addPoint(float x, float y) {
		addPoint( new Point( x, y ) );
	}
	
	public final void addPoint(Point point) {
		// adds the point to the list in numerical order
		
		// no points in list
		if (points.size() == 0) {
			points.add( point );
			return;
		}
		if (point.x < points.get( 0 ).x) {
			/*
			 * if point.x is less than smallest value of the list, add it to
			 * first index
			 */
			points.add( 0, point );
			return;
		}
		
		if (point.x > points.get( points.size() - 1 ).x) {
			/*
			 * if point is greater than greatest value of the list, add it to
			 * last index
			 */
			points.add( points.size(), point );
			return;
		}
		for (int i = 0; i < points.size(); i++) {
			if (point.x < points.get( i ).x) {
				points.add( i, point );
				return;
			}
			
		}
		
	}
	
	/**
	 * @param f the value on the x-axis. 'f' does <b>not</b> have to be [0,1].
	 *          (See: valueAtNormalized() )
	 */
	public abstract float valueAt(float f);
	
	/**
	 * @param f the normalized value of the x-axis
	 */
	public final float valueAtNormalized(float f) {
		//		if (f < 0 || f > 1) throw new IllegalArgumentException("'f' has to be [0,1]."); 
		// TODO: implement looping
		if (f < 0) f = 0;
		if (f > 1) f = 1;
		return valueAt( convertToCurvePosition( f ) );
	}
	
	/**
	 * Turns the normalized value into x-value
	 *
	 * @param f01 float which is between 0 and 1 (or equal)
	 * @return the x-value
	 */
	protected final float convertToCurvePosition(float f01) {
		//		if (f01 < 0 || f01 > 1) throw new IllegalArgumentException("'f' has to be [0,1].");
		// TODO: implement looping
		if (f01 < 0) f01 = 0;
		if (f01 > 1) f01 = 1;
		
		// no points, return zero, or should we throw an exception?
		if (points.size() == 0) return 0F;
		
		// calculate the difference between the first point (index = 0) and the last point (index = size-1)
		float total = points.get( points.size() - 1 ).x - points.get( 0 ).x;
		return total * f01;
	}
	
	/**
	 * Turns the x-value to normalized float value (which is between 0 and 1)
	 *
	 * @param f x-value
	 * @return normalized x-value
	 */
	protected final float convertToNormalized(float f) {
		// TODO: implement looping
		if (f < getXMin()) f = getXMin();
		if (f > getXMax()) f = getXMax();

		/*
		 * normalized = delta / total;
		 * 
		 * where delta is the distance between first point and f, and total is
		 * the difference between first point and last point
		 */
		
		// no points, return zero, or should we throw an exception?
		if (points.size() == 0) return 0F;
		
		// f == 0 --> return the x-value of the first point
		if (f == 0) return points.get( 0 ).x;
		
		// calculate the difference between the first point (index = 0) and the last point (index = size-1)
		float total = points.get( points.size() - 1 ).x - points.get( 0 ).x;
		float delta = f - points.get( 0 ).x;
		
		return delta / total;
	}

	/**
	 * Normalizes the value f between points x1 and x2
	 *
	 * @param f  the x-value to be normalized
	 * @param x1 the first point's x-value
	 * @param x2 the second point's x-value
	 * @return
	 */
	protected float getNormalizedBetween(float f, float x1, float x2) {
		float delta = x1 - f;
		float total = x1 - x2;

		return delta / total;
	}
	
}