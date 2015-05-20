package dishcloth.engine.util.quadtree;

/**
 * This class just contains four arrays. I made this class so we can easily divide arrays into four
 * pieces using the divideIntoFour method in DivideArray class. (We can move this class inside the DivideArray class.)
 * <p>
 * Created by Lassi on 20.5.2015.
 */
public class FourArrays {

	private Object[] a0, a1, a2, a3;

	public FourArrays(Object[] a0, Object[] a1, Object[] a2, Object[] a3) {
		set( a0, 0 );
		set( a1, 1 );
		set( a2, 2 );
		set( a3, 3 );
	}

	public FourArrays() {

	}

	/**
	 * Set one of the four arrays to parameter's array.
	 *
	 * @param index 0-3
	 * @throws IllegalArgumentException if x is not 0-3
	 */
	public void set(Object[] array, int index) {
		if (index == 0) {
			a0 = array;
		} else if (index == 1) {
			a1 = array;
		} else if (index == 2) {
			a2 = array;
		} else if (index == 3) {
			a3 = array;
		} else {
			throw new IllegalArgumentException( "The index has to be 0-3." );
		}

	}

	/**
	 * Returns one of the four arrays.
	 * Please note that returned array can be null if object has been initialized without arrays.
	 *
	 * @param n the array to be returned. Has to be 0-3
	 * @throws IllegalArgumentException if n is not 0-3
	 */
	public Object[] get(int n) {
		if (n == 0) return a0;
		if (n == 1) return a1;
		if (n == 2) return a2;
		if (n == 3) return a3;
		throw new IllegalArgumentException( "The index has to be 0-3." );
	}
}
