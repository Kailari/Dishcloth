package dishcloth.api.abstractionlayer.rendering;

import dishcloth.api.abstractionlayer.rendering.shaders.IShaderProgram;
import dishcloth.api.abstractionlayer.rendering.textures.ITexture;

/**
 * <b>IRenderer</b>
 * <p>
 * Renderer interface. Contains methods for basic rendering.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 12.5.2015
 */


public interface IRenderer {

	void bindShader(IShaderProgram program);

	void bindShader(int programID);

	int getBoundShaderID();

	void bindTexture(ITexture texture);

	void bindTexture(int textureID);

	int getBoundTextureID();
}
