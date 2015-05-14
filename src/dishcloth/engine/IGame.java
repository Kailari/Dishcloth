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

    void run();

    /**
     * Do all your initialization here.
     */
    void initialize();

    /**
     * Internal initialization. Calls Initialize()
     */
    void doInitialize();

    /**
     * Load all content in this method.
     */
    void loadContent();

    /**
     * Internal content loading method. Calls LoadContent()
     */
    void doLoadContent();

    /**
     * Do your update logic here.
     *
     * @param delta     time passed since last update call.
     */
    void update(float delta);

    /**
     * Internal Update method. Handles delta calculation, calls Update()
     *
     * @param delta     time passed since last update call.
     */
    void doUpdate(float delta);

    /**
     * FPS-synchronized locked time-step update call. Do all your locked-time-step-requiring update logic here.
     *
     * @param fixedDelta    time passed since last (fixed) update call.
     */
    void fixedUpdate(float fixedDelta);

    /**
     * Internal fixed update call. Handles fps-synchronization and fixedDelta-calculation. Calls FixedUpdate()
     * @param fixedDelta
     */
    void doFixedUpdate(float fixedDelta);

    /**
     * Do all your rendering here.
     */
    void render(Renderer renderer);

    /**
     * Internal render call. Calls Render()
     */
    void doRender();

    /**
     * Unload content here
     */
    void unloadContent();

    /**
     * Internal UnloadContent call. Calls UnloadContent()
     */
    void doUnloadContent();
}
