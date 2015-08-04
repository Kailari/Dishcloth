package dishcloth.engine.rendering.shaders;

import dishcloth.engine.content.AContent;
import dishcloth.engine.exception.ShaderCompilationFailedException;
import dishcloth.engine.exception.ShaderException;
import dishcloth.engine.exception.ShaderLinkFailedException;
import dishcloth.engine.exception.ShaderUniformException;
import dishcloth.engine.io.FileIOHelper;
import dishcloth.engine.util.logger.ANSIColor;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.util.math.Matrix4;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * ShaderProgram.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 21.5.2015
 */

public class ShaderProgram extends AContent {
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


	public void setUniformFloat(String name, float f) throws ShaderUniformException {
		glUniform1f( findUniformLocation( name ), f );
	}

	public void setUniformVec2f(String name, float x, float y) throws ShaderUniformException {
		glUniform2f( findUniformLocation( name ), x, y );
	}
	
	public void setUniformVec3f(String name, float x, float y, float z) throws ShaderUniformException {
		glUniform3f( findUniformLocation( name ), x, y, z );
	}
	
	public void setUniformVec4f(String name, float x, float y, float z, float w) throws ShaderUniformException {
		glUniform4f( findUniformLocation( name ), x, y, z, w );
	}
	
	
	public void setUniformInt(String name, int i) throws ShaderUniformException {
		glUniform1i( findUniformLocation( name ), i );
	}
	
	public void setUniformVec2I(String name, int x, int y) throws ShaderUniformException {
		glUniform2i( findUniformLocation( name ), x, y );
	}
	
	public void setUniformVec3I(String name, int x, int y, int z) throws ShaderUniformException {
		glUniform3i( findUniformLocation( name ), x, y, z );
	}
	
	public void setUniformVec4I(String name, int x, int y, int z, int w) throws ShaderUniformException {
		glUniform4i( findUniformLocation( name ), x, y, z, w );
	}


	public void setUniformMat4(String name, Matrix4 mat) throws ShaderUniformException {
		glUniformMatrix4fv( findUniformLocation( name ), false, mat.toFloatBuffer() );
	}

	public int getGLShaderID() {
		return programID;
	}
}
