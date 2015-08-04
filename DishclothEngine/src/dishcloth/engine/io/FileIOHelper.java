package dishcloth.engine.io;

import java.io.InputStream;

/**
 * <b>FileIOHelper</b>
 * <p>
 * Helper class for handling basic file I/O.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 22.5.2015
 */

public class FileIOHelper {

	public static final String RES_PATH = "/dishcloth/resources/";

	// TODO: Create a mod-sensitive version. ex. can search content from both "/resources/engine/" and "/resources/game/"
	// (it also could/should auto-detect the mod)
	public static InputStream createInputStream(String filename) {
		return FileIOHelper.class.getResourceAsStream( RES_PATH + filename );
	}

	public static InputStream createInputStream(String filename, String modID) {
		if (filename.replace( "\\", "/" ).startsWith( "/" )) {
			filename = filename.substring( 1 );
		}
		return FileIOHelper.class.getResourceAsStream( RES_PATH + modID + "/" + filename );
	}
}
