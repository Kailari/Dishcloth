package dishcloth.engine.input;

import dishcloth.engine.AGameEvents;
import dishcloth.engine.events.EventHandler;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * InputHandler.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 13.7.2015
 */

public class InputHandler {
	private static long activeWindow;
	private static HashMap<KeyCode, KeyState> keyStates;
	private static GLFWKeyCallback keyCallback;

	private InputHandler() {}

	@EventHandler
	public static void onGamePreInitializeEvent(AGameEvents.GamePreInitializationEvent event) {
		attachToWindow( event.getGame().getWindowID() );
	}

	@EventHandler
	public static void onGameShutdownEvent(AGameEvents.GameShutdownEvent event) {
		detachFromWindow();
	}

	private static void attachToWindow(long windowID) {
		activeWindow = windowID;
		keyStates = new HashMap<>();

		keyCallback = GLFWKeyCallback( (window, key, scancode, action, mods) -> {
			keyStates.forEach( (keyCode, keyState) -> {
				if (key == keyCode.glfw_keyCode) {
					keyState.updateState( action );
				}
			} );
		} );

		glfwSetKeyCallback( activeWindow, keyCallback );
	}

	private static void detachFromWindow() {
		activeWindow = -1;
		keyCallback.release();
	}

	private static KeyState.State getKey(KeyCode keyCode) {
		if (!keyStates.containsKey( keyCode )) {
			registerKeyCallback( keyCode );
		}

		return keyStates.get( keyCode ).currentState;
	}

	private static void registerKeyCallback(KeyCode keyCode) {
		keyStates.put( keyCode, new KeyState() );
	}

	public static boolean getKeyPress(KeyCode keyCode) {
		return getKey( keyCode ) == KeyState.State.PRESS;
	}

	public static boolean getKeyDown(KeyCode keyCode) {
		return getKey( keyCode ) == KeyState.State.DOWN;
	}

	public static boolean getKeyReleased(KeyCode keyCode) {
		return getKey( keyCode ) == KeyState.State.RELEASE;
	}

	public static boolean getKeyUp(KeyCode keyCode) {
		return getKey( keyCode ) == KeyState.State.UP;
	}

	public static void refreshStates() {
		keyStates.values().forEach( KeyState::refresh );
	}

	private static class KeyState {
		private boolean previous, current;
		private State currentState;

		KeyState() {
			this.currentState = State.UP;
		}

		void updateState(int glfw_state) {

			// 'glfw_state' value is either GLFW_PRESS, GLFW_REPEAT or GLFW_RELEASE
			// - GLFW_PRESS means that key was pressed.
			// - GLFW_RELEASE means that key was released
			// - GLFW_REPEAT means that 'key was held until it repeated', whatever that means.
			// To simplify State-parsing, 'current' is set to true if key was pressed or down (*_PRESS or *_REPEAT)
			// and set to false if key was released (*_RELEASE).
			current = glfw_state == GLFW.GLFW_REPEAT;

			/*if (!previous && !current) {
				this.currentState = State.UP;
			} else if (previous && !current) {
				this.currentState = State.RELEASE;
			} else if (!previous && current) {
				this.currentState = State.PRESS;
			} else { //if (previous && current) {
				this.currentState = State.DOWN;
			}*/

			if (glfw_state == GLFW_PRESS) {
				this.currentState = State.PRESS;
			}
			else if (glfw_state == GLFW_REPEAT) {
				this.currentState = State.DOWN;
			}
			else if (glfw_state == GLFW_RELEASE) {
				this.currentState = State.RELEASE;
			}
		}

		void refresh() {
			if (this.currentState == State.RELEASE) {
				this.currentState = State.UP;
			}
			else if (this.currentState == State.PRESS) {
				this.currentState = State.DOWN;
			}
		}

		enum State {
			PRESS,
			RELEASE,
			DOWN,
			UP
		}
	}
}
