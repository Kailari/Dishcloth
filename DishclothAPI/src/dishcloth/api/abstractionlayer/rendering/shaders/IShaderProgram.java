package dishcloth.api.abstractionlayer.rendering.shaders;

import dishcloth.api.exception.ShaderUniformException;
import dishcloth.api.util.math.Matrix4;

/**
 * <b>IShaderProgram</b>
 * <p>
 * ShaderProgram abstraction
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 6.8.2015 at 13:41
 */
public interface IShaderProgram {
	void setUniformFloat(String name, float f) throws ShaderUniformException;

	void setUniformVec2f(String name, float x, float y) throws ShaderUniformException;

	void setUniformVec3f(String name, float x, float y, float z) throws ShaderUniformException;

	void setUniformVec4f(String name, float x, float y, float z, float w) throws ShaderUniformException;

	void setUniformInt(String name, int i) throws ShaderUniformException;

	void setUniformVec2I(String name, int x, int y) throws ShaderUniformException;

	void setUniformVec3I(String name, int x, int y, int z) throws ShaderUniformException;

	void setUniformVec4I(String name, int x, int y, int z, int w) throws ShaderUniformException;

	void setUniformMat4(String name, Matrix4 mat) throws ShaderUniformException;

	int getGLShaderID();
}
