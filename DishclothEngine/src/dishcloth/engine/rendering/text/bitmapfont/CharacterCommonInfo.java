package dishcloth.engine.rendering.text.bitmapfont;

/**
 * CharacterCommonInfo.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 25.7.2015
 */

public class CharacterCommonInfo {
	private int lineHeight, baseHeight, textureWidth, textureHeight, nPages;

	private CharacterCommonInfo
			(
					int lineHeight,
					int baseHeight,
					int textureWidth,
					int textureHeight,
					int nPages
			) {

		this.lineHeight = lineHeight;
		this.baseHeight = baseHeight;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.nPages = nPages;
	}

	/**
	 * Creates new CharacterCommonInfo by parsing its values from given strings
	 */
	public static CharacterCommonInfo parseFromString(String commonInfoString) {
		int lineHeight = 0, baseHeight = 0, textureWidth = 0, textureHeight = 0, nPages = 0;

		// Remove the tag
		commonInfoString = commonInfoString.replace( "common ", "" );

		// Split by spaces
		String[] tags = commonInfoString.split( " " );

		// Iterate trough all tag-value-pairs
		for (String tag : tags) {
			// Tag and value are separated by equals-sign
			String tagName = tag.split( "=" )[0].toLowerCase();
			int tagValue = Integer.valueOf( tag.split( "=" )[1] );

			switch (tagName) {
				case "lineheight":
					lineHeight = tagValue;
					break;
				case "base":
					baseHeight = tagValue;
					break;
				case "scalew":
					textureWidth = tagValue;
					break;
				case "scaleh":
					textureHeight = tagValue;
					break;
				case "pages":
					nPages = tagValue;
					break;
				default:
					// Ignore other tags
					break;
			}
		}

		// Use parsed values to create new instance
		return new CharacterCommonInfo( lineHeight, baseHeight, textureWidth, textureHeight, nPages );
	}

	public int getLineHeight() {
		return lineHeight;
	}

	public int getBaseHeight() {
		return baseHeight;
	}

	public int getTextureWidth() {
		return textureWidth;
	}

	public int getTextureHeight() {
		return textureHeight;
	}

	public int getPages() {
		return nPages;
	}
}
