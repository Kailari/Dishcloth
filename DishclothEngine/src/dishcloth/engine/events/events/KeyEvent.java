package dishcloth.engine.events.events;

import dishcloth.engine.events.Event;
import dishcloth.engine.io.input.InputHandler;

import static org.lwjgl.glfw.GLFW.*;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * KeyEvent.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 1.6.2015
 */

public class KeyEvent implements Event {

	public enum TriggerCause {
		PRESS,
		RELEASE,
		REPEAT
	}

	private int keyCode;
	private long window;
	private boolean lastState;
	private TriggerCause triggerCause;

	public TriggerCause getCause() {
		return triggerCause;
	}

	public boolean wasPressed() {
		return triggerCause == TriggerCause.PRESS;
	}

	public boolean wasRelease() {
		return triggerCause == TriggerCause.RELEASE;
	}

	public boolean wasRepeat() {
		return triggerCause == TriggerCause.REPEAT;
	}


	public KeyEvent(int keyCode) {
		this.keyCode = keyCode;
	}

	@Override
	public boolean triggerCondition() {
		int result = InputHandler.getKey( keyCode );

		boolean state = result == GLFW_PRESS;

		boolean press = (state && !lastState);
		boolean release = (!state && lastState);
		boolean repeat = (state && lastState);

		state = press || release || repeat;

		if (press) {
			triggerCause = TriggerCause.PRESS;
		}
		else if (release) {
			triggerCause = TriggerCause.RELEASE;
		}
		else if (repeat) {
			triggerCause = TriggerCause.REPEAT;
		}

		lastState = result == GLFW_PRESS;
		return state;
	}
}
