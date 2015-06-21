package dishcloth.engine.util.time;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Timer.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 24.5.2015
 */

public class Timer extends Stopwatch {
	private double duration;
	private boolean paused;

	public Timer(double duration) {
		this.duration = duration;
	}

	public Timer() { }

	public void addTime(double seconds) {
		duration += seconds;
	}

	@Override
	public boolean hasTimePassed(double duration) {
		return !paused && super.hasTimePassed( duration );
	}

	public boolean hasCompleted() {
		return hasTimePassed( duration );
	}

	@Override
	public void start() {
		resume();
	}

	public void pause() {
		paused = true;
		duration -= elapsedTime();
	}

	public void resume() {
		paused = false;
		super.start();
	}
}
