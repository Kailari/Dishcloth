package dishcloth.engine.rendering.vao.vertex;


import dishcloth.api.util.Color;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;

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

public class ColorTextureVertex {

	public static final int SIZE_IN_BYTES = 20;

	public static final int POSITION_OFFSET = 0;
	public static final int POSITION_TYPE = GL_FLOAT;
	public static final int POSITION_SIZE = 2;

	public static final int COLOR_OFFSET = 8;
	public static final int COLOR_TYPE = GL_UNSIGNED_BYTE;
	public static final int COLOR_SIZE = 4;

	public static final int UV_OFFSET = 12;
	public static final int UV_TYPE = GL_FLOAT;
	public static final int UV_SIZE = 2;

	private static final VertexFormat vertexFormat = new VertexFormat(
			new VertexFormat.VertexAttribute( POSITION_SIZE, POSITION_TYPE, SIZE_IN_BYTES, POSITION_OFFSET, false ),
			new VertexFormat.VertexAttribute( COLOR_SIZE, COLOR_TYPE, SIZE_IN_BYTES, COLOR_OFFSET, true ),
			new VertexFormat.VertexAttribute( UV_SIZE, UV_TYPE, SIZE_IN_BYTES, UV_OFFSET, false )
	);

	private float x;
	private float y; // 2 floats --> 8 bytes => 64 bits
	private float u;
	private float v; // 2 floats --> 8 bytes => 64 bits
	private int color;  // 1 int --> 4 bytes => 32 bits

	public ColorTextureVertex() {
		this( 0f, 0f, 0, 0f, 0f );
	}

	public ColorTextureVertex(float x, float y, Color color, float u, float v) {
		this( x, y, color.toInteger(), u, v );
	}

	public ColorTextureVertex(float x, float y, int color, float u, float v) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.u = u;
		this.v = v;
	}

	public static ByteBuffer getAsBytes(ByteBuffer buffer, ColorTextureVertex[] vertices) {

		for (ColorTextureVertex v : vertices) {
			if (v != null) {
				buffer = v.toBytes( buffer );
			}
		}

		buffer.flip();

		return buffer;
	}

	public void setData(float x, float y, Color color, float u, float v) {
		setData( x, y, color.toInteger(), u, v );
	}

	public void setData(float x, float y, int color, float u, float v) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.u = u;
		this.v = v;
	}

	protected ByteBuffer toBytes(ByteBuffer buffer) {
		buffer.putFloat( x );
		buffer.putFloat( y );

		buffer.putInt( color );

		buffer.putFloat( u );
		buffer.putFloat( v );

		return buffer;
	}

	public static VertexFormat getFormat() {
		return vertexFormat;
	}

	public int getSizeInBytes() {
		return SIZE_IN_BYTES;
	}
}
