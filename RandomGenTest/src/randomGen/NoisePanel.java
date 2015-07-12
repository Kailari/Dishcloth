package randomGen;

import dishcloth.engine.world.level.TerrainChunk;
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
			DefaultTerrainGenerator generator = new DefaultTerrainGenerator(1L);

			int chunkSize = RandGenTest.HEIGHTMAP_SIZE * TerrainChunk.CHUNK_SIZE;
			float[] values = new float[chunkSize * chunkSize];
			for (int chunkX = 0; chunkX < RandGenTest.HEIGHTMAP_SIZE; chunkX++) {
				for (int chunkY = 0; chunkY < RandGenTest.HEIGHTMAP_SIZE; chunkY++) {
					System.out.println("Generating chunk: " + chunkX + "," + chunkY);
					long startTime = System.currentTimeMillis();

					float[] generated = generator.generateValues( chunkX - RandGenTest.HEIGHTMAP_SIZE / 2, chunkY);

					for (int x = 0; x < TerrainChunk.CHUNK_SIZE; x++) {
						for (int y = 0; y < TerrainChunk.CHUNK_SIZE; y++) {
							int iy = (y + (chunkY * TerrainChunk.CHUNK_SIZE)) * chunkSize;
							int ix = x + (chunkX * TerrainChunk.CHUNK_SIZE);
							values[ix + iy] = generated[x + y * TerrainChunk.CHUNK_SIZE];
						}
					}

					System.out.println("Chunk generated in "
							                   + (double)(System.currentTimeMillis() - startTime) + " ms");
				}
			}

			newImg = FileIOHelper.valuesToImage( values );

			FileIOHelper.SaveHeightmapToFile( "heightmap", values );

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
			g.drawImage( img, 0, 0, RandGenTest.NOISE_PANEL_SIZE, RandGenTest.NOISE_PANEL_SIZE, this );
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
