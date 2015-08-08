package dishcloth.engine.rendering.text.bitmapfont;

import dishcloth.engine.content.AContent;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.api.util.geom.Rectangle;

import java.util.HashMap;

/**
 * <b>BitmapFont</b>
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 25.7.2015
 */

public class BitmapFont extends AContent {
	
	private FontInfo fontInfo;
	private CharacterCommonInfo commonInfo;
	private FontPage[] pages;
	private HashMap<Character, CharacterInfo> characters;
	private HashMap<Character, Kernings> kerningPairs;

	public BitmapFont(FontInfo fontInfo, CharacterCommonInfo commonInfo, FontPage[] pages, HashMap<Character, CharacterInfo> characters, HashMap<Character, Kernings> kerningPairs) {
		this.fontInfo = fontInfo;
		this.commonInfo = commonInfo;
		this.pages = pages;
		this.characters = characters;
		this.kerningPairs = kerningPairs;
	}

	public Rectangle getSourceRectangleForCharacter(char characterID) {
		CharacterInfo info = getCharacter( characterID );
		if (info != null) {
			return info.getSpriteBatchSourceRectangle();
		} else {
			return null;
		}
	}

	public int getLineHeight() {
		return commonInfo.getLineHeight();
	}

	public int getBaseY() {
		return getLineHeight() - commonInfo.getBaseHeight();
	}

	public Texture getCharacterPageTexture(char characterID) {
		CharacterInfo info = getCharacter( characterID );
		if (info != null) {
			return pages[info.getPage()].getTexture();
		} else {
			return pages[0].getTexture();
		}
	}

	/**
	 * Takes rectangle as parameter in order to avoid disposable memory allocations
	 *
	 * @param rectangle recycled rectangle
	 */
	public Rectangle getBaseDestinationRectangleForCharacter
	(
			char characterID,
			Rectangle rectangle,
			int cursorPosition,
			int lineIndex
	) {
		CharacterInfo info = getCharacter( characterID );
		if (info != null) {

			rectangle.setX( cursorPosition + info.getOffsetX() ); // Assume that cursor position resets on newline
			rectangle.setY( getBaseY()
					                - (getLineHeight() - (info.getOffsetY() + info.getHeight()))
					                - lineIndex * getLineHeight() );
			rectangle.setW( info.getWidth() );
			rectangle.setH( info.getHeight() );

		} else {

			rectangle.setX( 0 );
			rectangle.setY( 0 );
			rectangle.setW( 0 );
			rectangle.setH( 0 );

		}

		return rectangle;
	}

	/**
	 * Calculates how much the cursor should move
	 *
	 * @param previousID previous character
	 * @param nextID     next character
	 * @return character xAdvance + kerning
	 */
	public int getAdvance(char previousID, char nextID) {
		CharacterInfo info;
		int advance = ((info = getCharacter( previousID )) != null ? info.getAdvanceX() : 0);

		if (kerningPairs.containsKey( previousID )) {
			advance += kerningPairs.get( previousID ).getKerningAmmount( nextID );
		}

		return advance;
	}

	private CharacterInfo getCharacter(char id) {
		return this.characters.containsKey( id ) ? this.characters.get( id ) : null;
	}

	@Override
	public void dispose() {
		// Release page textures
		for (FontPage page : pages) {
			page.dispose();
		}
	}
}
