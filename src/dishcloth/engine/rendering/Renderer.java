package dishcloth.engine.rendering;

import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vbo.VertexBufferObject;

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
	public void bindShader(ShaderProgram program) {
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
	public void bindTexture(Texture texture) {
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

	@Override
	public void renderVBOTextured(Texture texture, VertexBufferObject vbo) {
		// Bind only if necessary
		bindTexture( texture );

		vbo.render();
	}

	@Override
	public void renderVBO(VertexBufferObject vbo) {
		vbo.render();
	}
}
