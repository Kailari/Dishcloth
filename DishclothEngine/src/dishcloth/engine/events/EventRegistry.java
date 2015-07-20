package dishcloth.engine.events;

import dishcloth.engine.util.logger.ANSIColor;
import dishcloth.engine.util.logger.Debug;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * EventRegistry.java
 * <p>
 * So... much... reflection...
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 29.5.2015
 */

public final class EventRegistry {

	/**
	 * Contains map of instances and methods ordered by events, keyed by listener class.
	 * <p>
	 * 1.  This allows to easily get all event classes contained by certain listener class <br>
	 * 2.  All event classes in sub-map are directly convertible to their EventHandlers in listener class<br>
	 * 3.  Due to EventHandlers being stored in RegistryEntries with their instances, they are easily accessible
	 * when triggering events.<br>
	 */
	// HashMap<ListenerClass, HashMap<EventClass, Instance & Method>>
	private static HashMap<Class, HashMap<Class, RegistryEntry>> eventRegistry = new HashMap<>();
	/**
	 * Contains list of EventHandler types contained in a listener class
	 */
	// <ListenerClass, List<EventClass>>
	private static HashMap<Class, List<Class>> listenerRegistry = new HashMap<>();

	/**
	 * Classes that are already parsed for @EventHandlers
	 */
	private static List<Class> registeredClasses = new ArrayList<>();

	/**
	 * Registers object into registry for triggering its event handlers. If object inherits ADishclothObject this
	 * method is automatically called.
	 *
	 * @param object Event listener
	 */
	public static void registerEventListenerInstance(Object object) {
		registerEventListener( object.getClass(), object );
	}

	/**
	 * Registers static class into registry.<br>
	 * <b>WARN: ASSUMES THAT ALL EVENT HANDLERS IN GIVEN CLASS ARE STATIC<b/>
	 *
	 * @param listenerClass Event listener class. (static)
	 */
	public static void registerEventListener(Class listenerClass) {
		registerEventListener( listenerClass, null );
	}

	private static void registerEventListener(Class listenerClass, Object object) {
		if (!registeredClasses.contains( listenerClass )) {
			registerEventHandlers( listenerClass );
		}

		// For each handlerMethod in listenerClass, add instance to handlerMethod instances
		//  - Handlers are stored in RegistryEntries, thus we need to know EventClass in order
		//    to get reference to them.
		//  - EventClasses are stored as list in listenerRegistry, keyed by ListenerClass

		for (Class eventClass : listenerRegistry.get( listenerClass )) {
			eventRegistry.get( listenerClass ).get( eventClass ).instances.add( object );
		}

		Debug.logOK( "Instance of class "
				             + ANSIColor.YELLOW + "\"" +
				             listenerClass.getSimpleName()
				             + "\"" + ANSIColor.RESET
				             + " was successfully registered as event listener.",
		             "EventRegistry" );
	}

	/**
	 * Registers all @EventHandler -annotated methods from given class as events. Doing this causes all registered
	 * instances of listenerClass to get their eventHandlers ran every time compatible event is fired.
	 *
	 * @param listenerClass class which EventHandlers are to be registered
	 */
	private static void registerEventHandlers(Class listenerClass) {

		// Store old size for comparison. (If size changes, at least one handlerMethod was registered.)
		int oldCount = eventRegistry.size();

		// Prepare registry for new class
		if (!listenerRegistry.containsKey( listenerClass )) {
			listenerRegistry.put( listenerClass, new ArrayList<>() );
		}
		eventRegistry.put( listenerClass, new HashMap<>() );

		// In brief all that the following monstrous line does is:
		// It gets from 'listenerClass' all the methods that are annotated as EventHandler and makes sure that
		// methods have exactly one parameter, which is of a type that inherits from IEvent.
		// After that, it iterates trough all methods that passed the filter and registers them using
		// 'registerHandler' -method.

		// Also, note that all this is technically just a single line; not a horrifying pile of nested for-loops :P

		// Get all methods from the target class and convert given array to a list.
		Arrays.asList( listenerClass.getMethods() )
				// Convert the created list into a stream.
				.stream()
						// Filter the stream.
						// In order to pass the filter, the method must pass the three requirements:
						//  1. @EventHandler -annotation
						//  2. Exactly one parameter
						//  3. Parameter type must inherit from IEvent
				.filter( method ->
						         // Find annotation
						         (method.isAnnotationPresent( EventHandler.class )
								         // Check parameter count
								         && method.getParameterCount() == 1
								         // Check parameter type
								         && IEvent.class.isAssignableFrom( method.getParameterTypes()[0] )) )
						// Register all handlers that passed.
				.forEach( method -> EventRegistry.registerHandler( method, listenerClass ) );

		// Calculate difference in registry size
		int difference = eventRegistry.size() - oldCount;

		// Nothing was registered
		if (difference == 0) {
			Debug.logWarn( "EventRegistry found nothing to register. Class was " +
					               ANSIColor.YELLOW + "\"" +
					               listenerClass.getSimpleName()
					               + "\"" + ANSIColor.RESET,
			               "EventRegistry" );
		}
		// Registration successful
		else if (difference == 1) {
			Debug.logOK( "Successfully registered "
					             + ANSIColor.BLUE
					             + eventRegistry.get( listenerClass ).size()
					             + ANSIColor.RESET
					             + " EventHandlers from class " +
					             ANSIColor.YELLOW + "\"" +
					             listenerClass.getSimpleName()
					             + "\"" + ANSIColor.RESET,
			             "EventRegistry" );
		}
		// ERROR
		else if (difference < 0 || difference > 1) {
			Debug.logErr( "Something weird happened while registering EventHandlers. Class was " +
					              ANSIColor.YELLOW + "\"" +
					              listenerClass.getSimpleName()
					              + "\"" + ANSIColor.RESET,
			              "EventRegistry" );
		}

		// Flag class as registered.
		registeredClasses.add( listenerClass );
	}

