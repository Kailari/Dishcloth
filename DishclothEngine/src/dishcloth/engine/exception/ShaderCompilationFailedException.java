package dishcloth.engine.exception;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * ShaderCompilationFailedException.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 22.5.2015
 */

public class ShaderCompilationFailedException extends ShaderException {
	public ShaderCompilationFailedException() {
	}

	public ShaderCompilationFailedException(String message) {
		super( message );
	}

	public ShaderCompilationFailedException(String message, Throwable cause) {
		super( message, cause );
	}

	public ShaderCompilationFailedException(Throwable cause) {
		super( cause );
	}

	public ShaderCompilationFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super( message, cause, enableSuppression, writableStackTrace );
	}
}
