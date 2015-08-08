package dishcloth.api.util.logger;

import dishcloth.api.abstractionlayer.debug.IDebugLogger;

/**
 * <b>APIDebug</b>
 * <p>
 * Hides debug logger implementation via abstraction.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 6.8.2015 at 14:08
 */

public class APIDebug {
	private static IDebugLogger logger;

	public static void initialize(IDebugLogger newLogger) {
		logger = newLogger;
	}

	public static void log(String message, Object context) {
		logger.log( message, context );
	}

	public static void logNote(String message, Object context) {
		logger.logNote( message, context );
	}

	public static void logOK(String message, Object context) {
		logger.logOK( message, context );
	}

	public static void logWarn(String message, Object context) {
		logger.logWarn( message, context );
	}

	public static void logErr(String message, Object context) {
		logger.logErr( message, context );
	}

	public static void logException(Exception e, Object context) {
		logger.logException( e, context );
	}
}
