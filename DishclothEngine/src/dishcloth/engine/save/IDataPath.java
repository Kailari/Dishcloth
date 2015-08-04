package dishcloth.engine.save;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * IDataPath.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 7.6.2015
 */
public interface IDataPath {

	void startWrite();
	void endWrite();

	void writeByte(byte b);
	void writeShort(short s);
	void writeInt(int i);
	void writeLong(long l);
	void writeFloat(float f);
	void writeDouble(double d);
	void writeString(String s);
	void writeBytes(byte[] bytes);

	void startRead();
	void endRead();

	byte readByte();
	short readShort();
	int readInt();
	long readLong();
	float readFloat();
	double readDouble();
	String readString();
	byte[] readBytes(int n);
}
