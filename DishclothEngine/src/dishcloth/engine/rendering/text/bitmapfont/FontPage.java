package dishcloth.engine.rendering.text.bitmapfont;

import dishcloth.engine.content.ContentManager;
import dishcloth.engine.rendering.textures.Texture;

/**
 * FontPage.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 25.7.2015
 */

public class FontPage {
	private short id;
	private Texture texture;

	public FontPage(short id, String textureName, ContentManager contentManager) {
		this.id = id;
		this.texture = contentManager.loadContent( textureName );
	}

	/**
	 * Creates new FontPage by parsing its values from given strings
	 */
	public static FontPage parseFromString(String pageString, ContentManager contentManager) {
		short id = -1;
		String textureName = "NULL";

		// Remove the tag
		pageString = pageString.replace( "page ", "" );

		// Split by spaces
		String[] tags = pageString.split( " " );

		// Iterate trough all tag-value-pairs
		for (String tag : tags) {
			// Tag and value are separated by equals-sign
			String tagName = tag.split( "=" )[0].toLowerCase();
			String tagValueString = tag.split( "=" )[1];

			if (tagName.equalsIgnoreCase( "id" )) {

				id = Short.valueOf( tagValueString );

			} else if (tagName.equalsIgnoreCase( "file" )) {

				textureName = "/" + tagValueString.replace( "\"", "" );

			} else {
				// Ignore other tags
			}
		}

		return new FontPage( id, textureName, contentManager );
	}

	public short getId() {
		return id;
	}

	public Texture getTexture() {
		return texture;
	}

	public void dispose() {
		texture.dispose();
	}
}
