package dishcloth.engine.io.input.events;

import dishcloth.engine.io.input.InputEvent;

import static org.lwjgl.glfw.GLFW.*;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * KeyInputEvent.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 1.6.2015
 */

public class KeyInputEvent implements InputEvent {

	int keyCode;
	long window;
	boolean triggerOnPress, triggerOnRelease, triggerOnRepeat, lastState;

	public KeyInputEvent(long window, int keyCode,
	                     boolean triggerOnPress, boolean triggerOnRelease, boolean triggerOnRepeat) {
		this.keyCode = keyCode;
		this.window = window;
		this.triggerOnPress = triggerOnPress;
		this.triggerOnRelease = triggerOnRelease;
		this.triggerOnRepeat = triggerOnRepeat;
	}

	@Override
	public boolean triggerCondition() {
		int result = glfwGetKey( window, keyCode );

		boolean state = ((triggerOnPress && result == GLFW_PRESS)
				|| (triggerOnRelease && result == GLFW_RELEASE));

		// lastState == true if last state was GLFW_PRESS
		state = (state || (triggerOnRepeat && result == GLFW_REPEAT && lastState));

		lastState = (result == GLFW_PRESS);

		return state;
	}
}
