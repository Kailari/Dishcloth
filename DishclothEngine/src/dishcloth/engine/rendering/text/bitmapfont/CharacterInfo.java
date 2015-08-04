package dishcloth.engine.rendering.text.bitmapfont;

import dishcloth.engine.util.geom.Rectangle;
import dishcloth.engine.util.logger.Debug;

/**
 * CharacterInfo.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 25.7.2015
 */

public class CharacterInfo {
	private char id;
	private int x, y, width, height, xOffset, yOffset, xAdvance, page;
	private Rectangle spriteBatchSourceRectangle;
	
	public CharacterInfo(char id, int x, int y, int width, int height, int xOffset, int yOffset, int xAdvance, int page) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.xAdvance = xAdvance;
		this.page = page;

		this.spriteBatchSourceRectangle = new Rectangle( this.x, this.y, this.width, this.height );
	}
	
	public static CharacterInfo parseFromString(String characterInfoString) {
		char id = 0;
		int x = 0, y = 0, width = 0, height = 0, xOffset = 0, yOffset = 0, xAdvance = 0, page = 0;
		
		// Remove the tag
		characterInfoString = characterInfoString.replace( "char ", "" );
		
		// Split by spaces
		String[] tags = characterInfoString.split( " " );
		
		// Iterate trough all tag-value-pairs
		for (String tag : tags) {
			if (tag.length() == 0) {
				continue;
			}

			// Tag and value are separated by equals-sign
			String tagName = tag.split( "=" )[0].toLowerCase();
			String tagValueString = tag.split( "=" )[1];

			if (tagName.equalsIgnoreCase( "id" )) {

				id = Character.toChars( Integer.valueOf( tagValueString ) )[0];
				//Debug.log( "id: " + id + " (" + Integer.valueOf( tagValueString ) + ")", "CharacterInfo" );

			} else {
				int tagValue = Integer.valueOf( tagValueString );

				switch (tagName) {
					case "x":
						x = tagValue;
						break;
					case "y":
						y = tagValue;
						break;
					case "width":
						width = tagValue;
						break;
					case "height":
						height = tagValue;
						break;
					case "xoffset":
						xOffset = tagValue;
						break;
					case "yoffset":
						yOffset = tagValue;
						break;
					case "xadvance":
						xAdvance = tagValue;
						break;
					case "page":
						page = tagValue;
						break;
					default:
						// Ignore other tags
						break;
				}
			}
		}
		
		return new CharacterInfo( id, x, y, width, height, xOffset, yOffset, xAdvance, page );
	}

	public char getID() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getOffsetX() {
		return xOffset;
	}

	public int getOffsetY() {
		return yOffset;
	}

	public int getAdvanceX() {
		return xAdvance;
	}

	public int getPage() {
		return page;
	}

	public Rectangle getSpriteBatchSourceRectangle() {
		return spriteBatchSourceRectangle;
	}
}
