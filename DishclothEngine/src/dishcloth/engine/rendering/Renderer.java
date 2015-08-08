package dishcloth.engine.rendering;

import dishcloth.api.abstractionlayer.rendering.IRenderer;
import dishcloth.api.abstractionlayer.rendering.shaders.IShaderProgram;
import dishcloth.api.abstractionlayer.rendering.textures.ITexture;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class Renderer implements IRenderer {

	private int boundTextureID;
	private int boundShaderID;

	public Renderer() {
		boundTextureID = 0;
		boundShaderID = 0;
	}

	@Override
	public void bindShader(IShaderProgram program) {
		bindShader( program.getGLShaderID() );
	}

	@Override
	public void bindShader(int programID) {
		if (boundShaderID != programID) {
			glUseProgram( programID );
			boundShaderID = programID;
		}
	}

	@Override
	public int getBoundShaderID() {
		return boundShaderID;
	}

	@Override
	public void bindTexture(ITexture texture) {
		bindTexture( texture.getGLTexID() );
	}

	@Override
	public void bindTexture(int textureID) {
		if (textureID != boundTextureID) {
			glBindTexture( GL_TEXTURE_2D, textureID );
			boundTextureID = textureID;
		}
	}

	@Override
	public int getBoundTextureID() {
		return boundTextureID;
	}
}
