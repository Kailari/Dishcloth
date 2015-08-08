package dishcloth.engine.rendering.text;

import dishcloth.api.util.memory.PointCache;
import dishcloth.api.util.memory.RectangleCache;
import dishcloth.engine.rendering.render2d.sprites.batch.SpriteBatch;
import dishcloth.engine.rendering.text.bitmapfont.BitmapFont;
import dishcloth.api.util.Color;
import dishcloth.api.util.geom.Point;
import dishcloth.api.util.geom.Rectangle;

/**
 * TextRenderer.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 25.7.2015
 */

public class TextRenderer {
	private static Point origin = PointCache.getPoint( 0, 0 );
	private static Rectangle destination = RectangleCache.getRectangle( 0, 0, 0, 0 );

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

		int index = 0;
		for (char c : characters) {

			if (c == '\n') {
				cursorPosition = 0;
				lineIndex++;
			} else {

				Rectangle source = font.getSourceRectangleForCharacter( c );
				if (source == null) {
					continue;
				}

				//origin.y = -source.h;

				destination = font.getBaseDestinationRectangleForCharacter( c, destination, cursorPosition, lineIndex );
				destination.setX( destination.getX() + position.getX() );
				destination.setY( destination.getY() + position.getY() );

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
