package dishcloth.engine.io;

import dishcloth.engine.util.logger.Debug;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * FileIOHelper.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Helper class for handling basic file I/O
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 22.5.2015
 */

public class FileIOHelper {

	public static final String RES_PATH = "/dishcloth/resources/";

	private static int lastImageWidth;
	private static int lastImageHeight;

	public static InputStream createInputStream(String filename) {
		return FileIOHelper.class.getResourceAsStream( RES_PATH + filename );
	}

	public static String readLinesFromFile(String filename) {

		// Remove slashes from the start
		if (filename.startsWith( "/" ) || filename.startsWith( "\\" )) {
			filename = filename.substring( 1 );
		}

		StringBuilder source = new StringBuilder();
		try (BufferedReader reader = new BufferedReader( new InputStreamReader( createInputStream( filename ) ) )) {

			String line;
			while ((line = reader.readLine()) != null) {
				source.append( line ).append( "\n" );
			}

		} catch (Exception e) {
			Debug.logException( e, "FileIOHelper" );
		}

		return source.toString();
	}

	public static int[] readRawPixelDataFromFile(String filename) {
		int[] pixels = null;
		try {
			BufferedImage image = ImageIO.read( FileIOHelper.createInputStream( filename ) );

			int width = image.getWidth();
			int height = image.getHeight();

			pixels = new int[width * height];

			image.getRGB( 0, 0, width, height, pixels, 0, width );

			lastImageWidth = width;
			lastImageHeight = height;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pixels;
	}

	public static int[] readPixelDataFromFile(String filename) {
		int[] pixels = readRawPixelDataFromFile( filename );

		for (int i = 0; i < lastImageWidth * lastImageHeight; i++) {
			// parses RGBA values from pixels array and assigns them to the data array in the order which openGL
			// will read them in.
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);

			// Re-order values
			pixels[i] = a << 24 | b << 16 | g << 8 | r;
		}

		return pixels;
	}

	/**
	 * @return Width of the last image read using read(Raw)PixelDataFromFile
	 */
	public static int getImageWidth() {
		return lastImageWidth;
	}

	/**
	 * @return Height of the last image read using read(Raw)PixelDataFromFile
	 */
	public static int getImageHeight() {
		return lastImageHeight;
	}
}
