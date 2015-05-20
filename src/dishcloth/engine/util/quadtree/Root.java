package dishcloth.engine.util.quadtree;

import dishcloth.engine.util.DishMath;

/**
 * This is the root of the quadtree. Object of this class will either: <br>
 * -Have 4 children roots in array[] "children". <br>
 * -Have size, x and y coordinates and color(=type). This is called a leaf node.
 * <p>
 * Created by Lassi on 20.5.2015.
 */
public class Root {
	// TODO: osuvampi nimi varmaan olisi node, mutten nimennyt nodeksi koska jos sitä nimeä käytetään myöhemmin

	private boolean leaf; // if leaf is true, this object contains only one type of data
	private int size, xc, yc;
	// private Tile type;

	// I think I should add "private Root parent;", so we can easily update the tree

	/**
	 * Child nodes. This will be null is leaf=true. <br>
	 * Coordinates of child nodes are clockwise, starting from top left corner. <br>
	 * 0 = upper left <br>
	 * 1 = upper right <br>
	 * 2 = lower left <br>
	 * 3 = lower right <br>
	 */
	private Root[] children;

	/**
	 * @param grid this array will be replaced with Tile[] when we create that class
	 * @param size size of the square. This should be a power of two
	 * @param xc   x-coordinate for the first node
	 * @param yc   y-coordinate for the first node
	 */
	public Root(Object[] grid, int size, int xc, int yc) {
		leaf = sameType( grid );
		if (!DishMath.powerOf2( size )) {
			// if for some reason size is not a power of two, alarm the user
			System.out.println( "Error in the root class! Size is not a power of two." );
			System.out.println( "Size = " + size + ", " + "@" + Integer.toHexString( hashCode() ) );
		}

		if (leaf) {
			// root contains only one color(or type)
			this.size = size;
			this.xc = xc;
			this.yc = yc;
			// TODO: implement this:   this.type = grid[0];
		} else {
			// root contains more than one color(or type), split it into four pieces
			this.size = size;
			this.xc = xc;
			this.yc = yc;

			children = new Root[4];

			// create the children
			FourArrays ch4 = DivideArray.divideIntoFour( grid, size / 2, size / 2 );

			for (int i = 0; i < children.length; i++) {
				int xx = (i % 2) * (size / 2) + xc; // i=1,3(on right) --> add size/2 to xc
				int yy = (i / 2) * (size / 2) + yc; // i=2,3(down) --> add size/2 to yc
				children[i] = new Root( ch4.get( i ), size / 2, xx, yy );

			}
		}
	}

	private boolean sameType(Object[] grid) {
		Object p = grid[0]; // select the first object from the array and compare the rest to this
		for (int i = 0; i < grid.length; i++) {
			if (grid[i] != p) {
				return false; // if there is even one object that does not equal the first one, return false
			}
		}
		return true;
	}

	/**
	 * Returns the child nodes of this root. Note that the array will be null if this root in leaf.
	 * You can check is this node leaf by using {@link #leaf()} method
	 *
	 * @return child nodes of this root
	 */
	public Root[] getChildren() {
		return children;
	}

	/**
	 * Check if this node contains only one type of items.
	 */
	public boolean leaf() {
		return leaf;
	}

}
