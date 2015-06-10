package dishcloth.engine.util.quadtree;

import dishcloth.engine.util.math.DishMath;
import dishcloth.engine.world.ITile;

/**
 * This class will hold the "master quadTrees" of the quadtree (aka the ancestor quadTrees).
 * For example if the grid is 8x8, this class will hold only one root with size 8*8.
 * Respectively if the grid is 16*8, this class will hold two quadTrees, one with size 8*8 and x-coordinate 0, and the other
 * one with size 8*8 and x-coordinate 8 (size*1).
 * <p>
 * Created by Lassi on 20.5.2015.
 */
public class LevelGrid {

	private QuadTree<ITile>[] quadTrees;

	private int xn, yn; // number of ancestor quadTrees
	private int size; // size of the ancestor quadTrees


	/**
	 * Create a master(ancestor) branch for the quadtree.
	 * This object will contain an array of the quadTrees. The array's size will be either 1*n or n*1, where n is an integer.
	 *
	 * @param grid   the grid or the tiles to be squared. I'll consider changing this later to Tile[ ]
	 * @param width  width of the grid, has to be a power of two
	 * @param height height of the gris, has to be a power of two
	 * @throws IllegalArgumentException if width and height are not a power of two
	 */
	public LevelGrid(Object[] grid, int width, int height) {
		/*
		* actually, now that I think of it...
		* I guess width only has to be a MULTIPLE of HEIGHT (and height is a power of two), or vice versa.
		* but we can't implement that because the position of the stars is incorrect and the government of Portugal is
		* corrupt aka. I'm too lazy. https://www.youtube.com/watch?v=VlcUK3Os1j0 (1:25)
		* for example w=12,h=4 would work, since h=2^2 and w=3*h and we would have 3*1 quadTrees with size 4.
		*/

		if (!DishMath.powerOf2( width ) || !DishMath.powerOf2( height )) {
			throw new IllegalArgumentException( "Width and height have to be a power of two." );
		}

		int min = Math.min( width, height ); // select the smallest square
		this.size = min;

		// calculate how many quadTrees will be created in the vertical and horizontal direction
		int xn = width / min;
		int yn = height / min;
		// to clarify: let width=16,height=4. min=4. xn=4,yn=1. so we have 4*1 quadTrees

		quadTrees = new QuadTree[xn * yn];
		this.xn = xn;
		this.yn = yn;

		for (int yi = 0; yi < yn; yi++) {
			for (int xi = 0; xi < xn; xi++) {
				// divide original array
				Object[] array = DivideArray.divide( grid, width, height, xi * min, yi * min, min, min );

				// make new root with the recently created array
				//QuadTree r = new QuadTree( array, min, xi * min, yi * min );

				//quadTrees[xi + yi * xn] = r;
			}
		}

	}
}
