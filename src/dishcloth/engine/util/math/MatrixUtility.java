package dishcloth.engine.util.math;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * MatrixUtility.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 23.5.2015
 */

public class MatrixUtility {

	// Matrix indices:
	//    [ 0, 4,  8, 12 ]
	//    [ 1, 5,  9, 13 ]
	//    [ 2, 6, 10, 14 ]
	//    [ 3, 7, 11, 15 ]

	public static Matrix4 createOrthographicViewMatrix(float left, float right,
	                                                   float bottom, float top,
	                                                   float near, float far) {
		Matrix4 view = new Matrix4();

		view.setElement( 0, (2f / (right - left)) );
		view.setElement( 5, (2f / (top - bottom)) );
		view.setElement( 10, (2f / (near - far)) );

		view.setElement( 12, ((left + right) / (left - right)) );
		view.setElement( 13, ((bottom + top) / (bottom - top)) );
		view.setElement( 14, ((far + near) / (far - near)) );

		view.setElement( 15, 1f );

		return view;
	}

	public static Matrix4 createTranslation(float x, float y, float z) {
		Matrix4 translationMatrix = new Matrix4();

		translationMatrix.setElement( 0, 1f );
		translationMatrix.setElement( 5, 1f );
		translationMatrix.setElement( 10, 1f );
		translationMatrix.setElement( 15, 1f );

		translationMatrix.setElement( 12, x );
		translationMatrix.setElement( 13, y );
		translationMatrix.setElement( 14, z );

		return translationMatrix;
	}

	public static Matrix4 createRotationX(float degrees) {

		Matrix4 rotationMatrix = new Matrix4();

		float radians = (float) Math.toRadians( degrees );
		float cos = (float) Math.cos( radians );
		float sin = (float) Math.sin( radians );

		rotationMatrix.setElement( 0, 1f );
		rotationMatrix.setElement( 15, 1f );

		rotationMatrix.setElement( 1, cos );
		rotationMatrix.setElement( 5, -sin );
		rotationMatrix.setElement( 6, sin );
		rotationMatrix.setElement( 10, cos );

		return rotationMatrix;
	}

	public static Matrix4 createRotationY(float degrees) {

		Matrix4 rotationMatrix = new Matrix4();

		float radians = (float) Math.toRadians( degrees );
		float cos = (float) Math.cos( radians );
		float sin = (float) Math.sin( radians );

		rotationMatrix.setElement( 5, 1f );
		rotationMatrix.setElement( 15, 1f );

		rotationMatrix.setElement( 0, cos );
		rotationMatrix.setElement( 8, sin );
		rotationMatrix.setElement( 2, -sin );
		rotationMatrix.setElement( 10, cos );

		return rotationMatrix;
	}

	public static Matrix4 createRotationZ(float degrees) {

		Matrix4 rotationMatrix = new Matrix4();

		float radians = (float) Math.toRadians( degrees );
		float cos = (float) Math.cos( radians );
		float sin = (float) Math.sin( radians );

		rotationMatrix.setElement( 10, 1f );
		rotationMatrix.setElement( 15, 1f );

		rotationMatrix.setElement( 0, cos );
		rotationMatrix.setElement( 4, -sin );
		rotationMatrix.setElement( 1, sin );
		rotationMatrix.setElement( 5, cos );

		return rotationMatrix;
	}

	public static Matrix4 createScaling(float x, float y, float z) {
		Matrix4 scalingMatrix = new Matrix4();

		scalingMatrix.setElement( 0, x );
		scalingMatrix.setElement( 5, y );
		scalingMatrix.setElement( 10, z );
		scalingMatrix.setElement( 15, 1 );

		return scalingMatrix;
	}
}
