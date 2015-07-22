package dishcloth.utility.sdklauncher;

import dishcloth.engine.util.logger.ANSIColor;
import dishcloth.game.DishclothGame;
import dishcloth.utility.shadertest.Shadertest;

import java.util.*;

/**
 * DishclothUtilityLauncher.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 21.7.2015
 */

public class DishclothUtilityLauncher {
	
	private static final List<UtilitiesEntry> utilities = new ArrayList<>();
	private static final List<Command> commands = new ArrayList<>();

	private static boolean mainLoopRunning;
	private static IUtilityLauncher targetLauncher;
	private static ICommand targetCommand;
	private static String[] targetCommandArgs;
	
	static {

		// **** REGISTER UTILITIES HERE **** //

		addUtility( "Game", "Run the DishclothGame", () -> new DishclothGame().run() );
		addUtility( "Shadertest", "Shader debugging utility", () -> new Shadertest().run() );

		// ********************************* //


		// **** REGISTER COMMANDS HERE  **** //

		addCommand( "exit", "Exit the SDK application", args -> mainLoopRunning = false, "quit" );
		addCommand( "utilities", "Get a list of available utilities", args -> printUtilities() );
		addCommand( "commands", "Get a list of available utilities", args -> printCommands( Command.parseBoolean( args, 0 ) ) );
		addCommand( "help", "View help.", args -> printHelp(), "?" );

		// ********************************* //
	}

	private static void addUtility(String name, String description, IUtilityLauncher launcher) {
		if (name.length() > 16) {
			name = name.substring( 0, 16 );
		}
		utilities.add( new UtilitiesEntry( launcher, name, description ) );
	}
	
	private static void addCommand(String commandName, String description, ICommand command, String... alias) {
		if (commandName.length() > 16) {
			commandName = commandName.substring( 0, 16 );
		}
		commands.add( new Command( commandName, description, command, alias ) );
	}

	public static void run(String[] args) {
		printHeader();

		printHelp();

		printUtilities();

		doMainLoop( args );
	}

	private static void printHeader() {
		System.out.println( ""
				                    + ANSIColor.BLACK_BACKGROUND
				                    + ANSIColor.BLUE
				                    + "+--------------------------------------------+"
				                    + ANSIColor.RESET );
		System.out.println( ""
				                    + ANSIColor.BLACK_BACKGROUND
				                    + ANSIColor.BLUE
				                    + "|                                            |"
				                    + ANSIColor.RESET );
		System.out.println( ""
				                    + ANSIColor.BLACK_BACKGROUND
				                    + ANSIColor.BLUE
				                    + "| "
				                    + ANSIColor.BLACK_BACKGROUND
				                    + ANSIColor.BLUE
				                    + "Dishcloth Utility Launcher / Dishcloth SDK"
				                    + ANSIColor.RESET
				                    + ANSIColor.BLACK_BACKGROUND
				                    + ANSIColor.BLUE
				                    + " |"
				                    + ANSIColor.RESET );
		System.out.println( ""
				                    + ANSIColor.BLACK_BACKGROUND
				                    + ANSIColor.BLUE
				                    + "|                                            |"
				                    + ANSIColor.RESET );
		addCommand( "divideByZero", "Listen up kiddo! Just don't do it, OK?", args -> printTHEMessage() );
		System.out.println( ""
				                    + ANSIColor.BLACK_BACKGROUND
				                    + ANSIColor.BLUE
				                    + "+--------------------------------------------+"
				                    + ANSIColor.RESET );
		System.out.println( "" );
	}

	private static void printUtilities() {
		System.out.println( "Available utilities:" );
		System.out.println();

		utilities.forEach( DishclothUtilityLauncher::printEntry );
		System.out.print( "\n" );
	}

