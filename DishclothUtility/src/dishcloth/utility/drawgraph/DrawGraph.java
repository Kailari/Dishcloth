package dishcloth.utility.drawgraph;

import dishcloth.api.util.geom.Point;
import dishcloth.api.util.memory.PointCache;

import java.util.ArrayList;
import java.util.List;

/**
 * DrawGraph.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 22.7.2015
 */

public class DrawGraph {

	public static void run() {
		List<Point> points = new ArrayList<>();
	/*	points.add( new Point( 0, 7 ) );
		points.add( new Point( 1, 8 ) );
		points.add( new Point( 2, 4 ) );
		points.add( new Point( 3, 6 ) );
		points.add( new Point( 5, 14 ) );
		points.add( new Point( 6, 7 ) );
		points.add( new Point( 9, 12 ) );
		points.add( new Point( 16, 2 ) );
		points.add( new Point( 18, 1 ) );
		points.add( new Point( 21, 5 ) );
		points.add( new Point( 25, 8 ) );*/
		points.add( PointCache.getPoint( -20, 70 ) );
		points.add( PointCache.getPoint( 5, 50 ) );
		points.add( PointCache.getPoint( 60, 1 ) );
		points.add( PointCache.getPoint( 90, -15 ) );
		points.add( PointCache.getPoint( 110, -50 ) );

		GraphWindow graph = new GraphWindow();
		new GraphOptionWindow( points, graph );
	}
}
