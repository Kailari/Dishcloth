package dishcloth.utility.drawgraph;

import dishcloth.api.util.geom.Point;
import dishcloth.api.util.memory.PointCache;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Lassi on 21.7.2015.
 */
public class GraphWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 750, HEIGHT = 500;
	private static final int AXIS_START_X = 50;
	private static final int AXIS_START_Y = HEIGHT - 75;
	private GraphBundle graphs;

	public GraphWindow() {
		setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
		setBounds( 100, 100, WIDTH, HEIGHT );
		setLocationRelativeTo( null );
		JPanel contentPane = new JPanel();
		contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		setContentPane( contentPane );
		contentPane.setOpaque( true );
		contentPane.setLayout( null );
		setVisible( true );

		repaint();
	}

	public void setCurves(GraphBundle graphs) {
		this.graphs = graphs;
		repaint();
	}

	public void paint(Graphics g) {
		g.clearRect( 0, 0, WIDTH, HEIGHT );

		g.setColor( Color.BLACK );
		paintGrid( g );
		if (graphs == null) return;
		Point min = graphs.getMin();
		Point max = graphs.getMax();

		final float xs = 0.90F * WIDTH / (max.getX() - min.getX());
		final float ys = 0.75F * HEIGHT / (max.getY() - min.getY());

		graphs.draw( g, AXIS_START_X, AXIS_START_Y, xs, ys );

		PointCache.cachePoint( min );
		PointCache.cachePoint( max );

		g.dispose();
	}

	private void paintGrid(Graphics g) {
		g.drawLine( AXIS_START_X, AXIS_START_Y, AXIS_START_X, 0 );
		g.drawLine( AXIS_START_X, AXIS_START_Y, WIDTH, AXIS_START_Y );

		if (graphs == null) return;
		Point min = graphs.getMin();
		g.drawString( "" + min.getX(), AXIS_START_X, AXIS_START_Y + 15 );
		g.drawString( "" + min.getY(), AXIS_START_X - 40, AXIS_START_Y );

		Point max = graphs.getMax();
		g.drawString( "" + max.getX(), WIDTH - 50, AXIS_START_Y + 15 );
		g.drawString( "" + max.getY(), AXIS_START_X - 40, 50 );
	}
}
