package dishcloth.engine.exception;

/**
 * <b>GameInitializationException</b>
 * <p>
 * An exception thrown during game initialization.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 21.5.2015
 *
 * @see dishcloth.engine.AGame
 */

public class GameInitializationException extends Exception {
	public GameInitializationException(String message) {
		super( message );
	}
}
