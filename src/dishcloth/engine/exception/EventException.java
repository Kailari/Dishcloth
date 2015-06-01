package dishcloth.engine.exception;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * EventException.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 1.6.2015
 */

public class EventException extends Exception {
	public EventException() {
	}

	public EventException(String message) {
		super( message );
	}

	public EventException(String message, Throwable cause) {
		super( message, cause );
	}

	public EventException(Throwable cause) {
		super( cause );
	}

	public EventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super( message, cause, enableSuppression, writableStackTrace );
	}
}
