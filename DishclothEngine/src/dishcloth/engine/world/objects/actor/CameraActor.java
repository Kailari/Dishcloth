package dishcloth.engine.world.objects.actor;

import dishcloth.api.abstractionlayer.rendering.ICamera;
import dishcloth.api.abstractionlayer.rendering.IRenderer;
import dishcloth.api.util.geom.Point;
import dishcloth.api.util.math.Vector2;

/**
 * CameraActor.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 20.7.2015
 */

public class CameraActor extends AActor {

	private Vector2 velocity;
	private ICamera camera;

	public CameraActor(ICamera camera) {
		this.velocity = Vector2.zero();
		this.camera = camera;
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void fixedUpdate() {
		super.fixedUpdate();

		this.transform.translate( velocity.getX(), velocity.getY() );

		camera.setPosition( this.transform.getGlobalPosition( false ) );
	}

	public void setVelocity(float xVel, float yVel) {
		this.setVelocityX( xVel );
		this.setVelocityY( yVel );
	}

	public void setVelocity(Vector2 velocity) {
		setVelocity( velocity.getX(), velocity.getY() );
	}

	public void setVelocityX(float xVel) {
		this.velocity.setX( xVel );
	}

	public void setVelocityY(float yVel) {
		this.velocity.setY( yVel );
	}

	@Override
	public void render(IRenderer renderer) {
		super.render( renderer );
	}
}
