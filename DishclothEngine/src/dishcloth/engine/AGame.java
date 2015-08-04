package dishcloth.engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

// For NULL constant
import static org.lwjgl.system.MemoryUtil.*;

import dishcloth.engine.content.ContentManager;
import dishcloth.engine.content.ContentPipeline;
import dishcloth.engine.events.EventRegistry;
import dishcloth.engine.exception.GameInitializationException;
import dishcloth.engine.input.InputHandler;
import dishcloth.engine.rendering.ICamera;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.OrthographicCamera;
import dishcloth.engine.rendering.Renderer;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.util.time.Time;
import dishcloth.engine.world.block.BlockRegistry;
import dishcloth.engine.world.block.BlockTextureAtlas;
import dishcloth.engine.rendering.render2d.TerrainRenderer;
import org.lwjgl.opengl.GLContext;

import java.text.DecimalFormat;

/**
 * AGame.java
 * <p>
 * Abstract game class for handling initialization, update-tick, fps synchronization, etc.
 * This class just shouts "FUCK YOU" at Single Responsibility Principle, but just don't care about it, OK? :)
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 12.5.2015.
 */

// TODO: Some of AGame's responsibilities could be moved to other classes (ex. Window hardware initialization could be wrapped into its own class)
public abstract class AGame extends ADishclothObject implements IGame {

	protected int screenWidth, screenHeight;
	protected boolean doUpdateTime = true;
	private long windowID;
	private boolean windowShouldExit;
	private IRenderer renderer;
	private ICamera viewportCamera;
	private Timing timing;
	private ContentManager contentManager;

	protected AGame() {
		super( true );
	}

	protected void registerStaticEventListeners() {
		// TODO: Figure out some place where to store static class event listener registrations
		EventRegistry.registerStaticEventListener( InputHandler.class );
		EventRegistry.registerStaticEventListener( BlockTextureAtlas.class );
		EventRegistry.registerStaticEventListener( BlockRegistry.class );
		EventRegistry.registerStaticEventListener( TerrainRenderer.class );
		EventRegistry.registerStaticEventListener( ContentPipeline.class );
	}

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

		Debug.log( "Registering static EventListeners...", this );
		registerStaticEventListeners();

		Debug.log( "Initializing...", this );

		doInitialize();

		Debug.log( "Loading content...", this );
		doLoadContent();

		Debug.log( "Triggering post-initialize events", this );
		EventRegistry.fireEvent( new AGameEvents.GamePostInitializationEvent( this ) );

		timing = new Timing();

		timing.timestep = 1f / 60f;

		DecimalFormat fpsFormatter = new DecimalFormat( "0.00" );

		Debug.log( "", this );
		Debug.logNote( "Entering main loop...", this );
		int nFrames = 0;
		float fpsRecordStart = (float) glfwGetTime();
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

			// Calculate FPS
			nFrames++;
			if (timing.currentTime - fpsRecordStart >= 1.0f) {
				// Show fps in title
				String msToRender = fpsFormatter.format( 1000f / (float) nFrames );
				int nsToRender = Math.round( 1_000_000_000f / (float) nFrames );
				glfwSetWindowTitle( windowID,
				                    "Dishcloth - FPS: " + nFrames
						                    + ", Average time per frame: " + msToRender + " ms"
						                    + " (" + nsToRender + " ns)" );

				fpsRecordStart += 1.0f;
				nFrames = 0;
			}

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

			Debug.log( "Triggering pre-initialize events", this );
			EventRegistry.fireEvent( new AGameEvents.GamePreInitializationEvent( this ) );

			// Call initialize
			initialize();

			// TODO: Use reflection and URIClassloader -magic to find and initialize all mods

			// Register default ContentPipeline extensions
			// (Mods' extensions are handled via event that is fired inside this method)
			ContentPipeline.registerDefaultContentPipelineExtensions();

			// Initialize content pipeline
			ContentPipeline.initialize();
			this.contentManager = new ContentManager();

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

		// Create dishcloth.window handle
		windowID = glfwCreateWindow( screenWidth, screenHeight, "Dishcloth", NULL, NULL );

		// Validate windowID
		if (windowID == NULL) {
			throw new GameInitializationException( "glfwCreateWindow() failed!" );
		}


		// Make created window active

		glfwMakeContextCurrent( windowID );
		GLContext.createFromCurrent();
		glfwSwapInterval( 0 );
	}

	@Override
	public final void doLoadContent() {
		// Call loadContent()
		loadContent( this.contentManager );

		Debug.log( "Triggering content-initialization events", this );
		EventRegistry.fireEvent( new AGameEvents.GameContentLoadingEvent( this, this.contentManager ) );
	}

	@Override
	public final void doUpdate() {

		// XXX: Should this be an event? (OnUpdateTickEvent or something like that)
		InputHandler.refreshStates();

		// Poll glfw events (input etc.)
		glfwPollEvents();

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
		EventRegistry.fireEvent( new AGameEvents.GameContentDisposingEvent( this, contentManager ) );
		// Unload all non-contentManager-content
		unloadContent();

		// Dispose content manager
		contentManager.dispose();
	}

	@Override
	public final void doShutdown() {

		EventRegistry.fireEvent( new AGameEvents.GameShutdownEvent( this ) );

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
