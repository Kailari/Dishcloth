package dishcloth.api.exception;

import dishcloth.api.exception.ShaderUniformException;

/**
 * <b>ShaderException</b>
 * <p>
 * A general exception thrown when encountering problems with shaders.
 * Superclass for other shader-related exception.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 22.5.2015
 *
 * @see dishcloth.engine.rendering.shaders.ShaderProgram
 * @see ShaderUniformException
 * @see ShaderCompilationFailedException
 * @see ShaderLinkFailedException
 */

public class ShaderException extends Exception {
	public ShaderException(String message) {
		super( message );
	}
}
