package dishcloth.engine.rendering.vbo;

import dishcloth.engine.util.Color;
import dishcloth.engine.util.logger.Debug;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;

/**
 * TerrainVertex.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 23.7.2015
 */

public class TerrainVertex extends ColorTextureVertex {
	public static final int SIZE_IN_BYTES = ColorTextureVertex.SIZE_IN_BYTES + 8;

	public static final int OFFSET_OFFSET = 20;
	public static final int OFFSET_TYPE = GL_FLOAT;
	public static final int OFFSET_SIZE = 2;

	private static final VertexFormat vertexFormat = new VertexFormat(
			new VertexFormat.VertexAttribute( POSITION_SIZE, POSITION_TYPE, SIZE_IN_BYTES, POSITION_OFFSET, false ),
			new VertexFormat.VertexAttribute( COLOR_SIZE, COLOR_TYPE, SIZE_IN_BYTES, COLOR_OFFSET, true ),
			new VertexFormat.VertexAttribute( UV_SIZE, UV_TYPE, SIZE_IN_BYTES, UV_OFFSET, false ),
			new VertexFormat.VertexAttribute( OFFSET_SIZE, OFFSET_TYPE, SIZE_IN_BYTES, OFFSET_OFFSET, false )
	);

	private float offsX, offsY;

	public TerrainVertex() {
		this( 0f, 0f, Color.WHITE, 0f, 0f, 0f, 0f );
	}

	public TerrainVertex(float x, float y, Color color, float u, float v, float offsX, float offsY) {
		super( x, y, color, u, v );
		this.offsX = offsX;
		this.offsY = offsY;
	}

	@Override
	public int getSizeInBytes() {
		return SIZE_IN_BYTES;
	}

	@Override
	public VertexFormat getFormat() {
		return vertexFormat;
	}

	@Override
	protected ByteBuffer toBytes(ByteBuffer buffer) {
		buffer = super.toBytes( buffer );
		buffer.putFloat( offsX );
		buffer.putFloat( offsY );
		return buffer;
	}

	@Override
	public void setData(Object... data) {

		if (data.length < 7) {
			Debug.logErr( "Invalid data!", this );
		}

		super.setData( data );

		this.offsX = (float) data[5];
		this.offsY = (float) data[6];
	}
}
