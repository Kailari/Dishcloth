package dishcloth.engine.world.objects;

import dishcloth.engine.rendering.IRenderable;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.time.Time;
import dishcloth.engine.world.IUpdatable;
import dishcloth.engine.world.RenderTransform;
import dishcloth.engine.world.Transform;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * AWorldObject.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * A <b>thing</b> in the world that has position, may have update logic, and can be rendered if needed.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 19.7.2015
 */

public abstract class AWorldObject implements IRenderable, IUpdatable {
	private final RenderTransform renderTransform;
	protected final Transform transform;

	public Transform getTransform() {
		return transform;
	}

	protected final float getRenderAngle() {
		return this.renderTransform.getAngle();
	}

	protected final Point getRenderPosition() {
		return this.renderTransform.getPosition();
	}

	public AWorldObject() {
		this.transform = new Transform();
		this.renderTransform = new RenderTransform( this.transform );
	}

	@Override
	public void update() {
		// Nothing :3
	}

	@Override
	public void fixedUpdate() {
		// Nothing :3
	}

	@Override
	public void render(IRenderer renderer) {
		renderTransform.updateState( Time.getSimulationTimePool() / Time.getTimestep() );
	}
}
