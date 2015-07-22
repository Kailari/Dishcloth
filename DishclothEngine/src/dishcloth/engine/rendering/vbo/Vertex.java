package dishcloth.engine.rendering.vbo;

import dishcloth.engine.util.logger.Debug;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;

/**
 * Vertex.java
 * <p>
 * A Vertex. Point in a shape, contains some special properties like texture coordinates.<p>
 * When overriding this, don't forget to override the "getFormat()" -method!
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 23.5.2015
 */

public class Vertex {

	public static final int SIZE_IN_BYTES = 8; // 8 x 8 = 128
	public static final int POSITION_OFFSET = 0;
	public static final int POSITION_TYPE = GL_FLOAT;
	public static final int POSITION_SIZE = 2;
	public static final int POSITION_STRIDE = SIZE_IN_BYTES;

	private static final VertexFormat vertexFormat = new VertexFormat(
			new VertexFormat.VertexAttribute( POSITION_SIZE, POSITION_TYPE, POSITION_STRIDE, POSITION_OFFSET, false )
	);

	private float x, y; // 2 floats --> 8 bytes => 64 bits

	public Vertex(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public static ByteBuffer getAsBytes(Vertex[] vertices) {

		ByteBuffer buffer = BufferUtils.createByteBuffer( vertices.length * SIZE_IN_BYTES );

		for (Vertex v : vertices) {
			if (v != null) {
				v.toBytes( buffer );
			}

		}

		buffer.flip();

		return buffer;
	}

	public void setData(Object... data) {
		if (data.length < 2) {
			Debug.logErr( "Invalid data!", this );
			return;
		}

		this.x = (float) data[0];
		this.y = (float) data[1];
	}

	protected ByteBuffer toBytes(ByteBuffer buffer) {
		buffer.putFloat( x );
		buffer.putFloat( y );

		return buffer;
	}

	public VertexFormat getFormat() {
		return vertexFormat;
	}
}
