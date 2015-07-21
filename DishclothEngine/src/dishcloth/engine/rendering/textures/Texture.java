package dishcloth.engine.rendering.textures;

import dishcloth.engine.io.FileIOHelper;
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

	// TODO: Move this stuff to content pipeline. Texture-class should not have to handle its own disk I/O and hw.
	public Texture(String filename) {

		int[] pixelData = FileIOHelper.readPixelDataFromFile( filename );
		this.width = FileIOHelper.getImageWidth();
		this.height = FileIOHelper.getImageHeight();

		// TODO: round width/height up to nearest power of 2 for texture renderer optimizing.

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

	/**
	 * ASSUMES THAT TEXID IS GENERATED MANUALLY
	 */
	public Texture(int openGLTexID, int width, int height) {
		this.textureID = openGLTexID;
		this.width = width;
		this.height = height;

		textureCache.put( textureID, this );
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
