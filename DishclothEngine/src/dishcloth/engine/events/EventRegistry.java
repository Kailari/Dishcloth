package dishcloth.engine.events;

import dishcloth.api.abstractionlayer.events.EventHandler;
import dishcloth.api.abstractionlayer.events.IEvent;
import dishcloth.api.abstractionlayer.events.IEventRegistry;

/**
 * <b>EventRegistry</b>
 * <p>
 * Handles event registration, event listener class reflection and event firing on demand.
 * <p>
 * Implementation relies on internal singleton.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 29.5.2015
 *
 * @see IEvent
 * @see EventHandler
 */

// TODO: Split this class into multiple classes to follow the SRP
public final class EventRegistry {

	public static IEventRegistry getInstance() {
		return EventRegistrySingleton.getInstance();
	}

	/**
	 * Registers object into registry for triggering its event handlers. If object inherits ADishclothObject this
	 * method is automatically called.
	 *
	 * @param object Event listener
	 */
	public static void registerEventListenerInstance(Object object) {
		EventRegistrySingleton.getInstance().registerEventListenerInstance( object );
	}

	/**
	 * Registers static class into registry.<br>
	 * <b>WARN: ASSUMES THAT ALL EVENT HANDLERS IN GIVEN CLASS ARE STATIC<b/>
	 *
	 * @param listenerClass Event listener class. (static)
	 */
	// TODO: Make it possible to have static and non-static EventHandlers in one listener
	public static void registerStaticEventListener(Class listenerClass) {
		EventRegistrySingleton.getInstance().registerStaticEventListener( listenerClass );
	}

	public static boolean eventExists(Class eventClass) {
		return EventRegistrySingleton.getInstance().eventExists( eventClass );
	}

	public static String parseEventNameFromClass(Class eventClass) {
		return EventRegistrySingleton.getInstance().parseEventNameFromClass( eventClass );
	}

	public static void fireEvent(IEvent event) {
		EventRegistrySingleton.getInstance().fireEvent( event );
	}
}
