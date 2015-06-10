package dishcloth.engine.util.logger;

import dishcloth.engine.util.Color;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * ANSIColor.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 22.5.2015
 */

public enum ANSIColor {

	// TODO: Add bright color variants (using "bold" SGR parameter)

////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Reset SGR parameter. Use this to clear all SGR parameters
	 */
	RESET               ("\u001B[0m", Color.WHITE),

	BLACK               (0, Color.BLACK),
	RED                 (1, Color.RED),
	GREEN               (2, Color.GREEN),
	YELLOW              (3, Color.YELLOW),
	BLUE                (4, Color.BLUE),
	MAGENTA             (5, Color.MAGENTA),
	CYAN                (6, Color.CYAN),
	WHITE               (7, Color.WHITE),

	BLACK_BACKGROUND    (0, true, Color.BLACK),
	RED_BACKGROUND      (1, true, Color.RED),
	GREEN_BACKGROUND    (2, true, Color.GREEN),
	YELLOW_BACKGROUND   (3, true, Color.YELLOW),
	BLUE_BACKGROUND     (4, true, Color.BLUE),
	MAGENTA_BACKGROUND  (5, true, Color.MAGENTA),
	CYAN_BACKGROUND     (6, true, Color.CYAN),
	WHITE_BACKGROUND    (7, true, Color.WHITE);

////////////////////////////////////////////////////////////////////////////////////

	private final String sequence;
	private final Color rgb;


	ANSIColor(int colorIndex, Color c) {
		this(colorIndex, false, c);
	}

	/**
	 * Creates string representation of a ANSI color
	 * @param colorIndex    index of color. 0 to 7, inclusive
	 * @param bg            is background color?
	 */
	ANSIColor(int colorIndex, boolean bg, Color c) {
		this("\u001B["+((bg ? 40 : 30) + colorIndex)+"m", c);
	}

	ANSIColor(String sequence, Color c) {
		this.sequence = sequence;
		this.rgb = c;
	}


	public String applyTo(String message, boolean reset) {
		return this + message + (reset ? RESET : "");
	}

	public String applyTo(String message) {
		return applyTo( message, true );
	}

	@Override
	public String toString() {
		return sequence;
	}
}
