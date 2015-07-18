package dishcloth.engine.io.input;

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

public class InputHandler {
	private InputHandler() {}

	private static long activeWindow;

	public static void attachToWindow(long windowID) {
		activeWindow = windowID;
	}

	public static int getKey(int keyCode) {
		return GLFW.glfwGetKey(activeWindow, keyCode);
	}
}
