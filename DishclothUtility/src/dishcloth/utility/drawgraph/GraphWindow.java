package dishcloth.utility.drawgraph;

import dishcloth.engine.util.geom.Point;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Lassi on 21.7.2015.
 */
public class GraphWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 750, HEIGHT = 500;
	private static final Point AXIS_START = new Point( 50, HEIGHT - 75 );
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

		final double xs = 0.90 * WIDTH / (max.x - min.x);
		final double ys = 0.75 * HEIGHT / (max.y - min.y);
		graphs.draw( g, (int) AXIS_START.x, (int) AXIS_START.y, xs, ys );

		g.dispose();
	}

	private void paintGrid(Graphics g) {
		g.drawLine( (int) AXIS_START.x, (int) AXIS_START.y, (int) (AXIS_START.x), 0 );
		g.drawLine( (int) AXIS_START.x, (int) AXIS_START.y, WIDTH, (int) (AXIS_START.y) );

		if (graphs == null) return;
		Point min = graphs.getMin();
		g.drawString( "" + min.x, (int) AXIS_START.x, (int) AXIS_START.y + 15 );
		g.drawString( "" + min.y, (int) AXIS_START.x - 40, (int) AXIS_START.y );

		Point max = graphs.getMax();
		g.drawString( "" + max.x, WIDTH - 50, (int) AXIS_START.y + 15 );
		g.drawString( "" + max.y, (int) AXIS_START.x - 40, 50 );
	}
}
