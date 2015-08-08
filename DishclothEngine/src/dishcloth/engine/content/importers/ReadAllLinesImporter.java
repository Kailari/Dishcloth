package dishcloth.engine.content.importers;

import dishcloth.engine.content.AContentImporter;
import dishcloth.engine.io.FileIOHelper;
import dishcloth.engine.util.debug.Debug;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * <b>ReadAllLinesImporter</b>
 * <p>
 * Simple content importer that reads all lines from a file to a string. Resulting string represents all
 * lines of the source file, separated by EOL-character '\n'
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 4.8.2015
 *
 * @see dishcloth.engine.content.AContentImporter
 */

public class ReadAllLinesImporter extends AContentImporter<String> {
	@Override
	public String read(String filename) {
		// Remove slashes from the start
		if (filename.startsWith( "/" ) || filename.startsWith( "\\" )) {
			filename = filename.substring( 1 );
		}

		StringBuilder source = new StringBuilder();
		try (BufferedReader reader = new BufferedReader( new InputStreamReader( FileIOHelper.createInputStream( filename ) ) )) {

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
