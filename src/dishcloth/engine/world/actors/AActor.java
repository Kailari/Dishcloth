package dishcloth.engine.world.actors;

import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.IRenderable;
import dishcloth.engine.world.IUpdateable;

import java.util.ArrayList;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * AActor.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * An actor is something that has a position in the world and can be rendered.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 14.5.2015
 */

public abstract class AActor<T extends ActorState> implements IRenderable, IUpdateable {

    protected AActor<T> parent;
    protected ArrayList<AActor<T>> children;

    protected T state;

    public AActor(T state) {
        this.state = state;
        this.parent = null;
        this.children = new ArrayList<AActor<T>>();
    }

    @Override
    public final void DoUpdate(float delta) {
        Update(delta);
    }

    @Override
    public final void DoFixedUpdate(float fixedDelta) {
        state.SwapState();

        FixedUpdate(fixedDelta);
    }

    public final void DoRender(IRenderer renderer, double alpha) {
        state.UpdateRenderState(alpha);

        Render(renderer);
    }
}
