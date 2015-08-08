package dishcloth.engine.util.debug;

import dishcloth.api.abstractionlayer.debug.IDebugLogger;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Debug.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 21.5.2015
 */

public class Debug {

	public static IDebugLogger getInstance() {
		return DebugLoggerSingleton.getInstance();
	}

	public static void attachStream(PrintStream stream) {
		DebugLoggerSingleton.getInstance().attachStream( stream );
	}

	public static void detachStream(PrintStream stream) {
		DebugLoggerSingleton.getInstance().detachStream( stream );
	}

	public static void log(String message, Object context) {
		DebugLoggerSingleton.getInstance().log( message, context );
	}

	public static void logNote(String message, Object context) {
		DebugLoggerSingleton.getInstance().logNote( message, context );
	}

	public static void logOK(String message, Object context) {
		DebugLoggerSingleton.getInstance().logOK( message, context );
	}

	public static void logWarn(String message, Object context) {
		DebugLoggerSingleton.getInstance().logWarn( message, context );
	}

	public static void logErr(String message, Object context) {
		DebugLoggerSingleton.getInstance().logErr( message, context );
	}

	public static void logException(Exception e, Object context) {
		DebugLoggerSingleton.getInstance().logException( e, context );
	}
}
