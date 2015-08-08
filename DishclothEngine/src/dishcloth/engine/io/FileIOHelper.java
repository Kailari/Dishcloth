package dishcloth.engine.io;

import dishcloth.engine.modules.ModuleManager;

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
		InputStream stream = FileIOHelper.class.getResourceAsStream( RES_PATH + filename );
		if (stream == null) {
			stream = ModuleManager.createInputStream( RES_PATH + filename );
		}
		return stream;
	}

	public static InputStream createInputStream(String filename, String modID) {
		if (filename.replace( "\\", "/" ).startsWith( "/" )) {
			filename = filename.substring( 1 );
		}
		return FileIOHelper.class.getResourceAsStream( RES_PATH + modID + "/" + filename );
	}
}
