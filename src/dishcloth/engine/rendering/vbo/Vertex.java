package dishcloth.engine.rendering.vbo;

import dishcloth.engine.util.Color;
import dishcloth.engine.util.geom.Point;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_BYTE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;

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
	public static final int POSITION_STRIDE = SIZE_IN_BYTES;             // 1 float + 4 bytes = 8 bytes

	public static final int BRIGHTNESS_OFFSET = 8;      // 2 floats
	public static final int BRIGHTNESS_TYPE = GL_FLOAT;
	public static final int BRIGHTNESS_SIZE = 1;
	public static final int BRIGHTNESS_STRIDE = SIZE_IN_BYTES;     // 4 bytes + 2 floats = 12 bytes

	public static final int COLOR_OFFSET = 12;           // 3 floats
	public static final int COLOR_TYPE = GL_UNSIGNED_BYTE;
	public static final int COLOR_SIZE = 4;
	public static final int COLOR_STRIDE = SIZE_IN_BYTES;          // 3 floats = 12 bytes


	float x, y, brightness; // 3 x 32bit -> 96 bit
	byte r, g, b, a;        // 4 x 8 bit -> 32 bit -> total 128 bit

	public Vertex(float x, float y, float brightness, byte r, byte g, byte b, byte a) {
		this.x = x;
		this.y = y;

		this.brightness = brightness;

		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Vertex(Point p, float brightness, Color color) {
		this.x = (float) p.x;
		this.y = (float) p.y;

		this.brightness = brightness;

		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
		this.a = color.a;
	}

	public static ByteBuffer getAsBytes(Vertex[] vertices) {

		ByteBuffer buff = BufferUtils.createByteBuffer( vertices.length * SIZE_IN_BYTES );

		for (Vertex v : vertices) {
			buff.putFloat( v.x );
			buff.putFloat( v.y );
			buff.putFloat( v.brightness );

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

	public void setBrightness(float brightness) {
		this.brightness = brightness;
	}
}
