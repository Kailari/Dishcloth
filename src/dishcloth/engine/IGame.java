package dishcloth.engine;

import dishcloth.engine.rendering.Renderer;

/***********************************************************************************************************************
 * IGame.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Interface for basic game-class
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 12.5.2015
 */


public interface IGame {

    /**
     * Do all your initialization here.
     */
    void Initialize();

    /**
     * Internal initialization. Calls Initialize()
     */
    void DoInitialize();

    /**
     * Load all content in this method.
     */
    void LoadContent();

    /**
     * Internal content loading method. Calls LoadContent()
     */
    void DoLoadContent();

    /**
     * Do your update logic here.
     *
     * @param delta     time passed since last update call.
     */
    void Update(float delta);

    /**
     * Internal Update method. Handles delta calculation, calls Update()
     *
     * @param delta     time passed since last update call.
     */
    void DoUpdate(float delta);

    /**
     * FPS-synchronized locked time-step update call. Do all your locked-time-step-requiring update logic here.
     *
     * @param fixedDelta    time passed since last (fixed) update call.
     */
    void FixedUpdate(float fixedDelta);

    /**
     * Internal fixed update call. Handles fps-synchronization and fixedDelta-calculation. Calls FixedUpdate()
     * @param fixedDelta
     */
    void DoFixedUpdate(float fixedDelta);

    /**
     * Do all your rendering here.
     */
    void Render(Renderer renderer);

    /**
     * Internal render call. Calls Render()
     */
    void DoRender();

    /**
     * Unload content here
     */
    void UnloadContent();

    /**
     * Internal UnloadContent call. Calls UnloadContent()
     */
    void DoUnloadContent();
}
