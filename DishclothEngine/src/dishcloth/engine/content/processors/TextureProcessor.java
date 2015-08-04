package dishcloth.engine.content.processors;

import dishcloth.engine.content.AContentProcessor;
import dishcloth.engine.content.ContentManager;
import dishcloth.engine.content.importers.TextureImporter;
import dishcloth.engine.rendering.textures.Texture;
import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

/**
 * TextureProcessor.java
 * <p>
 * Processes TextureImportInfo into a Texture.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 4.8.2015
 */

public class TextureProcessor extends AContentProcessor<TextureImporter.TextureImportInfo, Texture> {
	public static int[] changePixelByteOrder(int[] rawPixels, int imageWidth, int imageHeight) {

		for (int i = 0; i < imageWidth * imageHeight; i++) {
			// parses RGBA values from pixels array and assigns them to the data array in the order which openGL
			// will read them in.
			int a = (rawPixels[i] & 0xff000000) >> 24;
			int r = (rawPixels[i] & 0xff0000) >> 16;
			int g = (rawPixels[i] & 0xff00) >> 8;
			int b = (rawPixels[i] & 0xff);

			// Re-order values
			rawPixels[i] = a << 24 | b << 16 | g << 8 | r;
		}

		return rawPixels;
	}

	@Override
	public Texture process(TextureImporter.TextureImportInfo read, ContentManager contentManager) {
		int[] pixelData = changePixelByteOrder( read.getPixels(), read.getImgWidth(), read.getImgHeight() );

		// TODO: round width/height up to nearest power of 2 for texture renderer optimizing.

		int textureID = glGenTextures();

		// Bind the newly created texture for parameter adjusting
		glBindTexture( GL_TEXTURE_2D, textureID );

		// Nearest neighbor filtering.
		glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST );
		glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST );

		IntBuffer buff = BufferUtils.createIntBuffer( pixelData.length );
		buff.put( pixelData ).flip();

		// Assign image pixel data to the GL texture
		glTexImage2D( GL_TEXTURE_2D, 0, GL_RGBA, read.getImgWidth(), read.getImgHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buff );

		// Unbind
		glBindTexture( GL_TEXTURE_2D, 0 );

		return new Texture( textureID, read.getImgWidth(), read.getImgHeight() );
	}
}
