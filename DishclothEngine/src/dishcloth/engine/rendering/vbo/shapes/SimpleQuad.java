package dishcloth.engine.rendering.vbo.shapes;

import dishcloth.engine.rendering.vbo.ColorTextureVertex;
import dishcloth.engine.rendering.vbo.Vertex;
import dishcloth.engine.util.Color;
import dishcloth.engine.util.geom.Rectangle;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * SimpleQuad.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 23.5.2015
 */

public class SimpleQuad extends Polygon<ColorTextureVertex> {
	public SimpleQuad(float w, float h) {
		super( new ColorTextureVertex[]{
				new ColorTextureVertex( -w / 2f, -h / 2f, Color.WHITE.toInteger(), 0f, 1f ),     // Left bot
				new ColorTextureVertex( +w / 2f, -h / 2f, Color.WHITE.toInteger(), 1f, 1f ),     // right top
				new ColorTextureVertex( +w / 2f, +h / 2f, Color.WHITE.toInteger(), 1f, 0f ),     // Right bottom
				new ColorTextureVertex( -w / 2f, +h / 2f, Color.WHITE.toInteger(), 0f, 0f )     // Left top
		} );
	}

	public SimpleQuad(Rectangle bounds) {
		super( new ColorTextureVertex[]{
				new ColorTextureVertex( bounds.x, bounds.y, Color.WHITE.toInteger(), 0f, 1f ),
				new ColorTextureVertex( bounds.x + bounds.w, bounds.y, Color.WHITE.toInteger(), 1f, 1f ),
				new ColorTextureVertex( bounds.x + bounds.w, bounds.y + bounds.h, Color.WHITE.toInteger(), 1f, 0f ),
				new ColorTextureVertex( bounds.x, bounds.y + bounds.h, Color.WHITE.toInteger(), 0f, 0f )
		} );
	}
}
