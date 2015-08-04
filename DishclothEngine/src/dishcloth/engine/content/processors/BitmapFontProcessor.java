package dishcloth.engine.content.processors;

import dishcloth.engine.content.AContentProcessor;
import dishcloth.engine.content.ContentManager;
import dishcloth.engine.rendering.text.bitmapfont.*;

import java.util.HashMap;

/**
 * <b>BitmapFontProcessor</b>
 * <p>
 * Processes data received from ReadAllLinesImporter and compiles it into a {@link BitmapFont}
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 4.8.2015
 *
 * @see dishcloth.engine.content.AContentProcessor
 * @see BitmapFont
 */

public class BitmapFontProcessor extends AContentProcessor<String, BitmapFont> {
	@Override
	public BitmapFont process(String read, ContentManager contentManager) {
		String[] lines = read.split( "\n" );

		int line = 0;

		FontInfo fontInfo = FontInfo.parseFromString( lines[line++] );
		CharacterCommonInfo commonInfo = CharacterCommonInfo.parseFromString( lines[line++] );

		// TODO: Compile all page textures into a single texture atlas
		FontPage[] pages = new FontPage[commonInfo.getPages()];
		for (int i = 0; i < pages.length; i++) {
			pages[i] = FontPage.parseFromString( lines[line++], contentManager );
		}

		int charCount = parseCount( lines[line++], "chars" );
		HashMap<Character, CharacterInfo> characters = new HashMap<>();
		for (int i = 0; i < charCount; i++) {
			CharacterInfo info = CharacterInfo.parseFromString( lines[line++] );
			characters.put( info.getID(), info );
		}

		HashMap<Character, Kernings> kerningPairs = new HashMap<>();
		if (line < lines.length - 1) {
			int kerningPairCount = parseCount( lines[line++], "kernings" );
			for (int i = 0; i < kerningPairCount; i++) {
				parseKerningPair( lines[line++], kerningPairs );
			}
		}

		return new BitmapFont( fontInfo, commonInfo, pages, characters, kerningPairs );
	}

	private void parseKerningPair(String line, HashMap<Character, Kernings> kerningPairs) {
		char firstID = 0, secondID = 0;
		int amount = 0;

		// Remove the tag
		line = line.replace( "kerning ", "" );

		// Split by spaces
		String[] tags = line.split( " " );

		// Iterate trough all tag-value-pairs
		for (String tag : tags) {
			// Ignore whitespace
			if (tag.length() == 0) {
				continue;
			}

			// Tag and value are separated by equals-sign
			String tagName = tag.split( "=" )[0].toLowerCase();
			String tagValueString = tag.split( "=" )[1];

			if (tagName.equalsIgnoreCase( "first" )) {

				firstID = Character.toChars( Integer.valueOf( tagValueString ) )[0];

			} else if (tagName.equalsIgnoreCase( "second" )) {

				secondID = Character.toChars( Integer.valueOf( tagValueString ) )[0];

			} else if (tagName.equalsIgnoreCase( "amount" )) {

				amount = Integer.valueOf( tagValueString );

			} else {
				// Ignore other tags
			}
		}

		if (!kerningPairs.containsKey( firstID )) {
			kerningPairs.put( firstID, new Kernings() );
		}

		kerningPairs.get( firstID ).addKerning( secondID, amount );
	}

	private int parseCount(String countString, String tag) {
		int nCharacters = -1;

		// Remove the tag
		countString = countString.replace( tag + " ", "" );

		// Split by spaces
		String[] tags = countString.split( " " );

		// Iterate trough all tag-value-pairs
		for (String tag1 : tags) {
			// Tag and value are separated by equals-sign
			String tagName = tag1.split( "=" )[0].toLowerCase();
			int tagValue = Integer.valueOf( tag1.split( "=" )[1] );

			if (tagName.equalsIgnoreCase( "count" )) {
				nCharacters = tagValue;
			}
		}

		return nCharacters;
	}
}
