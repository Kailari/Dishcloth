package dishcloth.api.abstractionlayer.events;

/**
 * <b>IEventRegistry</b>
 * <p>
 * Abstraction of EventRegistry
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 6.8.2015 at 13:21
 */
public interface IEventRegistry {
	void registerEventListenerInstance(Object object);

	void registerStaticEventListener(Class listenerClass);

	boolean eventExists(Class eventClass);

	String parseEventNameFromClass(Class eventClass);

	void fireEvent(IEvent event);
}
