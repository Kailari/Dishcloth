package dishcloth.engine.content;

import dishcloth.engine.events.IEvent;

/**
 * <b>ContentEvents</b>
 * <p>
 * Container for content pipeline events.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 4.8.2015
 *
 * @see ContentManager
 * @see ContentPipeline
 */

public class ContentEvents {
	/**
	 * <b>ContentPipelineExtensionRegistrationEvent</b>
	 * <p>
	 * Fired after default content pipeline extensions are registered.
	 * <p>
	 * This event can be used to register custom content pipeline extensions.
	 * <p>
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
	 *
	 * @see ContentEvents
	 * @see AContentImporter
	 * @see AContentProcessor
	 */
	public static class ContentPipelineExtensionRegistrationEvent implements IEvent {
	}
}
