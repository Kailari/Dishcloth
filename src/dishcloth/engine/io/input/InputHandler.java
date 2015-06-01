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

	public static void registerEvent(InputEvent event, String lookupName) {
		if (!events.contains( event )
				&& !boundActions.containsKey( event )
				&& !eventLookupTable.containsKey( lookupName )) {

			events.add( event );
			eventLookupTable.put( lookupName, event );

			boundActions.put( event, new ArrayList<>() );
		}
	}

	public static void bindAction(InputEvent event, InputAction action) {
		if (events.contains( event ) && boundActions.containsKey( event )) {
			List<InputAction> actions = boundActions.get( event );

			actions.add( action );

			boundActions.put(event, actions);
		}
	}

	public static void bindAction(String eventName, InputAction action) {
		try {
			bindAction( getEvent( eventName ), action );
		}
		catch (EventException e) {
			Debug.logException(e, "InputHandler");
		}
	}

	public static InputEvent getEvent(String lookupName) throws EventException {
		if (eventLookupTable.containsKey( lookupName )) {
			return eventLookupTable.get( lookupName );
		}

		throw new EventException( "Event not found. name=\"" + lookupName + "\"" );
	}
}
