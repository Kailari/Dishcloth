package dishcloth.utility.drawgraph;

import dishcloth.engine.util.curve.PointCurve;
import dishcloth.api.util.geom.Point;

import java.awt.*;
import java.util.List;


/**
 * Created by Lassi on 22.7.2015.
 */
public class PointGraph extends Graph {

	private static final int POINT_SIZE = 5;
	private List<Point> list;
	private PointCurve pc;

	public PointGraph(PointCurve curve, Color col, int pts) {
		super( curve, col, pts );

		this.pc = curve;
		list = pc.getPoints();
	}

	@Override
	public void draw(Graphics g, int x0, int y0, float xScale, float yScale, Point min) {
		g.setColor( col );
		for (int i = 0; i < list.size(); i++) {
			int xc = x0 + (int) ((list.get( i ).x - min.x) * xScale);
			int yc = y0 - (int) ((list.get( i ).y - min.y) * yScale);

			g.drawOval( xc, yc, POINT_SIZE, POINT_SIZE );
		}
	}
}
