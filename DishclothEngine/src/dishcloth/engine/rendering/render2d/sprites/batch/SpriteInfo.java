package dishcloth.engine.rendering.render2d.sprites.batch;

import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vbo.ColorTextureVertex;
import dishcloth.engine.rendering.vbo.Vertex;
import dishcloth.engine.util.Color;
import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.geom.Rectangle;

/**
 * SpriteInfo.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 21.7.2015
 */

// TODO: Figure out a convenient way to change vertex type. (<T extends Vertex>, perhaps?)
class SpriteInfo {
	Texture texture;
	Vertex topLeft, topRight, botLeft, botRight;

	// TODO: Add colors
	SpriteInfo(Rectangle source, Rectangle destination, float angle, Point origin, Texture texture, Color tint) {
		this.topLeft = new ColorTextureVertex( 0f, 0f, tint.toInteger(), 0f, 0f );
		this.topRight = new ColorTextureVertex( 0f, 0f, tint.toInteger(), 0f, 0f );
		this.botLeft = new ColorTextureVertex( 0f, 0f, tint.toInteger(), 0f, 0f );
		this.botRight = new ColorTextureVertex( 0f, 0f, tint.toInteger(), 0f, 0f );
		this.setData( source, destination, angle, origin, texture, tint );
	}
	
	void setData(Rectangle source, Rectangle destination, float angle, Point origin, Texture texture, Color tint) {
		this.texture = texture;
		
		float cos = (float) Math.cos( angle );
		float sin = (float) Math.sin( angle );
		
		float uStart = source.x / texture.getWidth();
		float vStart = source.y / texture.getHeight();
		float uEnd = uStart + (source.w / texture.getWidth());
		float vEnd = vStart + (source.h / texture.getHeight());
		
		this.topLeft.setData(
				destination.x + (-origin.x) * cos - (-origin.y) * sin,
				destination.y + (-origin.x) * sin + (-origin.y) * cos,
				uStart, vEnd );
		this.topRight.setData(
				destination.x + ((-origin.x) + destination.w) * cos - (-origin.y) * sin,
				destination.y + ((-origin.x) + destination.w) * sin + (-origin.y) * cos,
				uEnd, vEnd );
		this.botLeft.setData(
				destination.x + (-origin.x) * cos - ((-origin.y) + destination.h) * sin,
				destination.y + (-origin.x) * sin + ((-origin.y) + destination.h) * cos,
				uStart, vStart );
		this.botRight.setData(
				destination.x + ((-origin.x) + destination.w) * cos - ((-origin.y) + destination.h) * sin,
				destination.y + ((-origin.x) + destination.w) * sin + ((-origin.y) + destination.h) * cos,
				uEnd, vStart );
	}
}
