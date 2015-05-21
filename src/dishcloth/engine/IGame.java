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
     */
    void doUpdate();

    /**
     * FPS-synchronized locked time-step update call. Do all your locked-time-step-requiring update logic here.
     *
     */
    void fixedUpdate();

    /**
     * Internal fixed update call. Handles fps-synchronization and fixedDelta-calculation. Calls FixedUpdate()
     */
    void doFixedUpdate();

    /**
     * Do all your rendering here.
     */
    void render(Renderer renderer);

    /**
     * Internal render call. Calls Render()
     */
    void doRender();

	/**
	 * Internal call for shutdown. Calls shutdown();
	 */
	void doShutdown();

	/**
	 * Shuts down the game.
	 */
	void shutdown();

    /**
     * Unload content here
     */
    void unloadContent();

    /**
     * Internal UnloadContent call. Calls UnloadContent()
     */
    void doUnloadContent();
}
