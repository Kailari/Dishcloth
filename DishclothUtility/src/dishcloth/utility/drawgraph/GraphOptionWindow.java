package dishcloth.utility.drawgraph;

import dishcloth.engine.util.curve.CosineCurve;
import dishcloth.engine.util.curve.CubicCurve;
import dishcloth.engine.util.curve.LinearCurve;
import dishcloth.engine.util.curve.PointCurve;
import dishcloth.api.util.geom.Point;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Lassi on 21.7.2015.
 */
public class GraphOptionWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField txtPoints;
	private JCheckBox chckbxPoints, chckbxLinear, chckbxCosine, chckbxCubic;
	private Color[] colours;
	private GraphWindow graphWindow;
	private List<Point> points;
	private JSlider slider;

	public GraphOptionWindow(List<Point> points, GraphWindow graphWindow) {
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
		setBounds( 75, 500, 500, 250 );
		JPanel contentPane = new JPanel();
		contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		setContentPane( contentPane );
		contentPane.setLayout( null );

		JLabel lblPoints = new JLabel( "Points:" );
		lblPoints.setBounds( 10, 11, 46, 14 );
		contentPane.add( lblPoints );

		txtPoints = new JTextField();
		txtPoints.setText( "10" );
		txtPoints.setBounds( 10, 36, 90, 20 );
		contentPane.add( txtPoints );
		txtPoints.setColumns( 10 );

		JButton btnRandomize = new JButton( "Randomize" );
		btnRandomize.addActionListener( e -> {
			int p = new Random().nextInt( slider.getMaximum() ) + 1;
			txtPoints.setText( "" + p );
			slider.setValue( p );
		} );
		btnRandomize.setBounds( 10, 67, 90, 23 );
		contentPane.add( btnRandomize );

		JLabel lblDraw = new JLabel( "Draw:" );
		lblDraw.setBounds( 150, 11, 46, 14 );
		contentPane.add( lblDraw );

		chckbxPoints = new JCheckBox( "Points" );
		chckbxPoints.setBounds( 200, 7, 70, 23 );
		contentPane.add( chckbxPoints );

		chckbxLinear = new JCheckBox( "Linear" );
		chckbxLinear.setSelected( true );
		chckbxLinear.setBounds( 200, 35, 70, 23 );
		contentPane.add( chckbxLinear );

		chckbxCosine = new JCheckBox( "Cosine" );
		chckbxCosine.setSelected( true );
		chckbxCosine.setBounds( 200, 63, 70, 23 );
		contentPane.add( chckbxCosine );

		chckbxCubic = new JCheckBox( "Cubic" );
		chckbxCubic.setSelected( true );
		chckbxCubic.setBounds( 200, 91, 70, 23 );
		contentPane.add( chckbxCubic );

		slider = new JSlider( 1, 500, 20 );
		slider.addChangeListener( e -> txtPoints.setText( "" + slider.getValue() ) );
		slider.setBounds( 10, 106, 90, 23 );
		contentPane.add( slider );

		JButton btnCol = new JButton( "Col" );
		btnCol.addActionListener( e -> chooseColour( 0 ) );
		btnCol.setBounds( 275, 7, 55, 23 );
		contentPane.add( btnCol );

		JButton btnCol_1 = new JButton( "Col" );
		btnCol_1.addActionListener( e -> chooseColour( 1 ) );
		btnCol_1.setBounds( 275, 35, 55, 23 );
		contentPane.add( btnCol_1 );

		JButton btnCol_2 = new JButton( "Col" );
		btnCol_2.addActionListener( e -> chooseColour( 2 ) );
		btnCol_2.setBounds( 275, 63, 55, 23 );
		contentPane.add( btnCol_2 );

		JButton btnCol_3 = new JButton( "Col" );
		btnCol_3.addActionListener( e -> chooseColour( 3 ) );
		btnCol_3.setBounds( 275, 91, 55, 23 );
		contentPane.add( btnCol_3 );

		JButton btnOk = new JButton( "Ok" );
		btnOk.addActionListener( e -> createGraph() );
		btnOk.setBounds( 419, 178, 55, 23 );
		contentPane.add( btnOk );

		setVisible( true );
		colours = new Color[]{Color.BLACK, Color.BLACK, Color.CYAN, Color.RED};
		this.points = points;
		this.graphWindow = graphWindow;
	}

	private void chooseColour(int index) {
		Color col = JColorChooser.showDialog( this, "Choose color", Color.BLACK );
		colours[index] = col;
	}

	private void createGraph() {
		List<Graph> graphs = new ArrayList<>();
		int p = 1;
		try {
			p = Integer.parseInt( txtPoints.getText() );
		} catch (NumberFormatException e) {
			System.out.println( "TxtPoints doesn't contain a number!!!" );
		}

		if (chckbxPoints.isSelected()) {
			Graph graph = new PointGraph( new PointCurve( points ), colours[0], p );
			graphs.add( graph );
		}
		if (chckbxLinear.isSelected()) {
			Graph graph = new Graph( new LinearCurve( points ), colours[1], p );
			graphs.add( graph );
		}
		if (chckbxCosine.isSelected()) {
			Graph graph = new Graph( new CosineCurve( points ), colours[2], p );
			graphs.add( graph );
		}
		if (chckbxCubic.isSelected()) {
			Graph graph = new Graph( new CubicCurve( points ), colours[3], p );
			graphs.add( graph );
		}

		GraphBundle gc = new GraphBundle( graphs );
		graphWindow.setCurves( gc );
	}
}