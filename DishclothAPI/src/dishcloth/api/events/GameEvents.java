package dishcloth.api.events;

import dishcloth.api.abstractionlayer.IGame;
import dishcloth.api.abstractionlayer.content.IContentManager;
import dishcloth.api.abstractionlayer.events.IEvent;

/**
 * <b>GameEvents</b>
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 6.8.2015 at 13:17
 */

public class GameEvents {
	protected static class AGameEvent implements IEvent {
		private final IGame game;

		public AGameEvent(IGame game) {
			this.game = game;
		}

		public IGame getGame() {
			return game;
		}
	}

	public static class GamePreInitializationEvent extends AGameEvent {
		public GamePreInitializationEvent(IGame game) {
			super( game );
		}
	}

	public static class GamePostInitializationEvent extends AGameEvent {
		public GamePostInitializationEvent(IGame game) {
			super( game );
		}
	}

	public static class GameContentLoadingEvent extends AGameEvent {
		public final IContentManager contentManager;

		public GameContentLoadingEvent(IGame game, IContentManager contentManager) {
			super( game );
			this.contentManager = contentManager;
		}
	}

	public static class GameContentDisposingEvent extends AGameEvent {
		public final IContentManager contentManager;

		public GameContentDisposingEvent(IGame game, IContentManager contentManager) {
			super( game );
			this.contentManager = contentManager;
		}
	}

	public static class GameShutdownEvent extends AGameEvent {
		public GameShutdownEvent(IGame game) {
			super( game );
		}
	}
}
