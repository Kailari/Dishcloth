package dishcloth.utility.drawgraph;

import dishcloth.api.util.memory.PointCache;
import dishcloth.engine.util.curve.ACurve;
import dishcloth.api.util.geom.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lassi on 21.7.2015.
 */
public class Graph {
	
	protected Color col;
	private ACurve curve;
	private List<Point> points;

	public Graph(ACurve curve, Color col, int pts) {
		this.curve = curve;
		this.col = col;

		points = new ArrayList<>();
		for (int i = 0; i <= pts; i++) {
			float norm = i / (float) pts;
			points.add( PointCache.getPoint( curve.convertToCurvePosition( norm ), curve.valueAtNormalized( norm ) ) );
		}
	}

	public ACurve getCurve() {
		return curve;
	}

	public void draw(Graphics g, int x0, int y0, float xScale, float yScale, Point min) {
		g.setColor( col );
		for (int i = 0; i < points.size() - 1; i++) {

			int x1 = x0 + (int) ((points.get( i ).getX() - min.getX()) * xScale);
			int y1 = y0 - (int) ((points.get( i ).getY() - min.getY()) * yScale);

			int x2 = x0 + (int) ((points.get( i + 1 ).getX() - min.getX()) * xScale);
			int y2 = y0 - (int) ((points.get( i + 1 ).getY() - min.getY()) * yScale);

			g.drawLine( x1, y1, x2, y2 );
		}
	}

}