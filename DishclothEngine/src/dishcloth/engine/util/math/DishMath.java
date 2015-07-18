package dishcloth.engine.util.math;

import java.text.DecimalFormat;

/**
 * I will put some simple functions here which will be used frequently in our engine
 * <p>
 * Created by Lassi on 20.5.2015.
 */
public class DishMath {

	private static final double THRESHOLD = 0.001; // 10^-3
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat( "#.###" );


	private DishMath() {}

	/**
	 * Check if the number <b>n</b> is a power of two.
	 * Function will return false if <b>n</b> is negative power of two.
	 * (For example n with value 1/16(=2^-4) will return false)
	 *
	 * @param n the number to be tested. n has to be
	 * @return true if n is a power of two (and greater than one)
	 * @throws IllegalArgumentException if n is less or equal to zero
	 */
	public static boolean powerOf2(double n) {
		// 2^x=n <=> log(2)n=x, where n > 0 and x is an integer

		// make sure that n > 0
		if (n <= 0) {
			throw new IllegalArgumentException( "Logarithm thingy has to be greater than 0 :)." ); // don't remember the correct term
		}

		double x = Math.log10( n ) / Math.log10( 2 ); // log(2)n=log(10)n/log(10)2

		if (x < 0) return false; // if x is less than zero, then n is less than one. Only return true if x >= 0

		if (isInteger( x )) return true;// check if x is an integer
		// if (x % 1 == 0) return true; // the old way
		return false;
	}


	public static boolean approxSame(double d1, double d2, double threshold) {
		double delta = Math.abs( d1 - d2 );
		if (delta <= threshold) return true;
		return false;
	}

	/**
	 * Test if two doubles are approximately same using threshold value 10^-3
	 */
	public static boolean approxSame(double d1, double d2) {
		return approxSame( d1, d2, DishMath.THRESHOLD );
	}

	/**
	 * Test if double is approximately integer
	 */
	public static boolean isInteger(double d) {
		double delta = Math.abs( d % 1 ); // 0 < delta < 1

		boolean b1 = approxSame( delta, 1 ); // true when delta > 0.999
		boolean b2 = approxSame( delta, 0 ); // true when delta < 0.001

		return (b1 || b2);
	}

	/**
	 * Cuts some excess decimals off the double
	 */

	public static String cutDecimals(double d) {
		return DECIMAL_FORMAT.format( d );
	}

	/**
	 * NOTE: NOT THE SAME AS Math.signum, returns 1 even when value == 0
	 *
	 * @return 1 if d >= 0, otherwise -1
	 */
	public static int sign(double d) {
		if (d >= 0) return 1;
		return -1;
	}

	/**
	 * Linear interpolation between values
	 *
	 * @param a starting value
	 * @param b target value
	 * @param t interpolation "time". 0f means result is a, 1f means result is b.
	 *          0.5f is the midpoint between the values.
	 */
	public static float lerp(float a, float b, float t) {
		return (1f - t) * a + t * b;    // More precise. No risk of floating point errors.
		//return a + t * (b - a);
	}

	/**
	 * Essentially the same as Math.floor, but a lot faster.
	 */
	public static int fastFloor(double f) {
		int i = (int) f;
		return f < i ? i - 1 : i;
	}

	/**
	 * Essentially the same as Math.floor, but a lot faster.
	 */
	public static int fastFloor(float f) {
		int i = (int) f;
		return f < i ? i - 1 : i;
	}


	/**
	 * The most important method in our game.
	 */
	public static boolean seksuaalistaMerkitysta(String asia1, String asia2) {
		if (asia1 == "Brock" && asia2 == "Lihapullat") {
			return ((1 != 0) && ((2 - 1 == 1 - 2) || (2 - 1 != 1))) == ((false && !true) || ((!false == !!!true) || (("Kissat" == "Koirat") && true)));
		}
		return false;
	}

	public static float clampAngle(float deg) {
		float result = deg % 360f;
		if (result < 0) return result + 360f;
		return result;
	}

	public static float clampAngleRad(float rad) {
		float result = (float) (rad % 2 * Math.PI);
		if (result < 0) return result + (float) (2 * Math.PI);
		return result;
	}


	public static int nearestPowerOfTwo(int number) {
		return 32 - Integer.numberOfLeadingZeros( number - 1 );
	}
}
