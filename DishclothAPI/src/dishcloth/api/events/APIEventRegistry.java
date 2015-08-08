package dishcloth.api.events;

import dishcloth.api.abstractionlayer.events.IEvent;
import dishcloth.api.abstractionlayer.events.IEventRegistry;

/**
 * <b>APIEventRegistry</b>
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 6.8.2015 at 15:01
 */

public class APIEventRegistry {
	private static IEventRegistry mainEventRegistry;

	public static void initialize(IEventRegistry mainEventRegistry) {
		if (APIEventRegistry.mainEventRegistry == null) {
			APIEventRegistry.mainEventRegistry = mainEventRegistry;
		}
	}

	public static void registerEventListenerInstance(Object object) {
		mainEventRegistry.registerEventListenerInstance( object );
	}

	public static void registerStaticEventListener(Class listenerClass) {
		mainEventRegistry.registerEventListenerInstance( listenerClass );
	}

	public static boolean eventExists(Class eventClass) {
		return mainEventRegistry.eventExists( eventClass );
	}

	public static String parseEventNameFromClass(Class eventClass) {
		return mainEventRegistry.parseEventNameFromClass( eventClass );
	}

	public static void fireEvent(IEvent event) {
		mainEventRegistry.fireEvent( event );
	}
}
