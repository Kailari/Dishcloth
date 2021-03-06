package dishcloth.engine.rendering.textures;

import dishcloth.engine.content.processors.TextureProcessor;
import dishcloth.engine.io.FileIOHelper;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

/**
 * TextureAtlasBuilder.java
 * <p>
 * Creates texture atlases from multiple textures
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 10.6.2015
 */

public class TextureAtlasBuilder {

	private List<String> filenames;
	private int frameID_counter;
	private int singleTextureSize;

	public TextureAtlasBuilder(int textureSize) {
		this.filenames = new ArrayList<>();
		this.frameID_counter = 0;

		this.singleTextureSize = textureSize;
	}

	/**
	 * @param filename file where to look for the texture
	 * @return FrameID inside the atlas
	 */
	public int addTexture(String filename) {
		this.filenames.add( filename );
		return frameID_counter++;
	}

	public Texture build() {
		int atlasSize = Math.round( (float) Math.ceil( Math.sqrt( filenames.size() ) ) );

		int atlasSizeInPixels = atlasSize * this.singleTextureSize;
		int[] atlasPixels = new int[atlasSizeInPixels * atlasSizeInPixels];

		int[] texturePixels;
		int width;
		int height;
		for (int atlasX = 0; atlasX < atlasSize; atlasX++) {
			for (int atlasY = 0; atlasY < atlasSize; atlasY++) {
				int index = atlasX + atlasY * atlasSize;
				if (index >= filenames.size()) {
					break;
				}

				try {
					// Load image
					BufferedImage image = ImageIO.read( FileIOHelper.createInputStream( filenames.get( index ) ) );
					width = image.getWidth();
					height = image.getHeight();

					texturePixels = new int[width * height];

					// Get ARGB pixel values to int[]
					image.getRGB( 0, 0, width, height, texturePixels, 0, width );
					texturePixels = TextureProcessor.changePixelByteOrder( texturePixels, width, height );

					// Calculate texture width/height
					int texWidth = Math.min( this.singleTextureSize, width );
					int texHeight = Math.min( this.singleTextureSize, height );

					// Puke pixel values to atlas
					for (int textureX = 0; textureX < texWidth; textureX++) {
						for (int textureY = 0; textureY < texHeight; textureY++) {
							int targetX = atlasX * this.singleTextureSize + textureX;
							int targetY = ((atlasY * this.singleTextureSize) + textureY) * atlasSizeInPixels;

							atlasPixels[targetX + targetY] = texturePixels[textureX + textureY * texWidth];
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		int textureID = glGenTextures();

		// Bind the newly created texture for parameter adjusting
		glBindTexture( GL_TEXTURE_2D, textureID );

		// Nearest neighbor filtering.
		glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST );
		glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST );

		IntBuffer buff = BufferUtils.createIntBuffer( atlasPixels.length );
		buff.put( atlasPixels ).flip();

		// Assign image pixel data to the GL texture
		glTexImage2D( GL_TEXTURE_2D,
		              0,
		              GL_RGBA,
		              atlasSizeInPixels,
		              atlasSizeInPixels,
		              0,
		              GL_RGBA,
		              GL_UNSIGNED_BYTE,
		              buff );

		// Unbind
		glBindTexture( GL_TEXTURE_2D, 0 );

		return new Texture( textureID, atlasSizeInPixels, atlasSizeInPixels );
	}
}
