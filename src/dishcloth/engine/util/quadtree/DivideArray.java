package dishcloth.engine.util.quadtree;

import dishcloth.engine.util.DishMath;

/**
 * This class will return a cut from the original array.
 * The original array won't be changed.
 * <p>
 * Created by Lassi on 20.5.2015.
 */
public class DivideArray {

	/**
	 * @param array   original array
	 * @param width   width of the original array
	 * @param height  height of the original array
	 * @param startX  starting coordinates for the new array
	 * @param startY  starting coordinates for the new array
	 * @param width2  width for the <b>result</b> array
	 * @param height2 height for the <b>result</b> array
	 */
	public static Object[] divide(Object[] array, int width, int height, int startX, int startY, int width2, int height2) {
		Object[] result = new Object[width2 * height2];
		// TODO: btw pistin tuohon object[]. voidaan myöhemmin vaihtaa tile[] tai vastaava. toivottavasti tuo toimii
		for (int y = 0; y < height2; y++) {
			for (int x = 0; x < width2; x++) {
				// TODO: handle array index out of bounds
				result[x + y * width2] = array[(startX + x) + (startY + y) * width];
			}
		}
		return result;
	}

	/**
	 * @param array  the array to be split into four parts
	 * @param width  width of the <b>original</b> array, has to be a power of two
	 * @param height height of the <b>original</b> array, has to be a power of two
	 * @return original array split into four parts with each having same size
	 * @throws IllegalArgumentException if width or height are not a power of two
	 */
	public static FourArrays divideIntoFour(Object[] array, int width, int height) {
		// Make sure that width and height are a power of two
		if (!DishMath.powerOf2( width ) || !DishMath.powerOf2( height )) {
			throw new IllegalArgumentException( "Width and height have to be a power of two." );
		}
		FourArrays result = new FourArrays(); // create new empty fourArrays
		for (int i = 0; i < 4; i++) {
			int x = (i % 2) * (width / 2);
			int y = (i / 2) * (height / 2);
			Object[] a = divide( array, width, height, x, y, width / 2, height / 2 );
			result.set( a, i );
		}
		return result;
	}
}
