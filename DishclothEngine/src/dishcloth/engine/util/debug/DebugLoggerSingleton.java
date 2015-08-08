package dishcloth.engine.util.debug;

import dishcloth.api.abstractionlayer.debug.IDebugLogger;
import dishcloth.api.util.ANSIColor;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <b>DebugLoggerSingleton</b>
 * <p>
 * Internal logger logger singleton
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 6.8.2015 at 14:12
 */

class DebugLoggerSingleton implements IDebugLogger {

	private static DebugLoggerSingleton instance;
	private List<DebugMessage> messageList = new ArrayList<>();
	private List<PrintStream> outputStreams = new ArrayList<>();

	private DebugLoggerSingleton() {
		attachStream( System.out );
	}

	protected static DebugLoggerSingleton getInstance() {
		if (instance == null) {
			instance = new DebugLoggerSingleton();
		}

		return instance;
	}

	protected void attachStream(PrintStream stream) {
		outputStreams.add( stream );
	}
	
	protected void detachStream(PrintStream stream) {
		outputStreams.remove( stream );
	}
	
	private void log(String message, String context, DebugLevel debugLvl) {
		Date logTime = new Date();
		String messageString = DebugMessageFormatter.formatMessageString( message, context, debugLvl, logTime );
		
		DebugMessage dbgMsg = new DebugMessage( debugLvl, context, logTime, message, messageString );
		
		messageList.add( dbgMsg );
		
		for (PrintStream s : outputStreams) {
			s.println( dbgMsg.compiledMessage );
		}
	}

	private void log(String message, Object context, DebugLevel debugLvl) {
		if (context instanceof String) {
			log( message, (String) context, debugLvl );
		} else {
			log( message, context.getClass().getSimpleName(), debugLvl );
		}
	}

	@Override
	public void log(String message, Object context) {
		log( message, context, DebugLevel.LOG );
	}

	@Override
	public void logNote(String message, Object context) {
		log( message, context, DebugLevel.NOTE );
	}

	@Override
	public void logOK(String message, Object context) {
		log( message, context, DebugLevel.SUCCESS );
	}

	@Override
	public void logWarn(String message, Object context) {
		log( message, context, DebugLevel.WARNING );
	}

	@Override
	public void logErr(String message, Object context) {
		log( message, context, DebugLevel.ERROR );
	}

	@Override
	public void logException(Exception e, Object context) {

		String stacktrace = "";
		boolean first = true;
		for (StackTraceElement ste : e.getStackTrace()) {
			stacktrace += "\n" + (first ? "" : "    at ") + ste.toString();
			first = false;
		}

		log( ANSIColor.RED + "\n" + e.getClass().getSimpleName() + " was thrown!"
				     + "\n    Message:    " + e.getMessage()
				     + "\n    Cause:      " + (e.getCause() == null ? "Not specified" : e.getCause())
				     + "\n" + ANSIColor.RED_BACKGROUND + ANSIColor.BLACK
				     + "+----------------------------------------------------------------------------+"
				     + ANSIColor.RESET + "\n" + ANSIColor.BLACK + ANSIColor.RED_BACKGROUND + "|" + ANSIColor.RESET
				     + ANSIColor.RED + "   Stacktrace:                                                              "
				     + ANSIColor.RED_BACKGROUND + ANSIColor.BLACK + "|" + ANSIColor.RESET + "\n"
				     + ANSIColor.RED_BACKGROUND + ANSIColor.BLACK
				     + "+----------------------------------------------------------------------------+"
				     + ANSIColor.RESET + ANSIColor.RED + stacktrace + ANSIColor.RESET,
		     context, DebugLevel.EXCEPTION );
	}

	
	private class DebugMessage {
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
