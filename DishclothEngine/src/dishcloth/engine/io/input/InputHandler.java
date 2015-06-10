package dishcloth.engine.io.input;

import dishcloth.engine.exception.EventException;
import dishcloth.engine.util.logger.Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * InputHandler.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Stores and fires triggers (events) and logic tied to them (actions). An event may contain multiple bound actions.
 * ie. an event that fires when a key is pressed, may contain animation action and player movement action (or something
 * like that.)
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 29.5.2015
 */

public class InputHandler {

	private static List<InputEvent> events = new ArrayList<>();
	private static HashMap<InputEvent, List<InputAction>> boundActions = new HashMap<>();

	private static HashMap<String, InputEvent> eventLookupTable = new HashMap<>();

	@SuppressWarnings("unchecked")
	public static void updateEvents() {
		// Java 8 porn. Streams and method references.

		// Streams:
		//      for (a : b)
		//   becomes:
		//      a.stream().//.forEach()
		//
		// Method references (shorter way to write certain lambdas):
		//      event -> event.triggerCondition()
		//   becomes:
		//      InputEvent::triggerCondition

		events.stream().filter( InputEvent::triggerCondition ).forEach( event -> {
			for (InputAction action : boundActions.get( event )) {
				// NOTE: This is unchecked but as main binding method forces using compatible types,
				// it should not be a problem
				action.trigger( event );
			}
		} );
	}

	public static void registerEvent(InputEvent event, String lookupName) {
		if (!events.contains( event )
				&& !boundActions.containsKey( event )
				&& !eventLookupTable.containsKey( lookupName )) {

			events.add( event );
			eventLookupTable.put( lookupName, event );

			boundActions.put( event, new ArrayList<>() );
		}
	}

	public static <T extends InputEvent, T2 extends InputAction<T>> void bindAction(T event, T2 action) {
		if (events.contains( event ) && boundActions.containsKey( event )) {
			List<InputAction> actions = boundActions.get( event );

			actions.add( action );

			boundActions.put( event, actions );
		}
	}

	/**
	 * Unbinds ALL ACTIONS from given event (actions may be anonymous so they cannot be identified)
	 */
	public static void unbindActions(InputEvent event) {
		if (boundActions.containsKey( event )) {
			boundActions.get( event ).clear();
		}
	}

	/**
	 * A reeeeaaaally bad way of binding stuff to events. (Unchecked behaviour)
	 */
	@SuppressWarnings("unchecked")
	public static void bindAction(String eventName, InputAction action) {
		try {
			bindAction( getEvent( eventName ), action );
		} catch (EventException e) {
			Debug.logException( e, "InputHandler" );
		}
	}

	public static InputEvent getEvent(String lookupName) throws EventException {
		if (eventLookupTable.containsKey( lookupName )) {
			return eventLookupTable.get( lookupName );
		}

		throw new EventException( "Event not found. name=\"" + lookupName + "\"" );
	}
}
