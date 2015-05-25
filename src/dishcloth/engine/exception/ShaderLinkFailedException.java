package dishcloth.engine.exception;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * ShaderLinkFailedException.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 22.5.2015
 */

public class ShaderLinkFailedException extends ShaderException {
	public ShaderLinkFailedException() {
	}

	public ShaderLinkFailedException(String message) {
		super( message );
	}

	public ShaderLinkFailedException(String message, Throwable cause) {
		super( message, cause );
	}

	public ShaderLinkFailedException(Throwable cause) {
		super( cause );
	}

	public ShaderLinkFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super( message, cause, enableSuppression, writableStackTrace );
	}
}
