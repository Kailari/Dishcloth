package dishcloth.game;

import dishcloth.engine.util.logger.ANSIColor;
import dishcloth.engine.util.logger.Debug;

public class Program {

	public static void main(String[] args) {

		System.out.println("Application is starting...");

		new DishclothGame().run();

		System.out.println("Application stopped.");
	}
}
