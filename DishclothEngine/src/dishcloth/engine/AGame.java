package dishcloth.engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

// For NULL constant
import static org.lwjgl.system.MemoryUtil.*;

import dishcloth.engine.events.events.KeyEvent;
import dishcloth.engine.exception.GameInitializationException;
import dishcloth.engine.events.EventHandler;
import dishcloth.engine.events.EventAction;
import dishcloth.engine.io.input.InputHandler;
import dishcloth.engine.rendering.ICamera;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.OrthographicCamera;
import dishcloth.engine.rendering.Renderer;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.util.time.Time;
import dishcloth.engine.world.block.BlockRegistry;
import dishcloth.engine.world.level.TerrainRenderer;
import org.lwjgl.opengl.GLContext;

/**
 * ********************************************************************************************************************
 * AGame.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Abstract game class for handling initialization, update-tick, fps synchronization, etc.
 * (This class just shouts "FUCK YOU" at Single Responsibility Principle, but just don't care about it)
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 12.5.2015.
 */

public abstract class AGame implements IGame {

	protected int screenWidth, screenHeight;
	protected boolean doUpdateTime = true;
	private long windowID;
	private boolean windowShouldExit;
	private IRenderer renderer;
	private ICamera viewportCamera;
	private Timing timing;

	private EventAction<KeyEvent> forceExitAction = eventTrigger -> windowShouldExit = eventTrigger.wasPressed();

	public long getWindowID() {
		return windowID;
	}

	public ICamera getViewportCamera() {
		return viewportCamera;
	}

	@Override
	public final void run() {

		Debug.log( "", this );
		Debug.log( "||XX||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||XX||", this );
		Debug.log( "||==|+----------------------------------------------------------------------------------------+|==||", this );
		Debug.log( "||==||                                      Running game...                                   ||==||", this );
		Debug.log( "||==|+----------------------------------------------------------------------------------------+|==||", this );
		Debug.log( "||XX||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||XX||", this );
		Debug.log( "", this );

		Debug.log( "Initializing...", this );

		doInitialize();
		Debug.logOK( "Initializing successful!", this );

		Debug.log( "Loading content...", this );
		doLoadContent();
		Debug.logOK( "Content loading successful!", this );

		// TODO: Figure out where these should be kept
		BlockRegistry.doBlockRegistration( "dummy" );
		TerrainRenderer.initialize( this );


		timing = new Timing();

		timing.timestep = 1f / 60f;

		Debug.log( "", this );
		Debug.logNote( "Entering main loop...", this );
		while (glfwWindowShouldClose( windowID ) != GL_TRUE
				&& !windowShouldExit) {

			assert !windowShouldExit;

			timing.tick++;

			// Update time
			float oldTime = timing.currentTime;
			timing.currentTime = (float) glfwGetTime();

			// Calculate delta
			timing.delta = timing.currentTime - oldTime;

			doUpdate();

			doFixedUpdate();

			doRender();

			if (doUpdateTime) {
				Time.update( timing );
			}
		}

		Debug.logNote( "Main loop ended...", this );
		Debug.log( "", this );
		Debug.logWarn( "Unloading content...", this );

		doUnloadContent();


		Debug.logWarn( "Shutting down...", this );
		doShutdown();
	}

	@Override
	public final void doInitialize() {
		try {

			initHardware();

			// Attach inputHandler
			InputHandler.attachToWindow( windowID );

			// Register events
			EventHandler.registerEvents( AGameEvents.class );

			// Bind actions
			EventHandler.bindAction( AGameEvents.forceExitEvent, forceExitAction );


			// Call initialize
			initialize();

		} catch (GameInitializationException e) {

			Debug.logException( e, this );
			System.exit( 1 );
		}

	}

	private void initHardware() throws GameInitializationException {
		// Initialize window
		initWindow();

		// Create renderer
		renderer = new Renderer();

		// Create camera
		float halfW = screenWidth / 2f;
		float halfH = screenHeight / 2f;
		viewportCamera = new OrthographicCamera( -halfW, halfW,
		                                         -halfH, halfH,
		                                         1.0f, -1.0f );

		/*viewportCamera = new OrthographicCamera( 0f, screenWidth,
		                                         0f, screenHeight,
		                                         -1.0f, 1.0f );*/
	}


	private void initWindow() throws GameInitializationException {

		// Init GLFW - if glfwInit succeeds, it returns GL_TRUE
		if (glfwInit() != GL_TRUE) {
			throw new GameInitializationException( "glfwInit() failed!" );
		}

		screenWidth = 800;
		screenHeight = 600;

		// Initialize window

		// Set hint flags
		glfwWindowHint( GLFW_RESIZABLE, GL_FALSE );

		// Create randomGen.window handle
		windowID = glfwCreateWindow( screenWidth, screenHeight, "Dishcloth", NULL, NULL );

		// Validate windowID
		if (windowID == NULL) {
			throw new GameInitializationException( "glfwCreateWindow() failed!" );
		}

		// Make created window active

		glfwMakeContextCurrent( windowID );
		GLContext.createFromCurrent();
		glfwSwapInterval( 1 );
	}

	@Override
	public final void doLoadContent() {
		// Call loadContent()
		loadContent();
	}

	@Override
	public final void doUpdate() {

		// Poll glfw events (input etc.)
		glfwPollEvents();

		// Update EventHandler
		EventHandler.updateEvents();

		// Call update
		update( timing.delta );
	}

	@Override
	public final void doFixedUpdate() {

		timing.simulationTimePool += timing.delta;

		while (timing.simulationTimePool >= timing.timestep) {

			timing.fixedTick++;
			timing.simulationTimePool -= timing.timestep;

			if (doUpdateTime) {
				Time.update( timing );
			}

			fixedUpdate();
		}

		float t = timing.simulationTimePool / timing.timestep;
		// TODO: Interpolate world state using 't'
	}

	@Override
	public final void doRender() {
		glClear( GL_COLOR_BUFFER_BIT );

		// Call render()
		render( renderer );

		// Swap buffers
		glfwSwapBuffers( windowID );
	}

	@Override
	public final void doUnloadContent() {
		unloadContent();
	}

	@Override
	public final void doShutdown() {

		// Call shutdown()
		shutdown();


		// Destroy window
		glfwDestroyWindow( windowID );

		// Terminate glfw
		glfwTerminate();
	}

	public class Timing {
		private float simulationTimePool, timestep, currentTime, delta;
		private int tick, fixedTick;

		public float getSimulationTimePool() {
			return simulationTimePool;
		}

		public float getTimestep() {
			return timestep;
		}

		public float getCurrentTime() {
			return currentTime;
		}

		public float getDelta() {
			return delta;
		}

		public int getTick() {
			return tick;
		}

		public int getFixedTick() {
			return fixedTick;
		}
	}
}
