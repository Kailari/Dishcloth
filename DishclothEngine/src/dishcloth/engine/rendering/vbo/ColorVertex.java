package dishcloth.engine.rendering.vbo;

import dishcloth.engine.util.Color;
import dishcloth.engine.util.logger.Debug;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Vertex.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * A Vertex. Point in a shape, contains some special properties like texture coordinates
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 23.5.2015
 */

public class ColorVertex extends Vertex {

	public static final int SIZE_IN_BYTES = Vertex.SIZE_IN_BYTES + 4;

	public static final int COLOR_OFFSET = Vertex.SIZE_IN_BYTES;
	public static final int COLOR_TYPE = GL_UNSIGNED_BYTE;
	public static final int COLOR_SIZE = 4;
	public static final int COLOR_STRIDE = SIZE_IN_BYTES;

	// TODO: Add possibility to "extend" superclass vertexFormat (avoids having to re-register vertex ordering in subclasses)
	//  - note problem with changing stride! (which is always subclass SIZE_IN_BYTES)
	private static VertexFormat vertexFormat = new VertexFormat(
			new VertexFormat.VertexAttribute( Vertex.POSITION_SIZE, Vertex.POSITION_TYPE, SIZE_IN_BYTES, Vertex.POSITION_OFFSET, false ),
	        new VertexFormat.VertexAttribute( COLOR_SIZE, COLOR_TYPE, COLOR_STRIDE, COLOR_OFFSET, false )
	);

	private int color; // 1 int --> 4 bytes => 32 bits

	public ColorVertex(float x, float y, int color) {
		super(x, y);
		this.color = color;
	}

	public void setData(Object... data) {
		if (data.length < 3) {
			Debug.logErr( "Invalid data!", this );
			return;
		}

		super.setData( data[0], data[1] );

		// Find out if given data is a color
		if (data[2] instanceof Color) {
			this.color = ((Color) data[2]).toInteger();
		}
		// Assume it's an Integer
		else {
			this.color = (int) data[2];
		}
	}

	protected ByteBuffer toBytes(ByteBuffer buffer) {
		buffer = super.toBytes( buffer );
		buffer.putInt( color );

		return buffer;
	}

	@Override
	public VertexFormat getFormat() {
		return vertexFormat;
	}
}
