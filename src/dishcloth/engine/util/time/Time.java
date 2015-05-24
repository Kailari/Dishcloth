package dishcloth.engine.util.time;

import dishcloth.engine.AGame;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Time.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 24.5.2015
 */

public class Time {

	private static float simulationTimePool, timestep, currentTime, delta;
	private static int tick, fixedTick;

	public static void update(AGame.Timing timing) {
		simulationTimePool = timing.getSimulationTimePool();
		timestep = timing.getTimestep();
		currentTime = timing.getCurrentTime();
		delta = timing.getDelta();

		tick = timing.getTick();
		fixedTick = timing.getFixedTick();
	}

	public static float getSimulationTimePool() {
		return simulationTimePool;
	}

	public static float getTimestep() {
		return timestep;
	}

	public static float getCurrentTime() {
		return currentTime;
	}

	public static float getDelta() {
		return delta;
	}

	public static int getTick() {
		return tick;
	}

	public static int getFixedTick() {
		return fixedTick;
	}
}
