package dishcloth.engine.rendering.render2d.sprites;


import dishcloth.api.util.memory.PointCache;
import dishcloth.api.util.memory.RectangleCache;
import dishcloth.engine.rendering.render2d.sprites.batch.SpriteBatch;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.api.util.Color;
import dishcloth.api.util.geom.Point;
import dishcloth.api.util.geom.Rectangle;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Sprite.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 21.5.2015
 */

public class Sprite {

	private static Rectangle sourceRectangle = RectangleCache.getRectangle( 0, 0, 0, 0 );
	private static Rectangle destinationRectangle = RectangleCache.getRectangle( 0, 0, 0, 0 );

	private Texture texture;

	private int nColumns, nRows, frame;

	private float frameW, frameH;

	private Point origin;

	public Sprite(Texture texture, int nColumns, int nRows, int frame, float defaultOriginX, float defaultOriginY) {
		this.texture = texture;
		this.nColumns = nColumns;
		this.nRows = nRows;
		this.frame = frame;

		this.frameW = (float) texture.getWidth() / this.nColumns;
		this.frameH = (float) texture.getHeight() / this.nRows;

		this.origin = PointCache.getPoint( defaultOriginX, defaultOriginY );
	}

	public Sprite(Texture texture, int nColumns, int nRows, int frame, Point origin) {
		this( texture, nColumns, nRows, frame, origin.getX(), origin.getY() );
	}

	public Sprite(Texture texture, int nColumns, int nRows, int frame, Anchor anchor) {
		this( texture, nColumns, nRows, frame,
		      anchor.createPivot( (float) texture.getWidth() / nColumns,
		                          (float) texture.getHeight() / nRows ) );
	}

	public Sprite(Texture texture, int nColumns, int nRows, int frame) {
		this( texture, nColumns, nRows, frame, Anchor.TOPLEFT );
	}

	public Sprite(Texture texture) {
		this( texture, 1, 1, 0 );
	}

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		while (frame >= nRows * nColumns) {
			frame -= nRows * nColumns;
		}

		while (frame < 0) {
			frame += nRows * nColumns;
		}

		this.frame = frame;
	}

	public void render(SpriteBatch spriteBatch, Point position, float angle, Color tint, Point customOrigin) {
		int row = (int) Math.floor( (float) frame / (float) nColumns );
		int column = frame % nColumns;

		sourceRectangle.setX( frameW * column );
		sourceRectangle.setY( frameH * row );
		sourceRectangle.setW( frameW );
		sourceRectangle.setH( frameH );

		destinationRectangle.setX( position.getX() );
		destinationRectangle.setY( position.getY() );
		destinationRectangle.setW( frameW );
		destinationRectangle.setH( frameH );

		spriteBatch.queue( texture, destinationRectangle, sourceRectangle, angle, tint, customOrigin );
	}

	public void render(SpriteBatch spriteBatch, Point position, float angle, Color tint) {
		render( spriteBatch, position, angle, tint, origin );
	}

	public void render(SpriteBatch spriteBatch, Point position, float angle) {
		render( spriteBatch, position, angle, Color.WHITE );
	}

	public void render(SpriteBatch spriteBatch, Point position) {
		render( spriteBatch, position, 0f );
	}


	public void dispose() {
		texture.dispose();
	}
}
