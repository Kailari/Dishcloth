package dishcloth.engine.world;

import dishcloth.api.util.geom.Point;
import dishcloth.api.util.math.DishMath;
import dishcloth.api.util.math.Vector2;
import dishcloth.api.util.memory.PointCache;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * RenderTransform.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Handles position/rotation interpolation for rendering transforms.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 3.6.2015
 */

public class RenderTransform {

	private Transform target;

	private TransformState currentState, oldState, interpolatedState;

	public RenderTransform(Transform target) {
		this.target = target;

		this.currentState = new TransformState();
		this.oldState = new TransformState();
		this.interpolatedState = new TransformState();
	}

	public float getAngle() {
		return interpolatedState.angle;
	}

	public Point getPosition() {
		return interpolatedState.location;
	}

	public void updateState(float t) {

		oldState.angle = currentState.angle;
		oldState.location.setX( currentState.location.getX() );
		oldState.location.setY( currentState.location.getY() );

		// Cache the point
		PointCache.cachePoint( currentState.location );
		currentState.location = null;

		currentState.angle = target.getGlobalRotation();
		currentState.location = target.getGlobalPosition( false );

		interpolatedState.angle = getAngle( t );
		interpolatedState.location = getPosition( t );
	}

	private float getAngle(float t) {
		if (oldState == null) return currentState.angle;

		return DishMath.lerp( oldState.angle, currentState.angle, t );
	}

	private Point getPosition(float t) {
		if (oldState == null) return currentState.location;

		return Vector2.lerp( (Vector2) oldState.location, (Vector2) currentState.location, t );
	}

	private class TransformState {
		float angle;
		Point location;
	}
}
