package dishcloth.api.modules;

import dishcloth.api.abstractionlayer.content.IContentManager;
import dishcloth.api.abstractionlayer.events.IEventRegistry;
import dishcloth.api.abstractionlayer.rendering.IRenderer;

/**
 * <b>DishclothModuleBase</b>
 * <p>
 * Defines a common interface for communication between main program and modules.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 6.8.2015 at 0:06
 */
public abstract class DishclothModuleBase {

	private boolean initialized;
	private boolean contentLoaded;

	public final boolean isInitialized() {
		return initialized;
	}

	public boolean isContentLoaded() {
		return contentLoaded;
	}

	public final void doInitialize() {
		if (!this.isInitialized()) {
			initialize();
			initialized = true;
		}
	}

	public abstract void initialize();

	public final void doLoadContent(IContentManager contentManager) {
		if (!this.isContentLoaded()) {
			loadContent( contentManager );
			contentLoaded = true;
		}
	}

	public abstract void loadContent(IContentManager contentManager);

	public final void doUnloadContent(IContentManager contentManager) {
		if (this.isContentLoaded()) {
			unloadContent( contentManager );
			this.contentLoaded = false;
		}
	}

	protected abstract void unloadContent(IContentManager contentManager);

	public final void doShutdown() {
		if (this.isInitialized()) {
			shutdown();
			this.initialized = false;
		}
	}

	protected abstract void shutdown();

	public final void doUpdate() {
		update();
	}

	protected abstract void update();

	public final void doFixedUpdate() {
		fixedUpdate();
	}

	protected abstract void fixedUpdate();

	public final void doRender(IRenderer renderer) {
		render( renderer );
	}

	protected abstract void render(IRenderer renderer);
}
