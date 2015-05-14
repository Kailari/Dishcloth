package dishcloth.engine.world;

/**
 * ********************************************************************************************************************
 * AValueState.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 14.5.2015
 */
public abstract class AValueState {

    public AValueState() {
        // Nothing.
    }

    protected AValueState(AValueState vs) {
        // Copy variables
    }

    /**
     * ALL NUMERIC FLOATING POINT VARIABLES THAT AFFECT RENDERING SHOULD BE INTERPOLATED HERE TO AVOID JITTER
     * <p>
     *     for example, actor movement (position), smoothly changing render size, smoothly changing color, etc.
     * @param old   old value state
     * @param t     interpolation 'time'
     * @return      interpolated state for rendering
     */
    public abstract AValueState createRenderState(AValueState old, double t);
}
