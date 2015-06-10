package dishcloth.engine.world;

import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.math.DishMath;
import dishcloth.engine.util.math.Vector2;

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

	private TransformState currentState, oldState;

	public void updateState() {

		oldState = currentState;

		currentState = new TransformState();
		currentState.angle = target.getGlobalRotation();
		currentState.location = target.getGlobalPosition( false );
	}

	public float getAngle(float t) {
		if (oldState == null) return currentState.angle;

		return DishMath.lerp( oldState.angle, currentState.angle, t );
	}

	public Point getPosition(float t) {
		if (oldState == null) return currentState.location;

		return Vector2.lerp( (Vector2) oldState.location, (Vector2) currentState.location, t );
	}

	private class TransformState {
		float angle;
		Point location;
	}
}
