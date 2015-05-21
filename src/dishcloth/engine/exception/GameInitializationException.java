package dishcloth.engine.exception;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * GameInitializationException.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 21.5.2015
 */

public class GameInitializationException extends Exception {

	public GameInitializationException() {
	}

	public GameInitializationException(String message) {
		super( message );
	}

	public GameInitializationException(String message, Throwable cause) {
		super( message, cause );
	}

	public GameInitializationException(Throwable cause) {
		super( cause );
	}

	public GameInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super( message, cause, enableSuppression, writableStackTrace );
	}
}
