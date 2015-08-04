package dishcloth.engine;

import dishcloth.engine.content.ContentManager;
import dishcloth.engine.events.IEvent;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * AGameEvents.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 13.7.2015
 */
public class AGameEvents {

	protected static class AGameEvent implements IEvent {
		private final AGame game;

		public AGameEvent(AGame game) {
			this.game = game;
		}

		public AGame getGame() {
			return game;
		}
	}

	public static class GamePreInitializationEvent extends AGameEvent {
		public GamePreInitializationEvent(AGame game) {
			super( game );
		}
	}

	public static class GamePostInitializationEvent extends AGameEvent {
		public GamePostInitializationEvent(AGame game) {
			super( game );
		}
	}

	public static class GameContentLoadingEvent extends AGameEvent {
		public final ContentManager contentManager;

		public GameContentLoadingEvent(AGame game, ContentManager contentManager) {
			super( game );
			this.contentManager = contentManager;
		}
	}

	public static class GameContentDisposingEvent extends AGameEvent {
		public final ContentManager contentManager;

		public GameContentDisposingEvent(AGame game, ContentManager contentManager) {
			super( game );
			this.contentManager = contentManager;
		}
	}

	public static class GameShutdownEvent extends AGameEvent {
		public GameShutdownEvent(AGame game) {
			super( game );
		}
	}
}
