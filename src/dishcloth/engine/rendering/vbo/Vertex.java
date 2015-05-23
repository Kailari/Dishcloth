package dishcloth.engine.rendering.vbo;

import dishcloth.engine.util.Color;
import dishcloth.engine.util.geom.Point;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_SHORT;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Vertex.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * A Vertex. Point in a shape, contains some special properties like color.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 23.5.2015
 */

public class Vertex {

	public static final int SIZE_IN_BYTES = 16; // 16 x 8 = 128

	public static final int POSITION_OFFSET = 0;
	public static final int POSITION_TYPE = GL_FLOAT;
	public static final int POSITION_SIZE = 2;
	public static final int POSITION_STRIDE = SIZE_IN_BYTES;

	public static final int UV_OFFSET = 8;              // 2 floats
	public static final int UV_TYPE = GL_UNSIGNED_SHORT;
	public static final int UV_SIZE = 2;
	public static final int UV_STRIDE = SIZE_IN_BYTES;

	public static final int COLOR_OFFSET = 12;           // 3 floats
	public static final int COLOR_TYPE = GL_UNSIGNED_BYTE;
	public static final int COLOR_SIZE = 4;
	public static final int COLOR_STRIDE = SIZE_IN_BYTES;


	float x, y;             // 2 x 32 bit -> 64 bit
	short u, v;             // 2 x 16 bit -> 32 bit -> total 96 bit
	byte r, g, b, a;        // 4 x  8 bit -> 32 bit -> total 128 bit

	public Vertex(float x, float y, short u, short v, byte r, byte g, byte b, byte a) {
		this.x = x;
		this.y = y;

		this.u = u;
		this.v = v;

		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Vertex(Point p, short u, short v, Color color) {
		this( (float) p.x, (float) p.y, u, v, color.r, color.g, color.b, color.a );
	}

	public static ByteBuffer getAsBytes(Vertex[] vertices) {

		ByteBuffer buff = BufferUtils.createByteBuffer( vertices.length * SIZE_IN_BYTES );

		for (Vertex v : vertices) {
			buff.putFloat( v.x );
			buff.putFloat( v.y );

			buff.putShort( v.u );
			buff.putShort( v.v );

			buff.put( v.r );
			buff.put( v.g );
			buff.put( v.b );
			buff.put( v.a );
		}

		buff.flip();

		return buff;
	}

	public void setColor(Color color) {
		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
		this.a = color.a;
	}

	public void setUV(short u, short v) {
		this.u = u;
		this.v = v;
	}
}
