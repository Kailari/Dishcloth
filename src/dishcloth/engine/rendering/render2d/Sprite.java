package dishcloth.engine.rendering.render2d;


import dishcloth.engine.rendering.textures.Texture;
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

	public Texture getTexture() {
		return texture;
	}

	public int getTextureID() {
		return texture.getGLTexID();
	}

	public int getnColumns() {
		return nColumns;
	}

	public int getnRows() {
		return nRows;
	}

	public Sprite(Texture texture, int nColumns, int nRows, int frame) {
		this.texture = texture;
		this.nColumns = nColumns;
		this.nRows = nRows;
		this.frame = frame;
	}

	public void render(SpriteBatch spriteBatch, Point position, float angle) {

		float frameW = (float) texture.getWidth() / nColumns;
		float frameH = (float) texture.getHeight() / nRows;

		int row = (int)Math.floor( (float) frame / (float) nColumns );
		int column = frame % nColumns;

		Rectangle sourceRectangle = new Rectangle( frameW * column, frameH * row, frameW, frameH );
		Rectangle destinationRectangle = new Rectangle( position.x, position.y, frameW, frameH );

		spriteBatch.queue( texture, destinationRectangle, sourceRectangle, angle );
	}

	public void dispose() {
		texture.dispose();
	}
}
