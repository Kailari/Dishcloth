package dishcloth.engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

// For NULL constant
import static org.lwjgl.system.MemoryUtil.*;

import dishcloth.engine.exception.GameInitializationException;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.util.math.Matrix4;
import dishcloth.engine.util.time.Time;
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

	// XXX: Matrices are just temporarily here
	public static Matrix4 projectionMatrix;
	public static Matrix4 viewMatrix;
	protected int screenWidth, screenHeight;
	private long windowID;
	private boolean windowShouldExit;
	private IRenderer renderer;
	private Timing timing;

	protected boolean doUpdateTime = true;

	@Override
	public final void run() {

		System.out.println( "\n\n\n" );
		System.out.println( "||XX||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||XX||" );
		System.out.println( "||==|+----------------------------------------------------------------------------------------+|==||" );
		System.out.println( "||==||                                      Running game...                                   ||==||" );
		System.out.println( "||==|+----------------------------------------------------------------------------------------+|==||" );
		System.out.println( "||XX||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||XX||" );
		System.out.println( "\n" );

		Debug.log( "Initializing...", this );

		doInitialize();
		Debug.logOK( "Initializing successful!", this );

		Debug.log( "Loading content...", this );
		doLoadContent();
		Debug.logOK( "Content loading successful!", this );

		timing = new Timing();

		timing.timestep = 1f / 60f;

		System.out.println( "\n" );
		Debug.logNote( "Entering main loop...", this );
		while (glfwWindowShouldClose( windowID ) != GL_TRUE
				&& !windowShouldExit) {

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
				Time.update(timing);
			}
		}

		Debug.logNote( "Main loop ended...", this );
		System.out.println();
		Debug.logWarn( "Unloading content...", this );

		doUnloadContent();


		Debug.logWarn( "Shutting down...", this );
		doShutdown();

		Debug.logOK( "I'll be back...", this );
	}

	@Override
	public final void doInitialize() {
		try {
			initWindow();

			renderer = createRenderer();

			// Call initialize
			initialize();
		} catch (GameInitializationException e) {

			Debug.logException( e, this );
			System.exit( 1 );
		}

	}

	protected abstract IRenderer createRenderer();

	private final void initWindow() throws GameInitializationException {

		// Init GLFW - if glfwInit succeeds, it returns GL_TRUE
		if (glfwInit() != GL_TRUE) {
			throw new GameInitializationException( "glfwInit() failed!" );
		}

		screenWidth = 800;
		screenHeight = 600;

		// Initialize window

		// Set hint flags
		glfwWindowHint( GLFW_RESIZABLE, GL_FALSE );

		// Create window handle
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

		// Call update
		update( timing.delta );
	}

	@Override
	public final void doFixedUpdate() {

		timing.simulationTimePool += timing.delta;

		while (timing.simulationTimePool >= timing.timestep) {
			// TODO: Store old state

			timing.fixedTick++;
			timing.simulationTimePool -= timing.timestep;

			if (doUpdateTime) {
				Time.update(timing);
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
