package dishcloth.engine.util;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Color.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * A color. Stored as a 32-bit RGBA-value using floats
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 21.5.2015
 */

public class Color {

	public static final Color BLACK = new Color( 0, 0, 0 );
	public static final Color RED = new Color( 255, 0, 0 );
	public static final Color GREEN = new Color( 0, 255, 0 );
	public static final Color YELLOW = new Color( 255, 255, 0 );
	public static final Color BLUE = new Color( 0, 0, 255 );
	public static final Color MAGENTA = new Color( 255, 0, 255 );
	public static final Color CYAN = new Color( 0, 255, 255 );
	public static final Color WHITE = new Color( 255, 255, 255 );

	public byte r, g, b, a;


	public Color(int r, int g, int b, int a) {
		this.r = (byte)r;
		this.g = (byte)g;
		this.b = (byte)b;
		this.a = (byte)a;
	}

	public Color(int r, int g, int b) {
		this( r, g, b, 1 );
	}

	public Color(byte r, byte g, byte b, byte a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Color(byte r, byte g, byte b) {
		this( r, g, b, (byte) 255 );
	}
}
