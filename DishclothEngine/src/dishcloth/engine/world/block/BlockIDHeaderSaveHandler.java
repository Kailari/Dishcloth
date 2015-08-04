package dishcloth.engine.world.block;

import dishcloth.engine.save.IDataPath;

import java.util.List;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * BlockIDHeaderSaveHandler.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 21.6.2015
 */

public class BlockIDHeaderSaveHandler {
	static void loadBlockIDHeader(IDataPath path) {
		// Read size
		int size = path.readInt();
		// Find largest ID from header so we know where to start registering blocks
		short largest = 0;
		for (int i = 0; i < size; i++) {
			BlockID id = BlockID.saveHandler.readFromDataPath( path );
			// Update largest if needed
			largest = (id.getID() > largest ? id.getID() : largest);

			BlockIDHandler.registerBlockID( id );
			if (id.getFallbackID() != -1) {
				BlockIDHandler.registerFallbackID( id, id.getFallbackID() );
			}
		}

		BlockIDHandler.setBlockIDCounter( largest );
	}

	private static void writeBlockID(BlockID id, IDataPath path) {
		BlockID.saveHandler.writeToDataPath( path, id );
	}

	void saveBlockIDHeader(IDataPath path) {
		List<BlockID> blocks = BlockIDHandler.getBlockIDs();
		path.writeInt( blocks.size() );

		blocks.forEach( blockID -> writeBlockID( blockID, path ) );
	}
}
