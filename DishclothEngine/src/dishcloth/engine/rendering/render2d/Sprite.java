package dishcloth.engine.rendering.render2d;


import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.util.Color;
import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.geom.Rectangle;

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

		this.origin = new Point( defaultOriginX, defaultOriginY );
	}

	public Sprite(Texture texture, int nColumns, int nRows, int frame, Point origin) {
		this( texture, nColumns, nRows, frame, origin.x, origin.y );
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

		Rectangle sourceRectangle = new Rectangle( frameW * column, frameH * row, frameW, frameH );
		Rectangle destinationRectangle = new Rectangle( position.x, position.y, frameW, frameH );

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
