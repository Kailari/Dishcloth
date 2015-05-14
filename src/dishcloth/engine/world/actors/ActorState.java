package dishcloth.engine.world.actors;

import dishcloth.engine.world.IState;

/**
 * ********************************************************************************************************************
 * ActorState.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * TODO: Description
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 14.5.2015
 */

public class ActorState implements IState<ActorValueState> {

    private ActorValueState valueState;
    private ActorValueState valueState_old;
    private ActorValueState valueState_render;

    public ActorState() {
        this.valueState = new ActorValueState();
        this.valueState_old = new ActorValueState();
        this.valueState_render = new ActorValueState();
    }

    @Override
    public ActorValueState getOld() {
        return valueState_old;
    }

    @Override
    public ActorValueState getRender() {
        return valueState_render;
    }

    @Override
    public ActorValueState getSimulation() {
        return valueState;
    }


    @Override
    public void SwapState() {
        valueState_old = new ActorValueState(valueState);
    }

    @Override
    public void UpdateRenderState(double t) {
        valueState_render = (ActorValueState)valueState.createRenderState(valueState_old, t);
    }
}
