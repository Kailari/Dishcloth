package dishcloth.api.util.math;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

/**
 * <b>Matrix4</b>
 * <p>
 * Simple Matrix4f implementation
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 23.5.2015
 */

public class Matrix4 {

	private static FloatBuffer buffer = BufferUtils.createFloatBuffer( 16 );

	// [ 00, 01, 02, 03 ]       [ 0, 4,  8, 12 ]
	// [ 10, 11, 12, 13 ] ---|\ [ 1, 5,  9, 13 ]
	// [ 20, 21, 22, 23 ] ---|/ [ 2, 6, 10, 14 ]
	// [ 30, 31, 32, 33 ]       [ 3, 7, 11, 15 ]

	private float[] elements;

	public Matrix4() {
		elements = new float[16];
	}

	public Matrix4(float... elements) {
		this.elements = elements;
	}

	private static float multiplyEntry(Matrix4 A, Matrix4 B, int i, int j) {
		float sum = 0f;
		for (int k = 0; k < 4; k++) {
			sum += A.getElement( i, k ) * B.getElement( k, j );
		}

		return sum;
	}

	public static Matrix4 identity() {
		return new Matrix4(
				1f, 0f, 0f, 0f,
				0f, 1f, 0f, 0f,
				0f, 0f, 1f, 0f,
				0f, 0f, 0f, 1f
		);
	}

	public void setElement(int n, int m, float value) {
		elements[getIndex( n, m )] = value;
	}

	public void setElement(int index, float value) {
		elements[index] = value;
	}

	public float getElement(int n, int m) {
		return elements[getIndex( n, m )];
	}

	public int getIndex(int n, int m) {
		return n + m * 4;
	}

	public Matrix4 multiply(Matrix4 other) {
		Matrix4 result = new Matrix4();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				result.setElement( i, j, multiplyEntry( this, other, i, j ) );
			}
		}

		return result;
	}

	public FloatBuffer toFloatBuffer() {
		buffer.clear();

		for (int i = 0; i < 16; i++) {
			buffer.put( elements[i] );
		}

		buffer.flip();

		return buffer;
	}
}
