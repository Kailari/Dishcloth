package dishcloth.engine.util;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * DebugLevel.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Debug levels.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 22.5.2015
 */

public enum DebugLevel {

	LOG( "LOG", ANSIColor.WHITE ),
	NOTE( "NOTE", ANSIColor.BLUE ),
	SUCCESS( "OK", ANSIColor.GREEN ),
	WARNING( "WARN", ANSIColor.YELLOW ),
	ERROR( "ERR", ANSIColor.RED );

	private final String logPostfix;
	private final ANSIColor logColor;

	DebugLevel(String logPostfix, ANSIColor logColor) {
		this.logPostfix = logPostfix;
		this.logColor = logColor;
	}

	public String applyColor(String message) {
		return logColor.applyTo( message );
	}

	public String getPostfix() {
		return logPostfix;
	}
}
