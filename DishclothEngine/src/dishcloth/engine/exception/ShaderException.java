package dishcloth.engine.exception;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * ShaderException.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 22.5.2015
 */

public abstract class ShaderException extends Exception {
	public ShaderException() {
	}

	public ShaderException(String message) {
		super( message );
	}

	public ShaderException(String message, Throwable cause) {
		super( message, cause );
	}

	public ShaderException(Throwable cause) {
		super( cause );
	}

	public ShaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super( message, cause, enableSuppression, writableStackTrace );
	}
}
