package dishcloth.engine.input.controllers;

import dishcloth.engine.input.InputHandler;
import dishcloth.engine.input.KeyCode;
import dishcloth.engine.world.IUpdatable;
import dishcloth.engine.world.objects.actor.CameraActor;
import org.lwjgl.glfw.GLFW;

/**
 * CameraController.java
 * <p>
 * A simple use-arrow-keys-to-move-camera-thingy.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 20.7.2015
 */

// TODO: Create IController interface (It should have some way to link to IControllable Actors and should provide update-methods)
public class CameraController implements IUpdatable {

	public static final float CAMERA_SPEED = 10f;

	private CameraActor target;

	public void setActiveCamera(CameraActor target) {
		this.target = target;
	}

	@Override
	public void update() {
		float inputLeft = (InputHandler.getKeyDown( KeyCode.KEY_LEFT ) ? 1f : 0f);
		float inputRight = (InputHandler.getKeyDown( KeyCode.KEY_RIGHT ) ? 1f : 0f);

		float inputUp = (InputHandler.getKeyDown( KeyCode.KEY_UP ) ? 1f : 0f);
		float inputDown = (InputHandler.getKeyDown( KeyCode.KEY_DOWN ) ? 1f : 0f);

		target.setVelocityX( (inputRight - inputLeft) * CAMERA_SPEED );
		target.setVelocityY( (inputUp - inputDown) * CAMERA_SPEED );
	}

	@Override
	public void fixedUpdate() {
	}
}
