package dishcloth.engine.world.block;

import dishcloth.engine.io.save.IDataPath;
import dishcloth.engine.io.save.ISaveHandler;
import dishcloth.engine.io.save.ISaveReader;
import dishcloth.engine.io.save.ISaveWriter;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * BlockID.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 7.6.2015
 */

public class BlockID {

	public static final SaveHandler saveHandler = new SaveHandler();

	private final String mod;
	private final String idString;

	// ID is dynamically assigned and may change depending on in which order the blocks are registered.
	private short id;
	private short fallbackID;

	protected BlockID(String mod, String idString, short id, short fallbackID) {
		this.mod = mod;
		this.idString = idString;
		this.id = id;
		this.fallbackID = fallbackID;
	}

	public String getMod() {
		return mod;
	}

	public String getIDString() {
		return idString;
	}

	public short getID() {
		return id;
	}

	public short getFallbackID() {
		return fallbackID;
	}

	protected void setFallbackID(short newFallback) {
		this.fallbackID = newFallback;
	}

	@Override
	public String toString() {
		return "BlockID{" +
				"mod='" + mod + '\'' +
				", idString='" + idString + '\'' +
				", id=" + id +
				'}';
	}

	public static class SaveHandler implements ISaveHandler<BlockID, IDataPath> {
		@Override
		public BlockID readFromDataPath(IDataPath path) {
			return new BlockID( path.readString(), path.readString(), path.readShort(), path.readShort() );
		}

		@Override
		public void writeToDataPath(IDataPath path, BlockID data) {
			path.writeString( data.mod );
			path.writeString( data.idString );
			path.writeInt( data.id );
			path.writeShort( data.fallbackID );
		}
	}
}
