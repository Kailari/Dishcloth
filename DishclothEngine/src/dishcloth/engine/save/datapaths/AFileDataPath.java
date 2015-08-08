package dishcloth.engine.save.datapaths;

import dishcloth.engine.io.ByteHelper;
import dishcloth.engine.save.IDataPath;
import dishcloth.engine.util.debug.Debug;

import java.io.*;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * AFileDataPath.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 10.6.2015
 */

public class AFileDataPath implements IDataPath {

	private File file;
	private FileInputStream inputStream;
	private FileOutputStream outputStream;

	public AFileDataPath(String filename) {
		this.file = new File( filename );

		// Create the file if it does not already exist
		try {
			if (!new File( filename.substring( 0, filename.lastIndexOf( "/" ) ) ).mkdirs() && !this.file.createNewFile()) {
				if (!this.file.isFile()) {
					throw new IOException( "Could not create file" );
				}
			}
		} catch (IOException e) {
			Debug.logException( e, this );
		}
	}

	public boolean fileIsEmpty() {
		return file.length() == 0;
	}

	@Override
	public void startWrite() {
		if (inputStream == null) {
			try {
				outputStream = new FileOutputStream( file );
			} catch (FileNotFoundException e) {
				Debug.logException( e, this );
			}
		}
	}

	@Override
	public void endWrite() {
		try {
			outputStream.close();
		} catch (IOException e) {
			Debug.logException( e, this );
		} finally {
			outputStream = null;
		}
	}

	@Override
	public void startRead() {
		if (outputStream == null) {
			try {
				inputStream = new FileInputStream( file );
			} catch (FileNotFoundException e) {
				Debug.logException( e, this );
			}
		}
	}

	@Override
	public void endRead() {
		try {
			inputStream.close();
		} catch (IOException e) {
			Debug.logException( e, this );
		} finally {
			inputStream = null;
		}
	}

	@Override
	public void writeByte(byte b) {
		try {
			outputStream.write( new byte[]{b} );
		} catch (IOException e) {
			Debug.logException( e, this );
		}
	}

	@Override
	public void writeShort(short s) {
		try {
			outputStream.write( ByteHelper.toBytes( s ) );
		} catch (IOException e) {
			Debug.logException( e, this );
		}
	}

	@Override
	public void writeInt(int i) {
		try {
			outputStream.write( ByteHelper.toBytes( i ) );
		} catch (IOException e) {
			Debug.logException( e, this );
		}
	}

	@Override
	public void writeLong(long l) {
		try {
			outputStream.write( ByteHelper.toBytes( l ) );
		} catch (IOException e) {
			Debug.logException( e, this );
		}
	}

	@Override
	public void writeFloat(float f) {
		try {
			outputStream.write( ByteHelper.toBytes( f ) );
		} catch (IOException e) {
			Debug.logException( e, this );
		}
	}

	@Override
	public void writeDouble(double d) {
		try {
			outputStream.write( ByteHelper.toBytes( d ) );
		} catch (IOException e) {
			Debug.logException( e, this );
		}
	}

	@Override
	public void writeString(String s) {
		try {
			outputStream.write( ByteHelper.toBytes( s.length() ) );
			outputStream.write( ByteHelper.toBytes( s ) );
		} catch (IOException e) {
			Debug.logException( e, this );
		}
	}

	@Override
	public void writeBytes(byte[] bytes) {
		try {
			outputStream.write( bytes );
		} catch (IOException e) {
			Debug.logException( e, this );
		}
	}

	@Override
	public byte readByte() {
		return readBytes( 1 )[0];
	}

	@Override
	public short readShort() {
		return ByteHelper.toShort( readBytes( 2 ) );
	}

	@Override
	public int readInt() {
		return ByteHelper.toInt( readBytes( 4 ) );
	}

	@Override
	public long readLong() {
		return ByteHelper.toLong( readBytes( 8 ) );
	}

	@Override
	public float readFloat() {
		return ByteHelper.toFloat( readBytes( 4 ) );
	}

	@Override
	public double readDouble() {
		return ByteHelper.toShort( readBytes( 8 ) );
	}

	@Override
	public String readString() {
		byte[] bytes = readBytes( readInt() );
		return ByteHelper.toString( bytes );
	}

	@Override
	public byte[] readBytes(int n) {
		try {
			byte[] bytes = new byte[n];
			int read = inputStream.read( bytes );

			// IF THIS ASSERTION FAILS, THE DOOMSDAY IS UPON US
			assert read == n;

			return bytes;
		} catch (IOException e) {
			Debug.logException( e, this );
			return new byte[0];
		}
	}
}
