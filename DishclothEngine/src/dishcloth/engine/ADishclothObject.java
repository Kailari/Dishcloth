package dishcloth.engine;

import dishcloth.engine.events.EventRegistry;

/**
 * ADishclothObject.java
 * <p>
 * All objects that need to be able to subscribe to events should extend ADishclothObject
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 20.7.2015
 */

public abstract class ADishclothObject {
	
	protected ADishclothObject(boolean doEventListenerRegistration) {
		if (doEventListenerRegistration) {
			registerInstance();
		}
	}
	
	private void registerInstance() {
		EventRegistry.registerEventListenerInstance( this );
	}
}
