package dishcloth.engine.world.block;

import dishcloth.engine.util.logger.Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * BlockIDHandler.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Ensures that block IDs are kept same, even if new blocks are registered and/or some of old blocks are removed.
 * <p>
 * No direct calls to this class should be necessary from game's code. All interaction should be done trough
 * BlockRegistry. Therefor, this class is made package local to minimize it's exposure.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 7.6.2015
 */

final class BlockIDHandler {
	private static BlockIDList IDs = new BlockIDList();
	private static List<Short> IDsWithForcedFallback = new ArrayList<>();
	private static short blockID_counter;

	private BlockIDHandler() {}

	protected static BlockID getBlockID(short id) {
		// -1 is the default fallback ID
		if (id == -1) {
			// TODO: Return air or something like that.
			return null;
		}

		if (IDsWithForcedFallback.contains( id )) {
			return getBlockID( IDs.get( id ).getFallbackID() );
		} else {
			return IDs.get( id );
		}
	}

	protected static BlockID getBlockID(String mod, String idString) {
		BlockID blockID = IDs.get( mod, idString );
		if (blockID != null) {
			if (IDsWithForcedFallback.contains( blockID.getID() )) {
				return getBlockID( blockID.getFallbackID() );
			} else {
				return blockID;
			}
		} else {
			return null;
		}
	}

	protected static void registerBlockID(BlockID id) {
		IDs.add( id );
	}

	protected static BlockID createBlockID(String mod, String id) {
		// Ensure that block hasn't been registered yet.
		if (IDs.get( mod, id ) != null) {
			Debug.logWarn( "Tried to re-register block! MOD:ID = " + mod + ":" + id, "BlockIDHandler" );
			return null;
		}
		BlockID blockID = new BlockID( mod, id, blockID_counter++, (short) -1 );
		registerBlockID( blockID );
		return blockID;
	}

	protected static void registerFallbackID(BlockID target, String fallbackMod, String fallbackID) {
		BlockID fallbackBlockID = IDs.get( fallbackMod, fallbackID );
		target.setFallbackID( fallbackBlockID.getID() );
		IDsWithForcedFallback.add( fallbackBlockID.getID() );
	}

	protected static void registerFallbackID(BlockID target, short fallback) {
		target.setFallbackID( fallback );
	}

	protected static void forceFallback(BlockID target) {
		IDsWithForcedFallback.add( target.getID() );
	}

	protected static List<BlockID> getBlockIDs() {
		return IDs.asList();
	}

	protected static void setBlockIDCounter(short blockIDCounter) {
		BlockIDHandler.blockID_counter = blockIDCounter;
	}
}
