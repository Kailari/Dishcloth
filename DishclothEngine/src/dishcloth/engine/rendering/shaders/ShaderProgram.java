package dishcloth.engine.rendering.shaders;

import dishcloth.api.abstractionlayer.rendering.shaders.IShaderProgram;
import dishcloth.engine.content.AContent;
import dishcloth.engine.content.processors.ShaderProgramProcessor;
import dishcloth.api.exception.ShaderUniformException;
import dishcloth.api.util.math.Matrix4;

import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

/**
 * <b>ShaderProgram</b>
 * <p>
 * Wraps OpenGL ShaderProgram and vertex- and fragment shaders into a one class.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 21.5.2015
 *
 * @see ShaderProgramProcessor
 */

public class ShaderProgram extends AContent implements IShaderProgram {
	private int programID;

	private int vertID;
	private int fragID;

	private HashMap<String, Integer> uniformCache;

	public ShaderProgram(int programID, int vertID, int fragID) {
		this.programID = programID;
		this.vertID = vertID;
		this.fragID = fragID;

		this.uniformCache = new HashMap<>();
	}

	@Override
	public void dispose() {
		uniformCache.clear();

		glUseProgram( 0 );

		glDetachShader( programID, vertID );
		glDetachShader( programID, fragID );

		glDeleteShader( vertID );
		glDeleteShader( fragID );

		glDeleteProgram( programID );
	}

	private int findUniformLocation(String name) throws ShaderUniformException {
		if (uniformCache.containsKey( name )) {
			return uniformCache.get( name );
		}

		int uniformLocation = glGetUniformLocation( programID, name );
		if (uniformLocation == -1) {
			throw new ShaderUniformException( "Invalid uniform variable: \"" + name + "\"!" );
		}

		uniformCache.put( name, uniformLocation );

		return uniformLocation;
	}

	@Override
	public void setUniformFloat(String name, float f) throws ShaderUniformException {
		glUniform1f( findUniformLocation( name ), f );
	}

	@Override
	public void setUniformVec2f(String name, float x, float y) throws ShaderUniformException {
		glUniform2f( findUniformLocation( name ), x, y );
	}

	@Override
	public void setUniformVec3f(String name, float x, float y, float z) throws ShaderUniformException {
		glUniform3f( findUniformLocation( name ), x, y, z );
	}

	@Override
	public void setUniformVec4f(String name, float x, float y, float z, float w) throws ShaderUniformException {
		glUniform4f( findUniformLocation( name ), x, y, z, w );
	}


	@Override
	public void setUniformInt(String name, int i) throws ShaderUniformException {
		glUniform1i( findUniformLocation( name ), i );
	}

	@Override
	public void setUniformVec2I(String name, int x, int y) throws ShaderUniformException {
		glUniform2i( findUniformLocation( name ), x, y );
	}

	@Override
	public void setUniformVec3I(String name, int x, int y, int z) throws ShaderUniformException {
		glUniform3i( findUniformLocation( name ), x, y, z );
	}

	@Override
	public void setUniformVec4I(String name, int x, int y, int z, int w) throws ShaderUniformException {
		glUniform4i( findUniformLocation( name ), x, y, z, w );
	}


	@Override
	public void setUniformMat4(String name, Matrix4 mat) throws ShaderUniformException {
		glUniformMatrix4fv( findUniformLocation( name ), false, mat.toFloatBuffer() );
	}

	@Override
	public int getGLShaderID() {
		return programID;
	}
}
