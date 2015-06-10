package dishcloth.engine.util.logger;

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

	private static List<DebugMessage> messageList = new ArrayList<>();
	private static List<PrintStream> outputStreams = new ArrayList<>();

	static {
		attachStream( System.out );
	}

	public static void attachStream(PrintStream stream) {
		outputStreams.add( stream );
	}

	public static void detachStream(PrintStream stream) {
		outputStreams.remove( stream );
	}

	private static void log(String message, String context, DebugLevel debugLvl) {
		Date logTime = new Date();
		String messageString = DebugMessageFormatter.formatMessageString( message, context, debugLvl, logTime );

		DebugMessage dbgMsg = new DebugMessage( debugLvl, context, logTime, message, messageString );

		messageList.add( dbgMsg );

		for (PrintStream s : outputStreams) {
			s.println( dbgMsg.compiledMessage );
		}
	}

	public static void log(String message, String context) {
		log( message, context, DebugLevel.LOG );
	}

	public static void logNote(String message, String context) {
		log( message, context, DebugLevel.NOTE );
	}

	public static void logOK(String message, String context) {
		log( message, context, DebugLevel.SUCCESS );
	}

	public static void logWarn(String message, String context) {
		log( message, context, DebugLevel.WARNING );
	}

	public static void logErr(String message, String context) {
		log( message, context, DebugLevel.ERROR );
	}

	private static void log(String message, Object context, DebugLevel debugLvl) {
		log( message, context.getClass().getSimpleName(), debugLvl );
	}

	public static void log(String message, Object context) {
		log( message, context, DebugLevel.LOG );
	}

	public static void logNote(String message, Object context) {
		log( message, context, DebugLevel.NOTE );
	}

	public static void logOK(String message, Object context) {
		log( message, context, DebugLevel.SUCCESS );
	}

	public static void logWarn(String message, Object context) {
		log( message, context, DebugLevel.WARNING );
	}

	public static void logErr(String message, Object context) {
		log( message, context, DebugLevel.ERROR );
	}

	public static void logException(Exception e, String context) {

		String stacktrace = "";
		boolean first = true;
		for (StackTraceElement ste : e.getStackTrace()) {
			stacktrace += "\n" + (first ? "" : "    at ") + ste.toString();
			first = false;
		}

		log( "\n" + e.getClass().getSimpleName() + " was thrown!"
				     + "\n    Message:    " + e.getMessage()
				     + "\n    Cause:      " + (e.getCause() == null ? "Not specified" : e.getCause())
				     + "\n+----------------------------------------------------------------------------+"
				     + "\n|   Stacktrace:                                                              |"
				     + "\n+----------------------------------------------------------------------------+"
				     + stacktrace,
		     context, DebugLevel.EXCEPTION );
	}

	public static void logException(Exception e, Object context) {
		logException( e, context.getClass().getSimpleName() );
	}


	private static class DebugMessage {
		DebugLevel dbgLvl;
		String context;
		Date timestamp;

		String rawMessage;
		String compiledMessage;

		public DebugMessage(DebugLevel dbgLvl, String context, Date timestamp, String rawMessage, String compiledMessage) {
			this.dbgLvl = dbgLvl;
			this.context = context;
			this.timestamp = timestamp;
			this.rawMessage = rawMessage;
			this.compiledMessage = compiledMessage;
		}
	}
}
