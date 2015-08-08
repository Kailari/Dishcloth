package dishcloth.engine.util;

import dishcloth.engine.util.debug.Debug;

/**
 * <b>BitUtils</b>
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 7.8.2015 at 17:46
 */

public class BitUtils {
	private static final int[] TWO_POW = new int[32];

	static {
		for (int i = 0; i < TWO_POW.length; i++) {
			TWO_POW[i] = 1 << i;
		}
	}

	private BitUtils() {}

	public static short interleave(byte a, byte b) {

		short x = 0;
		int mask;
		String dbg = "";
		String dbga = "";
		String dbgb = "";
		for (int i = 0; i < 8; i++) {
			mask = TWO_POW[i];
			x = (short) (x | (a & mask) << (i * 2) | (b & mask) << ((1 + i) * 2));
			dbg += "" + ((a & mask) != 0 ? 1 : 0) + ((b & mask) != 0 ? 1 : 0);
			dbga += "" + ((a & mask) != 0 ? 1 : 0);
			dbgb += "" + ((b & mask) != 0 ? 1 : 0);
		}

		Debug.logNote( "a bits:      " + new StringBuilder( dbga ).reverse().toString(), "BitUtils" );
		Debug.logNote( "a uint:      " + Byte.toUnsignedInt( a ), "BitUtils" );
		Debug.logNote( "b bits:      " + new StringBuilder( dbgb ).reverse().toString(), "BitUtils" );
		Debug.logNote( "b uint:      " + Byte.toUnsignedInt( b ), "BitUtils" );
		Debug.logNote( "interleaved: " + new StringBuilder( dbg ).reverse().toString(), "BitUtils" );
		Debug.logNote( "value:       " + x, "BitUtils" );
		Debug.logNote( "value uint:  " + Short.toUnsignedInt( x ), "BitUtils" );

		return (short) x;
	}
}
