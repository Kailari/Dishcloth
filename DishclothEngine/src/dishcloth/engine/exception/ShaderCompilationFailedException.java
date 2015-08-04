package dishcloth.engine.exception;

/**
 * <b>ShaderCompilationFailedException</b>
 * <p>
 * An exception thrown when shader compilation fails.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 22.5.2015
 *
 * @see dishcloth.engine.rendering.shaders.ShaderProgram
 * @see ShaderException
 */

public class ShaderCompilationFailedException extends ShaderException {
	public ShaderCompilationFailedException(String message) {
		super( message );
	}
}
