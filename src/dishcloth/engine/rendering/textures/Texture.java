package dishcloth.engine.rendering.textures;

import dishcloth.engine.io.IOHelper;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Texture.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 21.5.2015
 */

public class Texture {

	private static HashMap<Integer, Texture> textureCache = new HashMap<>();

	public static Texture findByID(int textureID) {
		if (textureCache.containsKey( textureID )) {
			return textureCache.get( textureID );
		}
		return null;
	}

	private int width, height;
	private int textureID;

	public Texture(String filename) {

		int[] pixelData = processPixelData( readRawPixelData( filename ) );

		textureID = glGenTextures();

		// Bind the newly created texture for parameter adjusting
		glBindTexture( GL_TEXTURE_2D, textureID );

		// Nearest neighbor filtering.
		glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST );
		glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST );

		IntBuffer buff = BufferUtils.createIntBuffer( pixelData.length );
		buff.put( pixelData ).flip();

		// Assign image pixel data to the GL texture
		glTexImage2D( GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buff );

		// Unbind
		glBindTexture( GL_TEXTURE_2D, 0 );

		textureCache.put( textureID, this );
	}

	private int[] readRawPixelData(String filename) {

		int[] pixels = null;
		try {
			BufferedImage image = ImageIO.read( IOHelper.createInputStream( filename ) );

			width = image.getWidth();
			height = image.getHeight();

			pixels = new int[width * height];

			image.getRGB( 0, 0, width, height, pixels, 0, width );
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pixels;
	}

	private int[] processPixelData(int[] rawData) {
		int[] data = new int[width * height];
		for (int i = 0; i < width * height; i++) {
			// parses RGBA values from pixels array and assigns them to the data array in the order which openGL
			// will read them in.
			int a = (rawData[i] & 0xff000000) >> 24;
			int r = (rawData[i] & 0xff0000) >> 16;
			int g = (rawData[i] & 0xff00) >> 8;
			int b = (rawData[i] & 0xff);

			data[i] = a << 24 | b << 16 | g << 8 | r;
		}

		return data;
	}

	public void bind() {
		glBindTexture( GL_TEXTURE_2D, textureID );
	}

	public void unbind() {
		glBindTexture( GL_TEXTURE_2D, 0 );
	}

	public void dispose() {
		unbind();

		textureCache.remove( textureID );

		glDeleteTextures( textureID );
	}

	public int getGLTexID() {
		return textureID;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
