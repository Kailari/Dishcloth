package dishcloth.engine.util;

/**
 * I will put some simple functions here which will be used frequently in our engine
 * <p>
 * Created by Lassi on 20.5.2015.
 */
public class DishMath {

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

		if (x < 0) return false; // if x is less than zero, then n is less than one. Only return true if x > 0

		if (x % 1 == 0) return true; // check if x is an integer
		return false;
	}

}
