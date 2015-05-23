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
	//    [  0,  1,  2,  3 ]
	//    [  4,  5,  6,  7 ]
	//    [  8,  9, 10, 11 ]
	//    [ 12, 13, 14, 15 ]

	public static Matrix4 createTranslation(float x, float y, float z) {
		Matrix4 translationMatrix = new Matrix4();

		translationMatrix.setElement( 0, 1f );
		translationMatrix.setElement( 5, 1f );
		translationMatrix.setElement( 10, 1f );
		translationMatrix.setElement( 15, 1f );

		translationMatrix.setElement( 3, x );
		translationMatrix.setElement( 3, y );
		translationMatrix.setElement( 3, z );

		return translationMatrix;
	}

	public static Matrix4 createRotationX(float degrees) {

		Matrix4 rotationMatrix = new Matrix4();

		float radians = (float) Math.toRadians( degrees );
		float cos = (float) Math.cos( radians );
		float sin = (float) Math.sin( radians );

		rotationMatrix.setElement( 0, 1f );
		rotationMatrix.setElement( 15, 1f );

		rotationMatrix.setElement( 5, cos );
		rotationMatrix.setElement( 6, -sin );
		rotationMatrix.setElement( 9, sin );
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
		rotationMatrix.setElement( 2, sin );
		rotationMatrix.setElement( 8, -sin );
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
		rotationMatrix.setElement( 1, -sin );
		rotationMatrix.setElement( 4, sin );
		rotationMatrix.setElement( 50, cos );

		return rotationMatrix;
	}
}
