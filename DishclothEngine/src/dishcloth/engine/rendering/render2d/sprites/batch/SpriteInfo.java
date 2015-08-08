package dishcloth.engine.rendering.render2d.sprites.batch;

import dishcloth.api.util.math.DishMath;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vao.vertex.ColorTextureVertex;
import dishcloth.api.util.Color;
import dishcloth.api.util.geom.Point;
import dishcloth.api.util.geom.Rectangle;

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
	ColorTextureVertex topLeft, topRight, botLeft, botRight;

	SpriteInfo(Rectangle source, Rectangle destination, float angle, Point origin, Texture texture, int tint) {
		this.topLeft = new ColorTextureVertex();
		this.topRight = new ColorTextureVertex();
		this.botLeft = new ColorTextureVertex();
		this.botRight = new ColorTextureVertex();

		this.setData( source, destination, angle, origin, texture, tint );
	}
	
	void setData(Rectangle source, Rectangle destination, float angle, Point origin, Texture texture, int tint) {

		if (DishMath.approxSame( angle, 0.0D )) {
			setData( source, destination, origin, texture, tint );
		} else {
			this.texture = texture;

			float cos = (float) Math.cos( angle );
			float sin = (float) Math.sin( angle );

			float uStart = source.getX() / texture.getWidth();
			float vStart = source.getY() / texture.getHeight();
			float uEnd = uStart + (source.getW() / texture.getWidth());
			float vEnd = vStart + (source.getH() / texture.getHeight());

			this.topLeft.setData(
					destination.getX() + (-origin.getX()) * cos - (-origin.getY()) * sin,
					destination.getY() + (-origin.getY()) * sin + (-origin.getY()) * cos,
					tint,
					uStart,
					vEnd
			);

			this.topRight.setData(
					destination.getX() + ((-origin.getX()) + destination.getW()) * cos - (-origin.getY()) * sin,
					destination.getY() + ((-origin.getY()) + destination.getW()) * sin + (-origin.getY()) * cos,
					tint,
					uEnd,
					vEnd
			);

			this.botLeft.setData(
					destination.getX() + (-origin.getX()) * cos - ((-origin.getY()) + destination.getH()) * sin,
					destination.getY() + (-origin.getX()) * sin + ((-origin.getY()) + destination.getH()) * cos,
					tint,
					uStart,
					vStart
			);

			this.botRight.setData(
					destination.getX() + ((-origin.getX()) + destination.getW()) * cos - ((-origin.getY()) + destination.getH()) * sin,
					destination.getY() + ((-origin.getX()) + destination.getW()) * sin + ((-origin.getY()) + destination.getH()) * cos,
					tint,
					uEnd,
					vStart
			);
		}
	}

	void setData(Rectangle source, Rectangle destination, Point origin, Texture texture, int tint) {
		this.texture = texture;

		float uStart = source.getX() / texture.getWidth();
		float vStart = source.getY() / texture.getHeight();
		float uEnd = uStart + (source.getW() / texture.getWidth());
		float vEnd = vStart + (source.getH() / texture.getHeight());

		this.topLeft.setData(
				destination.getLeftX() - origin.getX(),
				destination.getTopY() - origin.getY(),
				tint,
				uStart,
				vStart
		);

		this.topRight.setData(
				destination.getRightX() - origin.getX(),
				destination.getTopY() - origin.getY(),
				tint,
				uEnd,
				vStart
		);

		this.botLeft.setData(
				destination.getLeftX() - origin.getX(),
				destination.getBottomY() - origin.getY(),
				tint,
				uStart,
				vEnd
		);

		this.botRight.setData(
				destination.getRightX() - origin.getX(),
				destination.getBottomY() - origin.getY(),
				tint,
				uEnd,
				vEnd
		);
	}
}
