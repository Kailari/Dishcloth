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

	public static final Color RED = new Color( 1f, 0f, 0f, 1f );
	public static final Color GREEN = new Color( 0f, 1f, 0f, 1f );
	public static final Color BLUE = new Color( 0f, 0f, 1f, 1f );
	public static final Color YELLOW = new Color( 1f, 0f, 0f, 1f );

	float r;    // 32bit
	float g;    // 32bit
	float b;    // 32bit
	float a;    // 32bit
	// = 128bit

	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Color(float r, float g, float b) {
		this( r, g, b, 1f );
	}
}
