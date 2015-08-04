package dishcloth.engine.save.datapaths;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * BlockIDHeaderDataPath.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 10.6.2015
 */

public class BlockIDHeaderDataPath extends AFileDataPath {
	public BlockIDHeaderDataPath(String path) {
		super( preparePath(path) + "blockIDs.dat" );
	}

	private static String preparePath(String path) {
		if (!path.endsWith( "/" ) && !path.endsWith( "\\" )) {
			path = path.replace( "\\", "/" ) + "/";
		}

		if (!path.startsWith( "./" )) {
			if (path.startsWith( "/" ) || path.startsWith( "\\" )) {
				path = path.substring( 1 );
			}

			path = "./" + path;
		}

		return path;
	}
}
