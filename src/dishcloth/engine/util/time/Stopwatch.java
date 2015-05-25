package dishcloth.engine.util.time;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Stopwatch.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 24.5.2015
 */

public class Stopwatch {
	private double startTime;

	public Stopwatch() { }

	public void start() {
		startTime = glfwGetTime();
	}

	/**
	 * Checks if time passed is greater than or equal to desired duration
	 * @param duration  Time to test
	 * @return  true if time passed >= duration
	 */
	public boolean hasTimePassed(double duration) {
		return elapsedTime() >= duration;
	}

	public double elapsedTime() {
		return glfwGetTime() - startTime;
	}
}
