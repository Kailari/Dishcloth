package dishcloth.engine.rendering;

import dishcloth.api.abstractionlayer.rendering.ICamera;
import dishcloth.api.util.geom.Point;
import dishcloth.api.util.geom.Rectangle;
import dishcloth.api.util.math.Matrix4;
import dishcloth.api.util.math.MatrixUtility;
import dishcloth.api.util.memory.PointCache;
import dishcloth.api.util.memory.RectangleCache;
import dishcloth.engine.world.objects.actor.AActor;

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

public class OrthographicCamera extends AActor implements ICamera {

	public static OrthographicCamera instance;

	private Point position;
	private float angle;
	private float viewportW;
	private float viewportH;

	private Matrix4 projectionMatrix;
	private Matrix4 cameraTransformMatrix;

	public OrthographicCamera(float left, float right, float bottom, float top, float near, float far) {
		projectionMatrix = MatrixUtility.createOrthographicViewMatrix( left, right, bottom, top, near, far );

		position = PointCache.getPoint( 0f, 0f );
		angle = 0f;

		viewportW = Math.abs( left - right );
		viewportH = Math.abs( top - bottom );

		refreshTransformMatrix();

		// XXX: Find a better way to do this.
		if (instance == null) {
			instance = this;
		}
	}

	@Override
	public Rectangle getViewportRenderBounds() {
		return RectangleCache.getRectangle( position.getX() - viewportW / 2f,
		                                    position.getY() - viewportH / 2f,
		                                    viewportW,
		                                    viewportH );
	}

	private void refreshTransformMatrix() {
		cameraTransformMatrix =
				MatrixUtility.createRotationZ( angle )
						.multiply( MatrixUtility.createTranslation( position.getX(), position.getY(), 0f ) );
	}

	@Override
	public Matrix4 getProjectionMatrix() {
		return projectionMatrix;
	}

	@Override
	public Matrix4 getCameraTransformMatrix() {
		return cameraTransformMatrix;
	}

	@Override
	public Point getPosition() {
		return position;
	}

	@Override
	public void setPosition(Point position) {
		this.position = position;
		refreshTransformMatrix();
	}

	@Override
	public float getAngle() {
		return angle;
	}

	@Override
	public void setAngle(float angle) {
		this.angle = angle;

		refreshTransformMatrix();
	}
}
