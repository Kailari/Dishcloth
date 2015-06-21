package dishcloth.engine.world.block;

import dishcloth.engine.io.save.datapaths.BlockIDHeaderDataPath;
import dishcloth.engine.util.logger.Debug;

import java.util.HashMap;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * BlockIDHandler.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Ensures that block IDs are kept same, even if new blocks are registered
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 7.6.2015
 */

public class BlockIDHandler {

	private static BlockIDHeaderDataPath path;
	private static HashMap<_BlockID, IBlock> blocks = new HashMap<>();
	private static HashMap<String, HashMap<String, _BlockID>> idStrings = new HashMap<>();
	private static HashMap<Short, _BlockID> IDs = new HashMap<>();
	private static short blockID_counter;
	private BlockIDHandler() {}

	public static BlockID getBlockID(int id) {
		return (IDs.containsKey( id ) ? IDs.get( id ) : null);
	}

	public static BlockID getBlockID(String s) {
		String[] split = s.split( ":" );
		if (split.length == 2) {
			return getBlockID( split[0], split[1] );
		}

		return null;
	}

	public static BlockID getBlockID(String mod, String idString) {
		return idStrings.get( mod ).get( idString );
	}

	public static IBlock getBlock(int id) {
		return getBlock( getBlockID( id ) );
	}

	public static IBlock getBlock(BlockID blockID) {
		return (blocks.containsKey( (_BlockID) blockID ) ? blocks.get( blockID ) : null);
	}

	public static IBlock getBlock(String mod, String idString) {
		return getBlock( getBlockID( mod, idString ) );
	}

	public static IBlock getBlock(String s) {
		return getBlock( getBlockID( s ) );
	}

	private static void putIDStringOfBlockID(_BlockID id) {
		if (!idStrings.containsKey( id.getMod() )) {
			idStrings.put( id.getMod(), new HashMap<>() );
		}

		HashMap<String, _BlockID> strings = idStrings.get( id.getMod() );

		strings.put( id.getIDString(), id );

		idStrings.put( id.getMod(), strings );
	}

	/**
	 * MUST BE CALLED BEFORE ANY KIND OF BLOCK REGISTRATION IS DONE
	 *
	 * @param saveName name of the save-folder
	 */
	public static void initialize(String saveName) {
		path = new BlockIDHeaderDataPath( "/saves/" + saveName + "/" );

		if (!path.fileIsEmpty()) {
			loadBlockIDHeader();
		}
	}

	public static BlockID registerBlock(IBlock block, String blockID, String mod) {
		assert block != null;

		// Ensure that block hasn't been registered yet.
		if (blocks.values().contains( block )) {
			Debug.logWarn( "Tried to re-register block! MOD:ID = " + mod + ":" + blockID, "BlockIDHandler" );
			return null;
		}

		_BlockID id;

		// Check if given mod:id was loaded from header.
		if (idStrings.containsKey( mod ) && idStrings.get( mod ).containsKey( blockID )) {
			id = idStrings.get( mod ).get( blockID );
		} else {
			id = new _BlockID( mod, blockID, blockID_counter++ );
			IDs.put( id.getID(), id );

			putIDStringOfBlockID( id );
		}

		blocks.put( id, block );

		return id;
	}


	public static void saveBlockIDHeader() {
		path.startWrite();
		path.writeInt( blocks.size() );
		blocks.keySet().forEach( dishcloth.engine.world.block.BlockIDHandler::writeBlockID );
		path.endWrite();
	}

	private static void writeBlockID(_BlockID id) {
		BlockID.saveHandler.writeToFile( path, id );
	}

	public static void loadBlockIDHeader() {
		path.startRead();
		int size = path.readInt();
		short largest = 0;
		for (int i = 0; i < size; i++) {
			_BlockID id = (_BlockID) BlockID.saveHandler.readFromFile( path );
			largest = (id.getID() > largest ? id.getID() : largest);

			IDs.put( id.getID(), id );

			putIDStringOfBlockID( id );
		}
		path.endRead();

		blockID_counter = largest;
	}

	// Override for using protected constructor
	// This is done to prevent initializing BlockIDs from anywhere else than here
	// (OFC, if somebody REALLY wants to initialize one, it is still possible to override the class elsewhere)
	private static class _BlockID extends BlockID {
		private _BlockID(String mod, String idString, short id) {
			super( mod, idString, id );
		}
	}
}
