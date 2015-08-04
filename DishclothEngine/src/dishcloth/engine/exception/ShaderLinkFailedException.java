package dishcloth.engine.exception;

/**
 * <b>ShaderLinkFailedException</b>
 * <p>
 * An exception thrown when shader program linking fails.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 22.5.2015
 *
 * @see dishcloth.engine.rendering.shaders.ShaderProgram
 * @see ShaderException
 */

public class ShaderLinkFailedException extends ShaderException {
	public ShaderLinkFailedException(String message) {
		super( message );
	}
}
