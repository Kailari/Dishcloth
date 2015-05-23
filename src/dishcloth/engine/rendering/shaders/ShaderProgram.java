package dishcloth.engine.rendering.shaders;

import dishcloth.engine.exception.ShaderCompilationFailedException;
import dishcloth.engine.exception.ShaderException;
import dishcloth.engine.exception.ShaderLinkFailedException;
import dishcloth.engine.io.IOHelper;
import dishcloth.engine.util.logger.Debug;

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

public class ShaderProgram {
	int programID;

	int vertID;
	int fragID;

	public ShaderProgram(String vShader, String fShader) {
		programID = glCreateProgram();

		try {
			attachVertexShader( vShader );
			attachFragmentShader( fShader );

			linkProgram();
		}
		catch (ShaderException e) {
			Debug.logException( e, this );
		}
	}

	private void attachVertexShader(String name) throws ShaderCompilationFailedException {
		String source = IOHelper.readLinesFromFile( "shaders/" + name + ".vert" );

		// Create vertex shader
		vertID = glCreateShader( GL_VERTEX_SHADER );
		glShaderSource( vertID, source );

		// Compile
		glCompileShader( vertID );

		// Find possible errors
		if (glGetShaderi( vertID, GL_COMPILE_STATUS ) == GL_FALSE) {
			throw new ShaderCompilationFailedException( "Vertex Shader compilation failed: "
			                                            + glGetShaderInfoLog(vertID, glGetShaderi(vertID, GL_INFO_LOG_LENGTH)));
		}

		// Attach
		glAttachShader( programID, vertID );
	}

	private void attachFragmentShader(String name) throws ShaderCompilationFailedException {
		String source = IOHelper.readLinesFromFile( "shaders/" + name + ".frag" );

		// Create vertex shader
		fragID = glCreateShader( GL_FRAGMENT_SHADER );
		glShaderSource( fragID, source );

		// Compile
		glCompileShader( fragID );

		// Find possible errors
		if (glGetShaderi( fragID, GL_COMPILE_STATUS ) == GL_FALSE) {
			throw new ShaderCompilationFailedException( "Fragment Shader compilation failed: \n"
					                                            + glGetShaderInfoLog(vertID, glGetShaderi(vertID, GL_INFO_LOG_LENGTH)));
		}

		// Attach
		glAttachShader( programID, fragID );
	}

	private void linkProgram() throws ShaderLinkFailedException {
		// Link
		glLinkProgram( programID );

		// Check for problems
		if (glGetProgrami( programID, GL_LINK_STATUS ) == GL_FALSE) {
			throw new ShaderLinkFailedException( "Shader program linking failed." );
		}
	}

	public void bind() {
		glUseProgram( programID );
	}

	public void unbind() {
		bindNull();
	}

	public static void bindNull() {
		glUseProgram( 0 );
	}

	public void dispose() {
		unbind();

		glDetachShader( programID, vertID );
		glDetachShader( programID, fragID );

		glDeleteShader( vertID );
		glDeleteShader( fragID );

		glDeleteProgram( programID );
	}
}
