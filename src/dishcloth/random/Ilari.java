package dishcloth.random;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Lassi on 21.5.2015.
 */
public class Ilari extends JFrame {
	// pyysit että teen tämmösen joten ole hyvä :D
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	public Ilari(int x, int y) {
		setAlwaysOnTop( true );
		setResizable( false );
		setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		setBounds( x * 250, y * 200, 250, 200 );
		contentPane = new JPanel();
		contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		setContentPane( contentPane );
		contentPane.setLayout( null );

		JLabel lblUrFag = new JLabel( "ur fag" );
		lblUrFag.setVerticalAlignment( SwingConstants.CENTER );
		lblUrFag.setHorizontalAlignment( SwingConstants.CENTER );
		lblUrFag.setFont( new Font( "Tahoma", Font.PLAIN, 40 ) );
		lblUrFag.setBounds( 10, 11, 224, 150 );
		contentPane.add( lblUrFag );

		setVisible( true );

		changeColours();
	}

	private void changeColours() {
		Thread thread = new Thread( new Runnable() {

			public void run() {
				for (int i = 0; i < 200; i++) {

					if (i % 2 == 0) {
						contentPane.setBackground( Color.MAGENTA );
					} else {
						contentPane.setBackground( Color.RED );
					}
					try {
						Thread.sleep( 50 );
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				dispose();
			}
		}, "Main" );
		thread.start();
	}
}
