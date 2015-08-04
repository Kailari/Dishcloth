package dishcloth.engine.io;

import dishcloth.engine.util.logger.Debug;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * <b>ByteHelper</b>
 * <p>
 * Handles conversions between byte arrays and primitive types.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 10.6.2015
 */

public class ByteHelper {
	private ByteHelper() { }

	public static short toShort(byte[] bytes) {
		assert bytes.length == 2;
		return ByteBuffer.wrap(bytes).getShort();
	}

	public static int toInt(byte[] bytes) {
		assert bytes.length == 4;
		return ByteBuffer.wrap(bytes).getInt();
	}

	public static long toLong(byte[] bytes) {
		assert bytes.length == 8;
		return ByteBuffer.wrap(bytes).getLong();
	}

	public static float toFloat(byte[] bytes) {
		assert bytes.length == 4;
		return ByteBuffer.wrap(bytes).getFloat();
	}

	public static double toDouble(byte[] bytes) {
		assert bytes.length == 8;
		return ByteBuffer.wrap(bytes).getDouble();
	}

	public static String toString(byte[] bytes) {
		try {
			return new String( bytes, "UTF-8" );
		}
		catch (UnsupportedEncodingException e) {
			Debug.logException( e, "ByteHelper" );
			return "";
		}
	}

	public static byte[] toBytes(short s) {
		byte[] bytes = new byte[2];
		ByteBuffer.wrap( bytes ).putShort( s );
		return bytes;
	}

	public static byte[] toBytes(int i) {
		byte[] bytes = new byte[4];
		ByteBuffer.wrap( bytes ).putInt( i );
		return bytes;
	}

	public static byte[] toBytes(long l) {
		byte[] bytes = new byte[8];
		ByteBuffer.wrap( bytes ).putLong( l );
		return bytes;
	}

	public static byte[] toBytes(float f) {
		byte[] bytes = new byte[4];
		ByteBuffer.wrap( bytes ).putFloat( f );
		return bytes;
	}

	public static byte[] toBytes(double d) {
		byte[] bytes = new byte[8];
		ByteBuffer.wrap( bytes ).putDouble( d );
		return bytes;
	}

	public static byte[] toBytes(String s) {
		//return s.getBytes();
		return s.getBytes( Charset.forName("UTF-8") );
	}
}
