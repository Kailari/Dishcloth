package dishcloth.engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

// For NULL constant
import static org.lwjgl.system.MemoryUtil.*;

import dishcloth.engine.exception.GameInitializationException;
import org.lwjgl.Sys;
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

	private long windowID;
	private boolean windowShouldExit;

	private float simulationTimePool, timestep, currentTime, oldTime, delta;


	@Override
	public void run() {

		System.out.println("\n\n\n");
		System.out.println( "+------------------------------------------+" );
		System.out.println( "|               Running game...            |" );
		System.out.println( "+------------------------------------------+" );
		System.out.println();
		System.out.println( "[INIT] Initializing..." );
		doInitialize();

		System.out.println( "[INIT] Loading content..." );
		doLoadContent();

		float delta = 0.0f;
		timestep = 1f / 60f;

		System.out.println();
		System.out.println( "[RUNTIME] Entering main loop..." );
		while (glfwWindowShouldClose( windowID ) != GL_TRUE
				&& !windowShouldExit) {

			// Update time
			oldTime = currentTime;
			currentTime = (float) glfwGetTime();

			// Calculate delta
			delta = currentTime - oldTime;

			doUpdate();

			doFixedUpdate();

			doRender();
		}

		System.out.println( "[RUNTIME] Main loop ended..." );
		System.out.println();
		System.out.println( "[END] Unloading content..." );

		doUnloadContent();


		System.out.println( "[END] Shutting down..." );
		doShutdown();
	}


	@Override
	public final void doInitialize() {
		try {
			initWindow();

			// Call initialize
			initialize();
		} catch (GameInitializationException e) {

			System.err.println( "[INIT/ERR] Initialization failed!" );
			System.err.println( "    Message:    " + e.getMessage() );
			System.err.println( "    Stacktrace: " );
			System.err.println( "---------------------------------------------------------" );

			e.printStackTrace();
			System.exit( 1 );
		}

	}

	private final void initWindow() throws GameInitializationException {

		// Init GLFW - if glfwInit succeeds, it returns GL_TRUE
		if (glfwInit() != GL_TRUE) {
			throw new GameInitializationException( "glfwInit() failed!" );
		}


		// Initialize window

		// Set hint flags
		glfwWindowHint( GLFW_RESIZABLE, GL_FALSE );

		// Create window handle
		windowID = glfwCreateWindow( 800, 600, "Dishcloth", NULL, NULL );

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
		update( delta );
	}

	@Override
	public final void doFixedUpdate() {

		simulationTimePool += delta;

		while (simulationTimePool >= timestep) {
			// TODO: Store old state

			fixedUpdate();

			simulationTimePool -= timestep;
		}

		float t = simulationTimePool / timestep;
		// TODO: Interpolate world state using 't'
	}

	@Override
	public final void doRender() {
		// Call render()
		render( null );

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
}
