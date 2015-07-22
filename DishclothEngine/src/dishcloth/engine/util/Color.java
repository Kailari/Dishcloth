package dishcloth.engine.util;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Color.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * A color. Stored as a 128-bit RGBA-value using floats
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 21.5.2015
 */

public class Color {

	public static final Color BLACK = new Color( 0f, 0f, 0f );
	public static final Color RED = new Color( 1.0f, 0f, 0f );
	public static final Color GREEN = new Color( 0f, 1.0f, 0f );
	public static final Color YELLOW = new Color( 1.0f, 1.0f, 0f );
	public static final Color BLUE = new Color( 0f, 0f, 1.0f );
	public static final Color MAGENTA = new Color( 1.0f, 0f, 1.0f );
	public static final Color CYAN = new Color( 0f, 1.0f, 1.0f );
	public static final Color WHITE = new Color( 1.0f, 1.0f, 1.0f );

	public float r, g, b, a;

	public Color(int r, int g, int b, int a) {
		this( r / 255f, g / 255f, b / 255f, a / 255f );
	}

	public Color(int r, int g, int b) {
		this( r, g, b, 255 );
	}

	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Color(float r, float g, float b) {
		this( r, g, b, 1f );
	}

	public int toInteger() {
		int rInt = Math.round( r * 255f );
		int gInt = Math.round( g * 255f );
		int bInt = Math.round( b * 255f );
		int aInt = Math.round( a * 255f );
		return aInt << 24 | bInt << 16 | gInt << 8 | rInt;
	}
}
