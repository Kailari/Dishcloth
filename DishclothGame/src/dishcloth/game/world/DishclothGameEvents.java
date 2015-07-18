package dishcloth.game.world;

import dishcloth.engine.events.IEventStorage;
import dishcloth.engine.events.events.KeyEvent;
import org.lwjgl.glfw.GLFW;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * DishclothGameEvents.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 13.7.2015
 */

public class DishclothGameEvents implements IEventStorage {
	public static final KeyEvent leftArrowKeyEvent = new KeyEvent( GLFW.GLFW_KEY_LEFT );
	public static final KeyEvent rightArrowKeyEvent = new KeyEvent( GLFW.GLFW_KEY_RIGHT );
	public static final KeyEvent upArrowKeyEvent = new KeyEvent( GLFW.GLFW_KEY_UP );
	public static final KeyEvent downArrowKeyEvent = new KeyEvent( GLFW.GLFW_KEY_DOWN );
}
