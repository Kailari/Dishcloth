package dishcloth.engine.world.actors;

import dishcloth.engine.util.Vector2;
import dishcloth.engine.world.AValueState;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * ActorValueState.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Contains all actor variables.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 14.5.2015
 */

public class ActorValueState extends AValueState {

    Vector2 position;

    public ActorValueState(ActorValueState vs) {
        super(vs);

        position = new Vector2(vs.position.x, vs.position.y);
    }

    public ActorValueState() {

    }

    @Override
    public AValueState createRenderState(AValueState old, double t) {
        ActorValueState vs = new ActorValueState(this);

        vs.position = Vector2.lerp(((ActorValueState)old).position, position, t);

        return vs;
    }
}
