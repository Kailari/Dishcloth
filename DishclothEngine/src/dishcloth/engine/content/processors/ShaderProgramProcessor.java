package dishcloth.engine.content.processors;

import dishcloth.engine.content.AContentProcessor;
import dishcloth.engine.content.ContentManager;
import dishcloth.engine.content.importers.ShaderProgramImporter;
import dishcloth.engine.exception.ShaderCompilationFailedException;
import dishcloth.api.exception.ShaderException;
import dishcloth.engine.exception.ShaderLinkFailedException;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.util.debug.Debug;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.glGetProgrami;

/**
 * <b>ShaderProgramProcessor</b>
 * <p>
 * Processes data received from ShaderProgramImporter and compiles it into a {@link ShaderProgram}.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 4.8.2015
 *
 * @see dishcloth.engine.content.AContentProcessor
 * @see ShaderProgram
 */

public class ShaderProgramProcessor extends AContentProcessor<String[], ShaderProgram> {
	@Override
	public ShaderProgram process(String[] read, ContentManager contentManager) {
		int programID = glCreateProgram();

		try {
			int vertexShaderID = attachVertexShader( read[ShaderProgramImporter.VERTEX_SHADER_INDEX], programID );
			int fragmentShaderID = attachFragmentShader( read[ShaderProgramImporter.FRAGMENT_SHADER_INDEX], programID );

			linkProgram( programID );
			return new ShaderProgram( programID, vertexShaderID, fragmentShaderID );

		} catch (ShaderException e) {
			Debug.logException( e, this );
		}

		return null;
	}

	private int attachVertexShader(String source, int programID) throws ShaderCompilationFailedException {
		// Create vertex shader
		int vertID = glCreateShader( GL_VERTEX_SHADER );
		glShaderSource( vertID, source );

		// Compile
		glCompileShader( vertID );

		// Find possible errors
		if (glGetShaderi( vertID, GL_COMPILE_STATUS ) == GL_FALSE) {
			throw new ShaderCompilationFailedException( "Vertex Shader compilation failed: "
					                                            + glGetShaderInfoLog( vertID, glGetShaderi( vertID, GL_INFO_LOG_LENGTH ) ) );
		}

		// Attach
		glAttachShader( programID, vertID );

		return vertID;
	}

	private int attachFragmentShader(String source, int programID) throws ShaderCompilationFailedException {
		// Create vertex shader
		int fragID = glCreateShader( GL_FRAGMENT_SHADER );
		glShaderSource( fragID, source );

		// Compile
		glCompileShader( fragID );

		// Find possible errors
		if (glGetShaderi( fragID, GL_COMPILE_STATUS ) == GL_FALSE) {
			throw new ShaderCompilationFailedException( "Fragment Shader compilation failed: \n"
					                                            + glGetShaderInfoLog( fragID, glGetShaderi( fragID, GL_INFO_LOG_LENGTH ) ) );
		}

		// Attach
		glAttachShader( programID, fragID );

		return fragID;
	}

	private void linkProgram(int programID) throws ShaderLinkFailedException {
		Debug.log( "Linking shaders!", this );
		// Link
		glLinkProgram( programID );

		// Check for problems
		if (glGetProgrami( programID, GL_LINK_STATUS ) == GL_FALSE) {
			throw new ShaderLinkFailedException( "Shader program linking failed." );
		}
	}
}
