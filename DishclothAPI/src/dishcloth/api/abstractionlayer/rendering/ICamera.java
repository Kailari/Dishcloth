package dishcloth.api.abstractionlayer.rendering;

import dishcloth.api.util.geom.Point;
import dishcloth.api.util.geom.Rectangle;
import dishcloth.api.util.math.Matrix4;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * ICamera.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 29.5.2015
 */

public interface ICamera {
	Matrix4 getProjectionMatrix();
	Matrix4 getCameraTransformMatrix();

	Point getPosition();
	void setPosition(Point position);

	float getAngle();
	void setAngle(float angle);

	Rectangle getViewportRenderBounds();
}