	private static void registerHandler(Method method, Class listenerClass) {
		Class eventClass = method.getParameterTypes()[0];

		// Add event class to listener class's events
		listenerRegistry.get( listenerClass ).add( eventClass );

		// Register method to registry
		eventRegistry.get( listenerClass ).put( eventClass, new RegistryEntry( method ) );
	}

	public static boolean eventExists(Class eventClass) {
		boolean keyWasFound = false;

		for (HashMap<Class, RegistryEntry> registryEntryMap : eventRegistry.values()) {
			if (registryEntryMap.containsKey( eventClass )) {
				keyWasFound = true;
				break;
			}
		}

		return keyWasFound;
	}

	// TODO: Implement event String-lookup
	// XXX: is it really even needed?
	public static String parseEventNameFromClass(Class eventClass) {
		String accessName = "event";

		String fullName = eventClass.getSimpleName().replace( "Event", "" ).replace( "event", "" );

		for (String s : fullName.split( "(?=\\p{Upper})" )) {
			accessName += "_" + s.toLowerCase();
		}

		return accessName;
	}

	public static void fireEvent(IEvent event) {
		Class eventClass = event.getClass();

		if (!eventExists( eventClass )) {
			Debug.logNote( "Fired event that has no registered listeners.", "EventRegistry" );
			Debug.logNote( "Event: "
					               + ANSIColor.CYAN + "\""
					               + parseEventNameFromClass( eventClass )
					               + "\"" + ANSIColor.YELLOW + " (" + eventClass.getSimpleName()
					               + ")" + ANSIColor.RESET, "EventRegistry" );
			return;
		}

		// Following method-scope class and temporary list are necessary to prevent
		// ConcurrentModificationException in cases where events are registered inside
		// another method.

		// Method-scope class :---DDD
		class EventToBeFired {
			Object instance;
			IEvent event;
			Method handlerMethod;

			public EventToBeFired(Object instance, IEvent event, Method handlerMethod) {
				this.instance = instance;
				this.event = event;
				this.handlerMethod = handlerMethod;
			}
		}
		List<EventToBeFired> events = new ArrayList<>();

		eventRegistry.values().stream()
				.filter( registryEntryMap -> registryEntryMap.containsKey( eventClass ) )
				.forEach( registryEntryMap -> {
					RegistryEntry entry = registryEntryMap.get( eventClass );
					entry.instances.forEach( instance -> events.add( new EventToBeFired( instance,
					                                                                     event,
					                                                                     entry.method ) ) );
				} );

		events.forEach( eventToBeFired -> fireEvent( eventToBeFired.event,
		                                             eventToBeFired.instance,
		                                             eventToBeFired.handlerMethod ) );
	}

	private static void fireEvent(IEvent event, Object instance, Method handler) {
		try {
			Debug.log( "Calling EventHandler "
					           + ANSIColor.CYAN + "\""
					           + parseEventNameFromClass( event.getClass() )
					           + "\"" + ANSIColor.RESET
					           + " in class "
					           + ANSIColor.YELLOW + "\""
					           + handler.getDeclaringClass().getSimpleName()
					           + "\"" + ANSIColor.RESET, "EventRegistry" );

			handler.invoke( instance, event );
		} catch (IllegalAccessException e) {
			Debug.logErr( "EVENT HANDLER WAS INACCESSIBLE!", "EventRegistry" );
			Debug.logException( e, "EventRegistry" );
		} catch (IllegalArgumentException e) {
			Debug.logErr( "EVENT HANDLER ARGUMENT DID NOT MATCH GIVEN EVENT!", "EventRegistry" );
			Debug.logErr( "This is most likely engine-level EventRegistry bug.", "EventRegistry" );
			Debug.logException( e, "EventRegistry" );
		} catch (InvocationTargetException e) {
			Debug.logErr( "EXCEPTION IN EVENT HANDLER!", "EventRegistry" );
			Debug.logErr( "Exception was thrown in method "
					              + ANSIColor.YELLOW + "\""
					              + handler.getName()
					              + "\"" + ANSIColor.RESET
					              + " in class "
					              + ANSIColor.YELLOW + "\""
					              + instance.getClass().getSimpleName()
					              + "\"" + ANSIColor.RESET, "EventRegistry" );
			Debug.logException( e, "EventRegistry" );
		} catch (Exception e) {
			Debug.logException( e, "EventRegistry" );
		}
	}

	private static class RegistryEntry {
		private List<Object> instances;
		private Method method;

		private RegistryEntry(Method method) {
			this.instances = new ArrayList<>();
			this.method = method;
		}
	}
}
