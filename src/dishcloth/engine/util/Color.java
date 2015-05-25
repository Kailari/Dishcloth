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

	public static final Color BLACK = new Color( 0, 0, 0 );
	public static final Color RED = new Color( 255, 0, 0 );
	public static final Color GREEN = new Color( 0, 255, 0 );
	public static final Color YELLOW = new Color( 255, 255, 0 );
	public static final Color BLUE = new Color( 0, 0, 255 );
	public static final Color MAGENTA = new Color( 255, 0, 255 );
	public static final Color CYAN = new Color( 0, 255, 255 );
	public static final Color WHITE = new Color( 255, 255, 255 );

	public float r, g, b, a;


	public Color(int r, int g, int b, int a) {
		this(r / 255f, g / 255f, b / 255f, a / 255f);
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
		this(r, g, b, 1f);
	}
}
