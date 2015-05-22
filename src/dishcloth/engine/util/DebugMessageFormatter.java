package dishcloth.engine.util;

import java.util.Date;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * DebugMessageFormatter.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 22.5.2015
 */

public class DebugMessageFormatter {

	private static final int CONTEXT_MAX_LENGTH = 12;

	private static final int MISC_CHARS_IN_PREFIX = 3;
	private static final int WHITESPACE_AFTER_PREFIX = 1;

	private static final int PREFIX_MAX_LENGTH = CONTEXT_MAX_LENGTH + MISC_CHARS_IN_PREFIX;

	public static String formatMessageString(String message, String context, DebugLevel debugLevel, Date date) {
		String prefix = String.format( "[%-"+CONTEXT_MAX_LENGTH+"S\\ %-4S]",
		                               context.substring( 0, Math.min( context.length(), CONTEXT_MAX_LENGTH - 1 ) ),
		                               debugLevel.getPostfix() );

		String whitespace = new String( new char[WHITESPACE_AFTER_PREFIX] ).replace( '\0', ' ' );

		return createTimestamp( date ) +
				debugLevel.applyColor( prefix + whitespace + message );
	}

	private static String createTimestamp(Date date) {
		return String.format( "[%1$td.%1$tm.%1$tY - %1$tH:%1$tM:%1$tS]", date ); // [dd.mm.yyyy - hh:mm:ss]
	}
}
