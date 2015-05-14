package dishcloth.engine.world;

/**
 * ********************************************************************************************************************
 * IState.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 14.5.2015
 */

public interface IState<T extends AValueState> {
    T getOld();
    T getRender();
    T getSimulation();

    /**
     * Swaps current simulation state to old state.
     */
    void SwapState();

    /**
     * Interpolates render state values from old and simulation state values.
     * @param t    interpolation time
     */
    void UpdateRenderState(double t);
}
