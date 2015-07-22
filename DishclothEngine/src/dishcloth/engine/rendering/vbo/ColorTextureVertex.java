package dishcloth.engine.rendering.vbo;

import dishcloth.engine.util.Color;
import dishcloth.engine.util.logger.Debug;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;

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

public class ColorTextureVertex extends ColorVertex {

	public static final int SIZE_IN_BYTES = ColorVertex.SIZE_IN_BYTES + 8;

	public static final int UV_OFFSET = 12;              // 2 floats
	public static final int UV_TYPE = GL_FLOAT;
	public static final int UV_SIZE = 2;
	public static final int UV_STRIDE = SIZE_IN_BYTES;

	private float u, v; // 2 floats --> 8 bytes => 64 bits

	public ColorTextureVertex(float x, float y, int color, float u, float v) {
		super( x, y, color );
		this.u = u;
		this.v = v;
	}

	public static ByteBuffer getAsBytes(ColorTextureVertex[] vertices) {

		ByteBuffer buffer = BufferUtils.createByteBuffer( vertices.length * SIZE_IN_BYTES );

		for (ColorTextureVertex v : vertices) {
			if (v != null) {
				v.toBytes( buffer );
			}

		}

		buffer.flip();

		return buffer;
	}

	public void setData(Object... data) {
		if (data.length < 5) {
			Debug.logErr( "Invalid data!", this );
			return;
		}

		super.setData( data[0], data[1], data[2] );

		this.u = (float) data[3];
		this.v = (float) data[4];
	}

	protected ByteBuffer toBytes(ByteBuffer buffer) {
		buffer = super.toBytes( buffer );
		buffer.putFloat( u );
		buffer.putFloat( v );

		return buffer;
	}
}
