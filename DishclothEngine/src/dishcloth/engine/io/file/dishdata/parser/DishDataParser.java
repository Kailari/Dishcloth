package dishcloth.engine.io.file.dishdata.parser;

import dishcloth.engine.io.FileIOHelper;
import dishcloth.engine.io.file.dishdata.headers.DefaultHeaders;
import dishcloth.engine.io.file.dishdata.headers.HeaderRegistry;
import dishcloth.engine.io.file.dishdata.headers.IHeaderDefinition;
import dishcloth.engine.io.file.dishdata.nodes.DataNode;
import dishcloth.engine.io.file.dishdata.nodes.HierarchyNode;
import dishcloth.engine.io.file.dishdata.nodes.Node;
import dishcloth.engine.util.logger.Debug;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * DishDataParser.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 19.6.2015
 */

public class DishDataParser {

	public static HierarchyNode parseFile(String filename) {
		String allLines = FileIOHelper.readLinesFromFile( filename );

		// Delete comments
		while (allLines.contains( "#" )) {
			int commentStartIndex = allLines.indexOf( "#" );
			int commentEndIndex = allLines.indexOf( "\n", commentStartIndex );

			int oldLength = allLines.length();

			allLines = allLines.substring( 0, commentStartIndex ) + allLines.substring( commentEndIndex );

			assert allLines.length() == oldLength - (commentEndIndex - commentStartIndex);
		}

		// Remove empty lines and whitespace (regex is horrifying.)

		/*
		 * Parsed using the monstrous regex:
		 *      '^\s+'       => match whitespace at the beginning (\s is flag for all whitespace characters)
		 *      '\s+$'       => match whitespace at the end
		 *      '\s*(\n)\s*' => match whitespace that contains newline and capture the newline, discarding whitespace
		 *      '\s'         => match all whitespace
		 *
		 *      ['\s' flag includes tabs, spaces, newlines, null- and invisible characters, etc.]
		 *
		 *  Regex returns string stripped of ALL whitespace characters. It also returns captured newlines in '$1'.
		 *  By putting '$1' to replacement, we end up with all whitespace removed, and exactly one newline at
		 *  the end of every line.
		 */
		allLines = allLines.replaceAll( "^\\s+|\\s+$|\\s*(\n)\\s*|\\s", "$1" );

		// Split
		String[] lines = allLines.split( "\n" );

		// Recursively parse hierarchy (we know for sure that root-node is hierarchy node, and not a simple datanode)
		return parseHierarchyNode( lines );
	}

	private static HierarchyNode parseHierarchyNode(String[] lines) {
		// Read tag
		String tag = lines[0];

		// Convert to IHeaderDefinition using HeaderRegistry
		IHeaderDefinition tagDefinition = HeaderRegistry.getHeader( tag );

		// Create node
		HierarchyNode node = new HierarchyNode( tagDefinition );

		// Get possible subKeys
		List<String> possibleSubKeys = Arrays.asList( tagDefinition.getCompatibleSubkeys() );

		// Parse child nodes

		boolean blockOpen = false;
		String blockSubKey = "";
		int startIndex = -1;
		int endIndex;

		// Start from index 2 (third line) as the first line is the tag definition and the second line is a bracket
		// Also, iterate until 'length - 1' as last line is closing bracket
		for (int line = 2; line < lines.length - 1; line++) {
			if (!blockOpen) {
				if (possibleSubKeys.contains( lines[line] )) {
					startIndex = line;
					blockSubKey = lines[line];
					blockOpen = true;
				}
			}
			else if (lines[line].equals( "}" )) {
				endIndex = line;
				blockOpen = false;

				String[] subLines = new String[endIndex - startIndex];
				System.arraycopy( lines, startIndex, subLines, 0, subLines.length );

				node.setData( blockSubKey, parseNode( subLines ) );
			}
		}

		return node;
	}

	private static Node parseNode(String[] lines) {
		// Read tag
		String tag = lines[0];

		// Convert to IHeaderDefinition using HeaderRegistry
		IHeaderDefinition tagDefinition = HeaderRegistry.getHeader( tag );

		// Make sure we aren't parsing a hierarchy node
		if (tagDefinition.getCompatibleSubkeys().length != 0) {
			return parseHierarchyNode( lines );
		}

		// Create dataNode
		DataNode node = new DataNode(tagDefinition);

		// Parse data (for info on start and end indices, look at parseHierarchyNode method's for-loop)
		for (int line = 2; line < lines.length - 1; line++) {
			String[] splitLine = lines[line].split( ":" );

			assert splitLine.length == 3;

			String key = splitLine[0];
			String datatypeKey = splitLine[1];
			String rawData = splitLine[2];

			Object data = null;
			switch (datatypeKey) {
				case "s":
				case "str":
				case "string":
					// TODO: Parse translations-files into a registry of some kind and read/compare with that
					data = rawData;
					break;
				case "i":
				case "int":
				case "integer":
					data = Integer.valueOf( rawData );
					break;
				case "f":
				case "float":
					data = Float.valueOf( rawData );
					break;
				default:
					Debug.logErr( "Invalid datatypekey", "DishDataParser" );
					break;
			}

			node.setData( key, data );
		}

		return node;
	}
}
