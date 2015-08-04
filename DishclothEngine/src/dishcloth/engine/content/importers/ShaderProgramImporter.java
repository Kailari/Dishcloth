package dishcloth.engine.content.importers;

import dishcloth.engine.content.AContentImporter;

/**
 * <b>ShaderProgramImporter</b>
 * <p>
 * An importer that reads shader description file, parses it for shader source-file paths,
 * and returns array of those paths. Paths are returned as strings, ordered by indices
 * found in <b>ShaderProgramImporter.*_SHADER_INDEX</b> constants.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 4.8.2015
 *
 * @see dishcloth.engine.content.AContentImporter
 */

public class ShaderProgramImporter extends AContentImporter<String[]> {
	public static final int VERTEX_SHADER_INDEX = 0;
	public static final int FRAGMENT_SHADER_INDEX = 1;

	private static final ReadAllLinesImporter importer = new ReadAllLinesImporter();

	@Override
	public String[] read(String filename) {
		String[] result = new String[2];

		String[] shaderInfoString = importer.read( filename ).split( "\n" );
		for (String line : shaderInfoString) {
			if (line.length() == 0) {
				continue;
			}

			String[] split = line.split( ":" );
			if (split.length != 3) {
				continue;
			}

			if (!split[2].replace( "\\", "/" ).startsWith( "/" )) {
				split[2] = "/" + split[2];
			}
			String path = split[1] + "/shaders" + split[2];
			switch (split[0].toLowerCase()) {
				case "vert":
					result[VERTEX_SHADER_INDEX] = importer.read( path );
					break;
				case "frag":
					result[FRAGMENT_SHADER_INDEX] = importer.read( path );
					break;
			}
		}

		return result;
	}
}
