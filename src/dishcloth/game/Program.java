package dishcloth.game;

import dishcloth.engine.util.Debug;

public class Program {

	public static void main(String[] args) {

		System.out.println("Application is starting...");

		Debug.log( "This is a test", "MAIN" );
		Debug.log( "This is a test #2", Program.class );

		Debug.logNote( "This is a note", "NoteTest" );
		Debug.logOK( "This is a all-OK log entry", "OKTest" );
		Debug.logErr( "This is an error", "ErrorTest" );
		Debug.logWarn( "This is a warning", "WarningTest" );

		DishclothGame game = new DishclothGame();
		game.run();

		System.out.println("Application stopped.");
	}
}
