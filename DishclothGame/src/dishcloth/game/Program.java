package dishcloth.game;

import dishcloth.engine.util.logger.ANSIColor;
import dishcloth.engine.util.logger.Debug;

public class Program {

	public static void main(String[] args) {

		System.out.println("Application is starting...");

		Debug.log( "This is a test", "MAIN" );
		Debug.log( "This is a test #2", Program.class.getSimpleName() );

		Debug.logNote( "This is a note", "NoteTest" );
		Debug.logOK( "This is an all-OK log entry", "OKTest" );
		Debug.logErr( "This is an error", "ErrorTest" );
		Debug.logWarn( "This is a warning", "WarningTest" );

		Debug.log( ANSIColor.RED + "R" + ANSIColor.GREEN + "G" + ANSIColor.BLUE + "B" + ANSIColor.RESET, "mAiN" );

		new DishclothGame().run();

		System.out.println("Application stopped.");
	}
}
