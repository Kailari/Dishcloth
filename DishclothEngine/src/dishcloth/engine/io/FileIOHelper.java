package dishcloth.engine.io;

import dishcloth.engine.util.logger.Debug;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * FileIOHelper.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Helper class for handling basic file I/O
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 22.5.2015
 */

public class FileIOHelper {

	public static final String RES_PATH = "/dishcloth_resources/";

	public static InputStream createInputStream(String filename) {
		return FileIOHelper.class.getResourceAsStream( RES_PATH + filename );
	}

	public static String readLinesFromFile(String filename) {

		// Remove slashes from the start
		if (filename.startsWith( "/" ) || filename.startsWith( "\\" )) {
			filename = filename.substring( 1 );
		}


		StringBuilder source = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader( createInputStream( filename ) ) );

			String line;
			while ((line = reader.readLine()) != null) {
				source.append( line ).append( "\n" );
			}

		} catch (Exception e) {
			Debug.logException( e, "FileIOHelper" );
		}

		return source.toString();
	}
}
