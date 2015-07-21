package dishcloth.utility;

import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.logger.Debug;
import dishcloth.utility.drawgraph.GraphOptionWindow;
import dishcloth.utility.drawgraph.GraphWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lassi on 21.6.2015.
 */
public class Program {

	public static void main(String[] args) {
		Debug.log( "Hello world!", "DishclothUtility" );
		test();
	}

	private static void test() {
		List<Point> points = new ArrayList<>();
		points.add( new Point( 0, 7 ) );
		points.add( new Point( 1, 8 ) );
		points.add( new Point( 2, 4 ) );
		points.add( new Point( 3, 6 ) );
		points.add( new Point( 5, 14 ) );
		points.add( new Point( 6, 7 ) );
		points.add( new Point( 9, 12 ) );
		points.add( new Point( 16, 2 ) );
		points.add( new Point( 18, 1 ) );
		points.add( new Point( 21, 5 ) );
		points.add( new Point( 25, 8 ) );

		GraphWindow graph = new GraphWindow();
		new GraphOptionWindow( points, graph );
	}
}
