package dishcloth.engine.world.block;

import dishcloth.engine.io.save.IDataPath;
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

	private String mod;
	private String idString;

	// ID is dynamically assigned and may change depending on in which order the blocks are registered.
	private int id;

	protected BlockID(String mod, String idString, int id) {
		this.mod = mod;
		this.idString = idString;
		this.id = id;
	}

	public String getMod() {
		return mod;
	}

	public String getIDString() {
		return idString;
	}

	public int getID() {
		return id;
	}

	public static class SaveHandler implements ISaveWriter<BlockID, IDataPath>, ISaveReader<BlockID, IDataPath> {
		@Override
		public BlockID readFromFile(IDataPath path) {
			return new BlockID( path.readString(), path.readString(), path.readInt() );
		}

		@Override
		public void writeToFile(IDataPath path, BlockID data) {
			path.writeString( data.mod );
			path.writeString( data.idString );
			path.writeInt( data.id );
		}
	}
}
