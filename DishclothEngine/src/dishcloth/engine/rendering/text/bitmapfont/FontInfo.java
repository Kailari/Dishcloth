package dishcloth.engine.rendering.text.bitmapfont;

/**
 * FontInfo.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 25.7.2015
 */

public class FontInfo {
	int paddingUp, paddingRight, paddingDown, paddingLeft, spacingX, spacingY;
	private String ttfName;
	private int ttfFontsize, yAxisStretchPercentage;

	public FontInfo(String ttfName, int ttfFontsize, int yAxisStretchPercentage,
	                int paddingUp, int paddingRight, int paddingDown, int paddingLeft,
	                int spacingX, int spacingY) {
		this.ttfName = ttfName;
		this.ttfFontsize = ttfFontsize;
		this.yAxisStretchPercentage = yAxisStretchPercentage;
		this.paddingUp = paddingUp;
		this.paddingRight = paddingRight;
		this.paddingDown = paddingDown;
		this.paddingLeft = paddingLeft;
		this.spacingX = spacingX;
		this.spacingY = spacingY;
	}

	/**
	 * Creates new FontInfo by parsing its values from given strings
	 */
	public static FontInfo parseFromString(String fontInfoString) {
		String ttfName = "NULL";
		int ttfFontsize = 0, yAxisStretchPercentage = 100;
		int paddingUp = 0, paddingRight = 0, paddingDown = 0, paddingLeft = 0;
		int spacingX = 0, spacingY = 0;

		// Remove the tag
		fontInfoString = fontInfoString.replace( "info ", "" );

		// Split by spaces
		String[] tags = fontInfoString.split( " " );

		// Iterate trough all tag-value-pairs
		for (String tag : tags) {
			// Tag and value are separated by equals-sign
			String tagName = tag.split( "=" )[0].toLowerCase();
			String tagValueString = tag.split( "=" )[1];

			if (tagName.equalsIgnoreCase( "face" )) {

				ttfName = tagValueString;

			} else if (tagName.equalsIgnoreCase( "padding" )) {

				String[] values = tagValueString.split( "," );
				paddingUp = Integer.valueOf( values[0] );
				paddingRight = Integer.valueOf( values[1] );
				paddingDown = Integer.valueOf( values[2] );
				paddingLeft = Integer.valueOf( values[3] );

			} else if (tagName.equalsIgnoreCase( "spacing" )) {

				String[] values = tagValueString.split( "," );
				spacingX = Integer.valueOf( values[0] );
				spacingY = Integer.valueOf( values[1] );

			} else if (tagName.equalsIgnoreCase( "charset" )) {
				// Ignored
			} else {

				int tagValue = Integer.valueOf( tagValueString );
				switch (tagName) {
					case "size":
						ttfFontsize = tagValue;
						break;
					case "stretchh":
						yAxisStretchPercentage = tagValue;
						break;
					default:
						// Ignore other tags
						break;
				}
			}
		}

		// Use parsed values to create new instance
		return new FontInfo( ttfName, ttfFontsize, yAxisStretchPercentage,
		                     paddingUp, paddingRight, paddingDown, paddingLeft,
		                     spacingX, spacingY );
	}

	public int getPaddingUp() {
		return paddingUp;
	}

	public int getPaddingRight() {
		return paddingRight;
	}

	public int getPaddingDown() {
		return paddingDown;
	}

	public int getPaddingLeft() {
		return paddingLeft;
	}

	public int getSpacingX() {
		return spacingX;
	}

	public int getSpacingY() {
		return spacingY;
	}

	public String getTtfName() {
		return ttfName;
	}

	public int getTtfFontsize() {
		return ttfFontsize;
	}

	public int getyAxisStretchPercentage() {
		return yAxisStretchPercentage;
	}
}
