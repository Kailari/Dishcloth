package dishcloth.engine.util.math;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Matrix4.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 23.5.2015
 */

public class Matrix4 {

	public static final Matrix4 IDENTITY = new Matrix4( 1f, 0f, 0f, 0f,
	                                                    0f, 1f, 0f, 0f,
	                                                    0f, 0f, 1f, 0f,
	                                                    0f, 0f, 0f, 1f);

	public float[] elements;

	private Matrix4(float... elements) {
		this.elements = elements;
	}

	public Matrix4() {
		this.elements = new float[16];  // 4 x 4 = 16
	}

	public Matrix4(Matrix4 source) {
		this.elements = source.elements;
	}

	/**
	 * Don't ask me how this works. Matrix4 multiplication is something from the dark side of magimatics.
	 * PURE DARK MAGIC INFUSED WITH MATHEMATICSSSSSSS
	 */
	public Matrix4 multiply(Matrix4 other) {
		Matrix4 result = new Matrix4();
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				float sum = 0.0f;
				for (int e = 0; e < 4; e++) {
					sum += this.elements[x + e * 4] * other.elements[e + y * 4];
				}
				result.elements[x + y * 4] = sum;
			}
		}
		return result;
	}

	public void setElement(int i, float v) {
		elements[i] = v;
	}

	public float getElement(int i) {
		return elements[i];
	}

	public FloatBuffer toFloatBuffer() {
		FloatBuffer buff = BufferUtils.createFloatBuffer( 16 );
		buff.put( elements ).flip();
		return buff;
	}
}
