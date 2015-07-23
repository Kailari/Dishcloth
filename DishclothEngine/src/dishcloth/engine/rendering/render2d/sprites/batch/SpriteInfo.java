package dishcloth.engine.rendering.render2d.sprites.batch;

import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vbo.ColorTextureVertex;
import dishcloth.engine.rendering.vbo.Vertex;
import dishcloth.engine.util.Color;
import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.geom.Rectangle;
import dishcloth.engine.util.logger.Debug;

/**
 * SpriteInfo.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 21.7.2015
 */

// TODO: Figure out a convenient way to change vertex type. (<T extends Vertex>, perhaps?)
class SpriteInfo<T extends ColorTextureVertex> {
	Texture texture;
	T topLeft, topRight, botLeft, botRight;

	SpriteInfo(Class<T> vertexClass, Rectangle source, Rectangle destination, float angle, Point origin, Texture texture, Color tint, Object... additionalData) {
		try {
			this.topLeft = vertexClass.newInstance();
			this.topRight = vertexClass.newInstance();
			this.botLeft = vertexClass.newInstance();
			this.botRight = vertexClass.newInstance();

			this.setData( source, destination, angle, origin, texture, tint, additionalData );
		} catch (IllegalAccessException | InstantiationException e) {
			Debug.logException( e, this );
		}
	}
	
	void setData(Rectangle source, Rectangle destination, float angle, Point origin, Texture texture, Color tint, Object... additionalData) {
		this.texture = texture;
		
		float cos = (float) Math.cos( angle );
		float sin = (float) Math.sin( angle );
		
		float uStart = source.x / texture.getWidth();
		float vStart = source.y / texture.getHeight();
		float uEnd = uStart + (source.w / texture.getWidth());
		float vEnd = vStart + (source.h / texture.getHeight());

		Object[] varargs = new Object[5 + additionalData.length];
		System.arraycopy( additionalData, 0, varargs, 5, additionalData.length );

		varargs[0] = destination.x + (-origin.x) * cos - (-origin.y) * sin;
		varargs[1] = destination.y + (-origin.x) * sin + (-origin.y) * cos;
		varargs[2] = tint;
		varargs[3] = uStart;
		varargs[4] = vEnd;

		this.topLeft.setData( varargs );


		varargs[0] = destination.x + ((-origin.x) + destination.w) * cos - (-origin.y) * sin;
		varargs[1] = destination.y + ((-origin.x) + destination.w) * sin + (-origin.y) * cos;
		varargs[3] = uEnd;
		varargs[4] = vEnd;

		this.topRight.setData( varargs );


		varargs[0] = destination.x + (-origin.x) * cos - ((-origin.y) + destination.h) * sin;
		varargs[1] = destination.y + (-origin.x) * sin + ((-origin.y) + destination.h) * cos;
		varargs[3] = uStart;
		varargs[4] = vStart;

		this.botLeft.setData( varargs );


		varargs[0] = destination.x + ((-origin.x) + destination.w) * cos - ((-origin.y) + destination.h) * sin;
		varargs[1] = destination.y + ((-origin.x) + destination.w) * sin + ((-origin.y) + destination.h) * cos;
		varargs[3] = uEnd;
		varargs[4] = vStart;

		this.botRight.setData( varargs );
	}
}