	private static void printEntry(UtilitiesEntry entry) {
		System.out.println( "\t\t"
				                    + ANSIColor.BLUE
				                    + entry.index
				                    + ANSIColor.RESET
				                    + ".\t"
				                    + ANSIColor.GREEN
				                    + padRight( entry.name, 16 )
				                    + ANSIColor.RESET
				                    + " - \t"
				                    + entry.description );
	}

	private static void printCommands(boolean showHiddenCommands) {
		System.out.println( "Available commands:" );
		System.out.print( "\n" );
		commands.forEach( command -> printCommand( command, showHiddenCommands ) );
	}

	private static void printCommand(Command command, boolean flag) {
		if (!flag && command.commandStr.equalsIgnoreCase( "divideByZero" )) {
			return;
		}

		String cmdString = ANSIColor.MAGENTA + padRight( command.commandStr, 16 ) + ANSIColor.YELLOW + " (";
		String aliasString = "";
		List<String> aliases = command.getAliases();
		for (int i = 0; i < aliases.size(); i++) {
			String tmp = aliasString + aliases.get( i );
			if (i != aliases.size() - 1) {
				tmp += ", ";
			}

			if (tmp.length() < 26) {
				aliasString = tmp;
			} else {
				tmp = aliasString + "...";
				if (tmp.length() < 26) {
					aliasString = tmp;
				}
			}
		}
		cmdString += padRight( aliasString + ")", 26 ) + ANSIColor.RESET;

		System.out.println( "\t\t"
				                    + ANSIColor.GREEN
				                    + padRight( cmdString, 16 )
				                    + ANSIColor.RESET
				                    + " - \t"
				                    + command.description );
	}

	private static void printHelp() {
		System.out.println( ANSIColor.CYAN + "DishclothSDK command-line GUI usage:" + ANSIColor.RESET );
		System.out.println();
		System.out.println( "\t- Launch utilities by typing utility "
				                    + ANSIColor.GREEN + "name" + ANSIColor.RESET
				                    + " or "
				                    + ANSIColor.BLUE + "ID" + ANSIColor.RESET
				                    + "." );
		System.out.println( "\t- This GUI can be skipped by specifying commands/utility names in "
				                    + "\n\t  command-line arguments. ('shadertest quit' works, for example)" );
		System.out.println( "\t- ...just don't forget to chain it with \"quit\"! (You might end up" +
				                    "\n\t  with a large number of open SDK instances in background.)" );
		System.out.println( "\t- Commands and utility names are not case-sensitive" );
		System.out.println( "\t- Type "
				                    + ANSIColor.MAGENTA + "\"exit\"" + ANSIColor.RESET
				                    + " or "
				                    + ANSIColor.MAGENTA + "\"quit\"" + ANSIColor.RESET
				                    + " to exit." );
		System.out.println( "\t- Type "
				                    + ANSIColor.MAGENTA + "\"utilities\"" + ANSIColor.RESET
				                    + " for a list of utilities." );
		System.out.println( "\t- Type "
				                    + ANSIColor.MAGENTA + "\"commands\"" + ANSIColor.RESET
				                    + " for a list of commands." );
		System.out.println( "\t- Type "
				                    + ANSIColor.MAGENTA + "\"help\"" + ANSIColor.RESET
				                    + " or "
				                    + ANSIColor.MAGENTA + "\"?\"" + ANSIColor.RESET
				                    + " to print this message again." );
		System.out.print( "\n\n" );
	}

	private static void doMainLoop(String[] args) {
		Scanner inputScanner = new Scanner( System.in );

		boolean argsProcessed = (args.length == 0);
		int argIndex = 0;

		String input;
		mainLoopRunning = true;
		while (mainLoopRunning) {
			System.out.print( ">" );
			if (!argsProcessed) {
				input = args[argIndex];
				argIndex++;
				argsProcessed = (argIndex == args.length);

				System.out.print( input + "\n" );
			} else {
				try {
					input = inputScanner.nextLine().toLowerCase();
				} catch (NoSuchElementException e) {
					input = "quit";
				}
			}

			if (parseInput( input )) {
				if (targetCommand != null) {

					targetCommand.execute( targetCommandArgs );

					targetCommand = null;
					targetCommandArgs = null;
				} else if (targetLauncher != null) {

					System.out.println( "Launching utility!" );
					targetLauncher.launchUtility();

					targetLauncher = null;
					System.out.println( "Utility finished execution..." );
				} else {
					printTHEMessage();
				}
			} else {
				System.out.println( "Utility/Command not found! :C" );
			}
		}
	}

