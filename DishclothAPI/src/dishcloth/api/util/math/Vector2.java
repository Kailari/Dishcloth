package dishcloth.api.util.math;

import dishcloth.api.util.geom.Point;

/**
 * ********************************************************************************************************************
 * Vector2.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Vector datatype with appropriate math operations.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 14.5.2015
 */

public class Vector2 extends Point {

	public Vector2(float x, float y) {
		super( x, y );
		this.x = x;
		this.y = y;
	}
	
	public static Vector2 zero() { return new Vector2( 0f, 0f );}

	public static Vector2 one() { return new Vector2( 1f, 1f );}

	public static Vector2 right() { return new Vector2( 1f, 0f );}

	public static Vector2 left() { return new Vector2( -1f, 0f );}

	public static Vector2 up() { return new Vector2( 0f, 1f );}

	public static Vector2 down() { return new Vector2( 0f, -1f );}

	/**
	 * Adds the two vectors together
	 *
	 * @param v0 Vector #1
	 * @param v1 Vector #2
	 * @return new Vector2 which is sum of the two vectors.
	 */
	public static Vector2 sum(Vector2 v0, Vector2 v1) {
		return new Vector2( v0.x + v1.x, v0.y + v1.y );
	}

	/**
	 * Calculates difference between the two vectors
	 *
	 * @param v0 vector #1
	 * @param v1 vector #2
	 * @return new Vector2 which is the subtraction of the two vectors.
	 */
	public static Vector2 difference(Vector2 v0, Vector2 v1) {
		return new Vector2( v0.x - v1.x, v0.y - v1.y );
	}

	public static Vector2 multiply(Vector2 v0, float scalar) {
		return new Vector2( v0.x * scalar, v0.y * scalar );
	}

	public static Vector2 lerp(Vector2 v0, Vector2 v1, float t) {
		return Vector2.multiply( v0, 1 - t ).add( Vector2.multiply( v1, t ) );
	}

	public float magnitude() {
		return (float) Math.sqrt( Math.pow( x, 2 ) + Math.pow( y, 2 ) );
	}

	/**
	 * Adds the other vector to this vector
	 *
	 * @param vec the other vector
	 * @return this with other vector values added to x and y
	 */
	public Vector2 add(Vector2 vec) {
		x += vec.x;
		y += vec.y;
		return this;
	}

	/**
	 * Subtracts the other vector from this vector
	 *
	 * @param vec the other vector
	 * @return this with other vector values subtracted from x and y
	 */
	public Vector2 subtract(Vector2 vec) {
		x -= vec.x;
		y -= vec.y;
		return this;
	}

	/**
	 * Multiplies this vector by a scalar
	 *
	 * @param scalar scalar multiplier
	 * @return this multiplied by the scalar
	 */
	public Vector2 multiply(float scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	/**
	 * Calculates the dot product of the two vectors
	 *
	 * @param vec the other vector
	 * @return dot product
	 */
	public float dot(Vector2 vec) {
		return x * vec.x + y * vec.y;
	}

	/**
	 * Calculates determinant
	 *
	 * @param vec the other vector
	 * @return the determinant
	 */
	public float determinant(Vector2 vec) {
		return x * vec.y - y * vec.x;
	}

	public float angleTo(Vector2 vec) {
		return (float) Math.toDegrees( Math.atan2( this.dot( vec ), this.determinant( vec ) ) );
	}
}
