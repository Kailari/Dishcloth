package dishcloth.engine.rendering.textures;

import dishcloth.engine.content.AContent;
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

public class Texture extends AContent {

	private int width, height;
	private int textureID;

	/**
	 * ASSUMES THAT TEXID IS GENERATED MANUALLY
	 */
	public Texture(int openGLTexID, int width, int height) {
		this.textureID = openGLTexID;
		this.width = width;
		this.height = height;
	}

	public void bind() {
		glBindTexture( GL_TEXTURE_2D, this.textureID );
	}

	public void unbind() {
		glBindTexture( GL_TEXTURE_2D, 0 );
	}

	@Override
	public void dispose() {
		unbind();

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
