package dishcloth.engine.rendering.vbo;

import dishcloth.engine.util.Color;
import dishcloth.engine.util.logger.Debug;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * ColorTextureVertex.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * A Vertex. Point in a shape, contains some special properties like texture coordinates
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 23.5.2015
 */

public class ColorTextureVertex extends ColorVertex {

	public static final int SIZE_IN_BYTES = ColorVertex.SIZE_IN_BYTES + 8;

	public static final int UV_OFFSET = 12;              // 2 floats
	public static final int UV_TYPE = GL_FLOAT;
	public static final int UV_SIZE = 2;

	private static final VertexFormat vertexFormat = new VertexFormat(
			new VertexFormat.VertexAttribute( POSITION_SIZE, POSITION_TYPE, SIZE_IN_BYTES, POSITION_OFFSET, false ),
			new VertexFormat.VertexAttribute( COLOR_SIZE, COLOR_TYPE, SIZE_IN_BYTES, COLOR_OFFSET, true ),
			new VertexFormat.VertexAttribute( UV_SIZE, UV_TYPE, SIZE_IN_BYTES, UV_OFFSET, false )
	);

	private float u, v; // 2 floats --> 8 bytes => 64 bits

	public ColorTextureVertex() {
		this( 0f, 0f, 0, 0f, 0f );
	}

	public ColorTextureVertex(float x, float y, int color, float u, float v) {
		super( x, y, color );
		this.u = u;
		this.v = v;
	}

	public ColorTextureVertex(float x, float y, Color color, float u, float v) {
		this( x, y, color.toInteger(), u, v );
	}

	@Override
	public void setData(Object... data) {
		if (data.length < 5) {
			Debug.logErr( "Invalid data!", this );
			return;
		}

		super.setData( data[0], data[1], data[2] );

		this.u = (float) data[3];
		this.v = (float) data[4];
	}

	@Override
	protected ByteBuffer toBytes(ByteBuffer buffer) {
		buffer = super.toBytes( buffer );
		buffer.putFloat( u );
		buffer.putFloat( v );

		return buffer;
	}

	@Override
	public VertexFormat getFormat() {
		return vertexFormat;
	}

	@Override
	public int getSizeInBytes() {
		return SIZE_IN_BYTES;
	}
}
