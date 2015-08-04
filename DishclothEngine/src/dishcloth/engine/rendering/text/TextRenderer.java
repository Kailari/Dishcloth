package dishcloth.engine.rendering.text;

import dishcloth.engine.rendering.render2d.sprites.batch.SpriteBatch;
import dishcloth.engine.rendering.text.bitmapfont.BitmapFont;
import dishcloth.engine.util.Color;
import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.geom.Rectangle;
import dishcloth.engine.util.math.Vector2;

/**
 * TextRenderer.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 25.7.2015
 */

public class TextRenderer {
	private TextRenderer() {}

	public static void renderText
	(
			SpriteBatch spriteBatch,
			Point position,
			BitmapFont font,
			Color tint,
			String string
	) {

		int cursorPosition = 0, lineIndex = 0;

		char[] characters = string.toCharArray();

		Point origin = new Point( 0, 0 );
		Rectangle destination = new Rectangle( 0, 0, 0, 0 );
		int index = 0;
		for (char c : characters) {

			if (c == '\n') {
				cursorPosition = 0;
				lineIndex++;
			} else {

				Rectangle source = font.getSourceRectangleForCharacter( c );

				//origin.y = -source.h;

				destination = font.getBaseDestinationRectangleForCharacter( c, destination, cursorPosition, lineIndex );
				destination.x += position.x;
				destination.y += position.y;

				if (index < characters.length - 1) {
					cursorPosition += font.getAdvance( c, characters[index + 1] );
				}

				spriteBatch.queue( font.getCharacterPageTexture( c ),
				                   destination,
				                   source,
				                   0f,
				                   tint,
				                   origin );
			}

			index++;
		}
	}
}
