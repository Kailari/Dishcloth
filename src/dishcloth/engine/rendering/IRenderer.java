package dishcloth.engine.rendering;

import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vbo.VertexBufferObject;

/**
 * IRenderer.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Renderer interface. Contains methods for basic rendering.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 12.5.2015
 */


public interface IRenderer {

	void bindShader(ShaderProgram program);

	void bindShader(int programID);

	int getBoundShaderID();

	void bindTexture(Texture texture);

	void bindTexture(int textureID);

	int getBoundTextureID();

	void renderVBOTextured(Texture texture, VertexBufferObject vbo);

	void renderVBO(VertexBufferObject vbo);
}
