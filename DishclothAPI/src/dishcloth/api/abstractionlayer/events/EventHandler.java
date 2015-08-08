package dishcloth.api.abstractionlayer.events;

import dishcloth.api.abstractionlayer.events.IEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>EventHandler</b>
 * <p>
 * Flags the method as a event handler.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 19.7.2015
 *
 * @see IEvent
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHandler {
}
