package dishcloth.engine.io;

import dishcloth.engine.util.logger.Debug;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * IOHelper.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Helper class for handling basic file I/O
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 22.5.2015
 */

public class IOHelper {

	public static String readFromFile(String filename) {

		StringBuilder source = new StringBuilder();

		try {

		}
		catch (Exception e) {
			Debug.logException( e, "IOHelper" );
		}

		return source.toString();
	}

}
