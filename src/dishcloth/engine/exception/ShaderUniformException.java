package dishcloth.engine.exception;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * ShaderUniformException.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 23.5.2015
 */

public class ShaderUniformException extends ShaderException {
	public ShaderUniformException() {
	}

	public ShaderUniformException(String message) {
		super( message );
	}

	public ShaderUniformException(String message, Throwable cause) {
		super( message, cause );
	}

	public ShaderUniformException(Throwable cause) {
		super( cause );
	}

	public ShaderUniformException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super( message, cause, enableSuppression, writableStackTrace );
	}
}
