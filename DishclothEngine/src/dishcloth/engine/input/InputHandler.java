package dishcloth.engine.input;

import org.lwjgl.glfw.GLFW;

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

// TODO: Implement input polling
public class InputHandler {
	private InputHandler() {}

	private static long activeWindow;

	public static void attachToWindow(long windowID) {
		activeWindow = windowID;
	}

	public static int getKey(KeyCode keyCode) {
		return GLFW.glfwGetKey(activeWindow, keyCode.glfw_keyCode);
	}

	public static boolean getKeyDown(KeyCode keyCode) {
		return getKey( keyCode ) != GLFW.GLFW_RELEASE;
	}
}
