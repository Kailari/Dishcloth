package dishcloth.engine.events;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * EventAction.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 1.6.2015
 */

public interface EventAction<T extends Event> {
	/**
	 * This method is ran after the bound event triggers.
	 * @param eventTrigger    event that triggered this action
	 */
	void trigger(T eventTrigger);
}
