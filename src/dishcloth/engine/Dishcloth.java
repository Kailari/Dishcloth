package dishcloth.engine;

public class Dishcloth {

	public enum ERROR {

		// Member definitions -->

		No_Error,
		Init_Main_InterruptedException;

		// <-- Member definitions

		public int AsInteger() {
			return this.ordinal();
		}
	}

	public static int main(String[] args) {

		System.out.println("Boring application initialization cycle, INCOMING!");
		System.out.println();
		System.out.println("\"main( String[] args )\" called...");
		System.out.println("Adding a bit 'bar' to 'foo'...");

		foobar();

		System.out.print("Continuing endlessly with boring initialization sequence...\n");

		try {

			// Sleep for 1 sec
			Thread.sleep(1000);

			System.out.println("which just keeps on going... on and on...");

			Thread.sleep(1000);
			System.out.println("    and on...");

			Thread.sleep(1000);
			System.out.println("        and on...");
			System.out.println();

			Thread.sleep(1000);

			// Print "error" message
			System.err.println("[CRITICAL] Failed at starting the apocalypse!");
			Thread.sleep(2500);
		}
		catch (InterruptedException e) {
			// If somehow the thread gets interrupted while attempting to sleep,
			// print error and exit.

			System.err.println("Wait... WHAT!? Something DID go wrong, for real! :C");

			return ERROR.Init_Main_InterruptedException.AsInteger();
		}
		finally {

			// Print "Hello World!"
			System.out.print("\n\n\n");
			System.out.println("nah, just kidding, here it comes:");
			System.out.println("    Hello World! :)");
		}

		return ERROR.No_Error.AsInteger();
	}

	public static void foobar() {
		System.out.println("\"foobar()\" called...");
	}
}
