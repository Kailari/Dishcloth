package dishcloth.engine.world.block;

import dishcloth.api.abstractionlayer.world.block.IBlockID;
import dishcloth.engine.save.IDataPath;
import dishcloth.engine.save.ISaveHandler;

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

public class BlockID implements IBlockID {

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

	@Override
	public String getMod() {
		return mod;
	}

	@Override
	public String getIDString() {
		return idString;
	}

	@Override
	public short getID() {
		return id;
	}

	@Override
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
