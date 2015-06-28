package randomGen;

import randomGen.generation.generator.DefaultTerrainGenerator;
import randomGen.io.FileIOHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * NoisePanel.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 28.6.2015
 */

public class NoisePanel extends JPanel {
	private Image img;
	private Image newImg;
	private boolean newImgReady;

	public void createNoiseImage() {

		Thread thread = new Thread( () -> {
			DefaultTerrainGenerator generator = new DefaultTerrainGenerator();
			float[] values = generator.generateValues( 0, 0, RandGenTest.HEIGHTMAP_SIZE );
			newImg = FileIOHelper.valuesToImage( values );
			newImgReady = true;
			repaint();
		} );

		// Run image creation in its own thread.
		thread.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent( g );

		if (newImgReady) {
			newImgReady = false;
			img = newImg;
		}

		if (img != null) {
			g.drawImage( img, 0, 0, this );
		}
		else {
			g.drawString( "Press space to generate image.", 32, 32 );
		}

	}

	public static class NoisePanelKeyListener implements KeyListener {

		private NoisePanel panel;

		public NoisePanelKeyListener(NoisePanel panel) {
			this.panel = panel;
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				panel.createNoiseImage();
			}
		}
	}
}