	private static void printTHEMessage() {
		System.out.println(
				"This line should never get called. If you see this message in console,"
						+ "\nplease send the nearest highly-educated monkey to the moon so he/she"
						+ "\ncan investigate the issue. Also, this may be a quite certain hint of"
						+ "\nthe apocalypse getting " + ANSIColor.RED + "REALLY" + ANSIColor.RESET + " near." );
	}

	private static boolean parseInput(String input) {
		String[] splitInput = input.replace( "-", "" ).split( " " );
		// If length is one, it likely is a utility name or index
		if (splitInput.length == 1) {
			if (parseUtility( splitInput[0] )) {
				return true;
			}
		}

		String[] args = new String[splitInput.length - 1];
		System.arraycopy( splitInput, 1, args, 0, args.length );
		return parseCommand( splitInput[0], args );
	}

	private static boolean parseCommand(String commandString, String[] args) {
		for (Command command : commands) {
			if (command.getAliases().contains( commandString.toLowerCase() )) {
				targetCommand = command.command;
				targetCommandArgs = args;
				return true;
			}
		}

		return false;
	}

	private static boolean parseUtility(String utilityString) {
		// First, check if utilityString is a utility index
		try {
			int id = Integer.parseInt( utilityString );
			if (id > 0 && id <= utilities.size()) {
				targetLauncher = utilities.get( id - 1 ).launcher;
				return true;
			}
		} catch (NumberFormatException e) {
			// Invalid number format is expected behaviour. Nothing to see here. :3
		}

		// ...it most certainly wasn't. Now check if it is a valid name then.
		for (UtilitiesEntry entry : utilities) {
			if (entry.name.equalsIgnoreCase( utilityString )) {
				targetLauncher = entry.launcher;
				return true;
			}
		}

		// No matching utility found.
		return false;
	}

	/**
	 * Helper function to pad right side of the String with spaces so that String length is n.
	 *
	 * @param s String to pad
	 * @param n Desired string length
	 * @return Padded String with length n
	 */
	private static String padRight(String s, int n) {
		return String.format( "%1$-" + n + "s", s );
	}

	/**
	 * Stores basic information of a registered utility for easy access and listing.
	 */
	private static class UtilitiesEntry {
		private static int id_counter;
		IUtilityLauncher launcher;
		String name;
		String description;
		int index;
		
		public UtilitiesEntry(IUtilityLauncher launcher, String name, String description) {
			this.launcher = launcher;
			this.name = name;
			this.description = description;
			this.index = ++id_counter;
		}
	}

	/**
	 * Stores basic information of a registered command for easy access and listing.
	 */
	private static class Command {
		private static final HashMap<String, List<String>> aliases = new HashMap<>();
		ICommand command;
		String commandStr;
		String description;
		
		public Command(String commandStr, String description, ICommand command, String... alias) {
			this.command = command;
			this.commandStr = commandStr;
			this.description = description;

			aliases.put( commandStr.toLowerCase(), new ArrayList<>() );
			aliases.get( commandStr.toLowerCase() ).add( commandStr.toLowerCase() );
			for (String s : alias) {
				aliases.get( commandStr.toLowerCase() ).add( s.toLowerCase() );
			}
		}

		private static boolean parseBoolean(String[] args, int index) {
			return (args.length == index + 1 && args[0].equalsIgnoreCase( "true" ));
		}

		public List<String> getAliases() {
			return aliases.get( commandStr.toLowerCase() );
		}
	}
}
