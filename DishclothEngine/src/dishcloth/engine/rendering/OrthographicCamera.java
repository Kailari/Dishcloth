package dishcloth.engine.rendering;

import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.geom.Rectangle;
import dishcloth.engine.util.math.Matrix4;
import dishcloth.engine.util.math.MatrixUtility;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * OrthographicCamera.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 29.5.2015
 */

public class OrthographicCamera implements ICamera {

	public static OrthographicCamera instance;

	private Point position;
	private float angle;
	private float viewportW;
	private float viewportH;

	private Matrix4 projectionMatrix;
	private Matrix4 viewMatrix;

	public OrthographicCamera(float left, float right, float bottom, float top, float near, float far) {
		projectionMatrix = MatrixUtility.createOrthographicViewMatrix( left, right, bottom, top, near, far );

		position = new Point( 0f, 0f );
		angle = 0f;

		viewportW = Math.abs( left - right );
		viewportH = Math.abs( top - bottom );

		refreshViewMatrix();

		// XXX: Find a better way to do this.
		if (instance == null) {
			instance = this;
		}
	}

	public Rectangle getViewportBounds() {
		return new Rectangle( -position.x - viewportW / 2f,
		                      position.y - viewportH / 2f,
		                      viewportW,
		                      viewportH );
	}

	private void refreshViewMatrix() {
		viewMatrix = MatrixUtility.createRotationZ( angle )
				.multiply( MatrixUtility.createTranslation( position.x, position.y, 0f ) );
	}

	@Override
	public Matrix4 getProjectionMatrix() {
		return projectionMatrix;
	}

	@Override
	public Matrix4 getViewMatrix() {
		return viewMatrix;
	}

	@Override
	public Point getPosition() {
		return position;
	}

	@Override
	public void setPosition(Point position) {
		this.position = position;
		refreshViewMatrix();
	}

	@Override
	public float getAngle() {
		return angle;
	}

	@Override
	public void setAngle(float angle) {
		this.angle = angle;

		refreshViewMatrix();
	}
}
