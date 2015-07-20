package dishcloth.engine.world.objects.actor;

import dishcloth.engine.rendering.ICamera;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.math.Vector2;

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
	private Point realLocation;
	private float realRotation;
	private ICamera camera;

	public CameraActor(ICamera camera) {
		this.velocity = Vector2.zero();
		this.camera = camera;

		this.realLocation = transform.getLocation();
		this.realRotation = transform.getRotation();
	}

	@Override
	public void update() {
		super.update();

		transform.setLocation( realLocation );
		transform.setRotation( realRotation );
	}

	@Override
	public void fixedUpdate() {
		super.fixedUpdate();

		this.transform.translate( velocity.x, velocity.y );

		camera.setPosition( this.transform.getGlobalPosition( false ) );
	}

	public void setVelocity(float xVel, float yVel) {
		this.setVelocityX( xVel );
		this.setVelocityY( yVel );
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public void setVelocityX(float xVel) {
		this.velocity.x = xVel;
	}

	public void setVelocityY(float yVel) {
		this.velocity.y = yVel;
	}

	@Override
	public void render(IRenderer renderer) {
		super.render( renderer );

		realLocation = transform.getLocation();
		realRotation = transform.getRotation();

		transform.setLocation( getRenderPosition() );
		transform.setRotation( getRenderAngle() );
	}
}
