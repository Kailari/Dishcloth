package dishcloth.engine.events;

import dishcloth.engine.exception.EventException;
import dishcloth.engine.util.logger.ANSIColor;
import dishcloth.engine.util.logger.Debug;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * EventHandler.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Stores and fires triggers (events) and logic tied to them (actions). An event may contain multiple bound actions.
 * ie. an event that fires when a key is pressed, may contain animation action and player movement action (or something
 * like that.)
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 29.5.2015
 */

public class EventHandler {

	private static List<Event> events = new ArrayList<>();
	private static HashMap<Event, List<EventAction>> boundActions = new HashMap<>();

	private static HashMap<String, Event> eventLookupTable = new HashMap<>();

	@SuppressWarnings("unchecked")
	public static void updateEvents() {
		// Java 8 porn. Streams and method references.

		// Streams:
		//      for (a : b)
		//      (where b is a collection)
		//   becomes:
		//      a.stream().forEach()
		//
		// Method references (shorter way to write certain lambdas):
		//      event -> event.triggerCondition()
		//   becomes:
		//      Event::triggerCondition

		events.stream().filter( Event::triggerCondition ).forEach( event -> {
			for (EventAction action : boundActions.get( event )) {
				// NOTE: This is unchecked but as main binding method forces using compatible types,
				// it should not be a problem
				action.trigger( event );
			}
		} );
	}

	public static void registerEvent(Event event, String lookupName) {
		if (!events.contains( event )
				&& !boundActions.containsKey( event )
				&& !eventLookupTable.containsKey( lookupName )) {

			events.add( event );
			eventLookupTable.put( lookupName, event );

			boundActions.put( event, new ArrayList<>() );
		}
	}

	public static <T extends Event, T2 extends EventAction<T>> void bindAction(T event, T2 action) {
		if (events.contains( event ) && boundActions.containsKey( event )) {
			List<EventAction> actions = boundActions.get( event );

			actions.add( action );

			boundActions.put( event, actions );
		}
	}

	/**
	 * Unbinds ALL ACTIONS from given event (actions may be anonymous so they cannot be identified)
	 */
	public static void unbindActions(Event event) {
		if (boundActions.containsKey( event )) {
			boundActions.get( event ).clear();
		}
	}

	/**
	 * A reeeeaaaally bad way of binding stuff to events. (Unchecked behaviour)
	 */
	@SuppressWarnings("unchecked")
	public static void bindAction(String eventName, EventAction action) {
		try {
			bindAction( getEvent( eventName ), action );
		} catch (EventException e) {
			Debug.logException( e, "EventHandler" );
		}
	}

	public static Event getEvent(String lookupName) throws EventException {
		if (eventLookupTable.containsKey( lookupName )) {
			return eventLookupTable.get( lookupName );
		}

		throw new EventException( "Event not found. name=\"" + lookupName + "\"" );
	}

	public static <T extends IEventStorage> void registerEvents(Class<T> eventsClass) {
		// Prefix all registered events with "event_<class name in lowercase without 'Events' at the end>"
		String accessNamePrefix = "event_"
				+ eventsClass.getSimpleName().toLowerCase().replace( "events", "" );

		// Iterate trough all fields in given class
		for (Field f : eventsClass.getFields()) {

			// Only register events if they are public, static and extend Event
			if (Modifier.isPublic( f.getModifiers() )
					&& Modifier.isStatic( f.getModifiers() )
					&& Event.class.isAssignableFrom( f.getType() )) {

				// Parse access name
				//  1. Get field name and remove all parts containing 'Event' or 'event'
				//  2. Split name by uppercase letters.
				//          (assumes that programmer who wrote the event storage used camelCase)
				//  3. Translate the camelCase into string_split_by_underscores
				String name = f.getName().replace( "Event", "" ).replace( "event", "" );
				String accessName = "";
				for (String s : name.split( "(?=\\p{Upper})" )) {
					accessName += "_" + s.toLowerCase();
				}

				try {
					registerEvent( (Event) f.get( null ), accessNamePrefix + accessName );

					Debug.log( "Registered event:"
							           + ANSIColor.CYAN
							           + " \"" + accessNamePrefix + accessName + "\""
							           + ANSIColor.RESET,
					           "EventHandler" );

				} catch (IllegalAccessException e) {
					Debug.logException( e, "EventHandler" );
				}
			}
		}
	}
}
