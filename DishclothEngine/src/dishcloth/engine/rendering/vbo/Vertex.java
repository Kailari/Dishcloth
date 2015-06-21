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
	public static final int UV_TYPE = GL_FLOAT;
	public static final int UV_SIZE = 2;
	public static final int UV_STRIDE = SIZE_IN_BYTES;


	float x, y, u, v; // 4 floats --> 128 bit

	public Vertex(float x, float y, float u, float v) {
		this.x = x;
		this.y = y;

		this.u = u;
		this.v = v;
	}

	public static ByteBuffer getAsBytes(Vertex[] vertices) {

		ByteBuffer buff = BufferUtils.createByteBuffer( vertices.length * SIZE_IN_BYTES );

		for (Vertex v : vertices) {
			buff.putFloat( v.x );
			buff.putFloat( v.y );

			buff.putFloat( v.u );
			buff.putFloat( v.v );
		}

		buff.flip();

		return buff;
	}

	public void setUV(float u, float v) {
		this.u = u;
		this.v = v;
	}
}
