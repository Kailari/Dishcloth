package randomGen.io;

import dishcloth.engine.util.logger.Debug;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * FileIOHelper.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 28.6.2015
 */

public class FileIOHelper {
	/**
	 * Saves a square-shaped heightmap to file
	 *
	 * @param filename Name of file to create
	 * @param values   Height-values
	 */
	public static void SaveHeightmapToFile(String filename, float[] values) {
		Color[] colors = new Color[values.length];

		for (int i = 0; i < values.length; i++) {
			if (values[i] <= 0f) {
				colors[i] = Color.cyan;
			}
			else {
				colors[i] = new Color( 1.0f - values[i], 1.0f - values[i], 1.0f - values[i], 1.0f );
			}
		}

		SaveColormapToFile( filename, colors );
	}

	/**
	 * Saves a square-shaped colortmap to file
	 *
	 * @param filename Name of file to create
	 * @param colors   Height-values
	 */
	public static void SaveColormapToFile(String filename, Color[] colors) {
		int size = (int) Math.sqrt( colors.length );

		// Create BufferedImage
		BufferedImage tmpImage = new BufferedImage( size, size, BufferedImage.TYPE_INT_ARGB );

		// Set pixels' colors
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (x % 256 == 0 || y % 256 == 0) {
					tmpImage.setRGB( x, y, Color.RED.getRGB() );
				}
				else {
					tmpImage.setRGB( x, y, colors[x + y * size].getRGB() );
				}
			}
		}

		// Try write to file
		try {
			File file = new File( "./" + filename + ".png" );
			ImageIO.write( tmpImage, "PNG", file );
		} catch (IOException e) {
			Debug.logException( e, "FileIOHelper" );
		}
	}

	public static Image valuesToImage(float[] values) {
		Color[] colors = new Color[values.length];

		for (int i = 0; i < values.length; i++) {
			if (values[i] <= 0f) {
				colors[i] = Color.cyan;
			}
			else {
				colors[i] = new Color( 1.0f - values[i], 1.0f - values[i], 1.0f - values[i], 1.0f );
			}
		}

		int size = (int) Math.sqrt( colors.length );

		// Create BufferedImage
		BufferedImage tmpImage = new BufferedImage( size, size, BufferedImage.TYPE_INT_ARGB );

		// Set pixels' colors
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (x % 256 == 0 || y % 256 == 0) {
					tmpImage.setRGB( x, y, Color.RED.getRGB() );
				}
				else {
					tmpImage.setRGB( x, y, colors[x + y * size].getRGB() );
				}
			}
		}

		return tmpImage;
	}
}
