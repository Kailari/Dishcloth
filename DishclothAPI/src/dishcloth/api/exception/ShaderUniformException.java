package dishcloth.api.exception;

/**
 * <b>ShaderUniformException</b>
 * <p>
 * An exception thrown when encountering error with setting shader uniforms.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 23.5.2015
 *
 * @see ShaderException
 */

public class ShaderUniformException extends ShaderException {
	public ShaderUniformException(String message) {
		super( message );
	}
}
