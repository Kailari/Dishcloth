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

	private boolean isDisposed = false;

	protected ADishclothObject(boolean doEventListenerRegistration) {
		if (doEventListenerRegistration) {
			registerInstance();
		}
	}
	
	private void registerInstance() {
		EventRegistry.registerEventListenerInstance( this );
	}

	@Override
	protected final void finalize() throws Throwable {
		super.finalize();
		if (!isDisposed) {
			dispose();
			isDisposed = true;
		}
	}

	public void dispose() {

	}
}
