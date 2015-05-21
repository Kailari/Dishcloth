package dishcloth.engine.util;

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

	public static void log(String message, String context, Level debugLvl) {

	}

	public enum Level {

		LOG( "LOG", Color.RED ),
		NOTE( "NOTE", Color.RED ),
		WARNING( "WARN", Color.RED ),
		ERROR( "ERR", Color.RED );

		private final String logPostfix;
		private final Color logColor;

		Level(String logPostfix, Color logColor) {
			this.logPostfix = logPostfix;
			this.logColor = logColor;
		}
	}
}
