package dishcloth.engine.input;

import dishcloth.api.abstractionlayer.events.EventHandler;
import dishcloth.api.events.GameEvents;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;

/**
 * <b>InputHandler</b>
 * <p>
 * Provides easy access to user input. Wraps GLFW input processing and extends it with some additional polling.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 13.7.2015
 */

public class InputHandler {
	private static long activeWindow;
	private static HashMap<KeyCode, KeyState> keyStates;
	private static GLFWKeyCallback keyCallback;

	private InputHandler() {}

	@EventHandler
	public static void onGamePreInitializeEvent(GameEvents.GamePreInitializationEvent event) {
		attachToWindow( event.getGame().getWindowID() );
	}

	@EventHandler
	public static void onGameShutdownEvent(GameEvents.GameShutdownEvent event) {
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
		private State currentState;

		KeyState() {
			this.currentState = State.UP;
		}

		void updateState(int glfw_state) {
			if (glfw_state == GLFW_PRESS) {
				this.currentState = State.PRESS;
			} else if (glfw_state == GLFW_REPEAT) {
				this.currentState = State.DOWN;
			} else if (glfw_state == GLFW_RELEASE) {
				this.currentState = State.RELEASE;
			}
		}

		void refresh() {
			if (this.currentState == State.RELEASE) {
				this.currentState = State.UP;
			} else if (this.currentState == State.PRESS) {
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
