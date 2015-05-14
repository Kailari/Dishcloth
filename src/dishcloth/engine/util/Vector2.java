package dishcloth.engine.util;

/**
 * ********************************************************************************************************************
 * Vector2.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Vector datatype with appropriate math operations.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 14.5.2015
 */

public class Vector2 {

    // CONSTANTS -->

    public static final Vector2 ZERO    = new Vector2( 0f, 0f);
    public static final Vector2 ONE     = new Vector2( 1f, 1f);

    public static final Vector2 RIGHT   = new Vector2( 1f, 0f);
    public static final Vector2 LEFT    = new Vector2(-1f, 0f);
    public static final Vector2 UP      = new Vector2( 0f, 1f);
    public static final Vector2 DOWN    = new Vector2( 0f,-1f);

    // <-- CONSTANTS

    public double x;
    public double y;
    
    public double magnitude() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }


    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }


    /**
     * Adds the two vectors together
     * @param v0    Vector #1
     * @param v1    Vector #2
     * @return      new Vector2 which is sum of the two vectors.
     */
    public static Vector2 sum(Vector2 v0, Vector2 v1) {
        return new Vector2( v0.x + v1.x, v0.y + v1.y );
    }

    /**
     * Adds the other vector to this vector
     * @param vec   the other vector
     * @return      this with other vector values added to x and y
     */
    public Vector2 add(Vector2 vec) {
        x += vec.x;
        y += vec.y;
        return this;
    }

    /**
     * Calculates difference between the two vectors
     * @param v0    vector #1
     * @param v1    vector #2
     * @return      new Vector2 which is the subtraction of the two vectors.
     */
    public static Vector2 difference(Vector2 v0, Vector2 v1) {
        return new Vector2( v0.x - v1.x, v0.y - v1.y );
    }

    /**
     * Subtracts the other vector from this vector
     * @param vec   the other vector
     * @return      this with other vector values subtracted from x and y
     */
    public Vector2 subtract(Vector2 vec) {
        x -= vec.x;
        y -= vec.y;
        return this;
    }

    /**
     * Multiplies this vector by a scalar
     * @param scalar    scalar multiplier
     * @return  this multiplied by the scalar
     */
    public Vector2 multiply(double scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    public static Vector2 multiply(Vector2 v0, double scalar) {
        return new Vector2( v0.x * scalar, v0.y * scalar );
    }

    /**
     * Calculates the dot product of the two vectors
     * @param vec   the other vector
     * @return      dot product
     */
    public double dot(Vector2 vec) {
        return x * vec.x + y * vec.y;
    }

    /**
     * Calculates determinant
     * @param vec   the other vector
     * @return      the determinant
     */
    public double determinant(Vector2 vec) {
        return x * vec.y - y * vec.x;
    }

    public double angleTo(Vector2 vec) {
        return Math.toDegrees( Math.atan2( this.dot(vec), this.determinant(vec)) );
    }

    public static Vector2 lerp(Vector2 v0, Vector2 v1, double t) {
        return Vector2.multiply(v0, 1 - t).add(Vector2.multiply(v1, t));
    }
}
