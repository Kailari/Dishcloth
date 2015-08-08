package dishcloth.engine.world.block;

import dishcloth.api.abstractionlayer.world.block.IBlockID;
import dishcloth.engine.util.debug.Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * IBlockIDHandler.java
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

	protected static IBlockID getBlockID(short id) {
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

	protected static IBlockID getBlockID(String mod, String idString) {
		IBlockID blockID = IDs.get( mod, idString );
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

	protected static void registerBlockID(IBlockID id) {
		IDs.add( id );
	}

	protected static IBlockID createBlockID(String mod, String id) {
		// Ensure that block hasn't been registered yet.
		if (IDs.get( mod, id ) != null) {
			Debug.logWarn( "Tried to re-register block! MOD:ID = " + mod + ":" + id, "IBlockIDHandler" );
			return null;
		}
		IBlockID blockID = new BlockID( mod, id, blockID_counter++, (short) -1 );
		registerBlockID( blockID );
		return blockID;
	}

	protected static void registerFallbackID(IBlockID target, String fallbackMod, String fallbackID) {
		IBlockID fallbackIBlockID = IDs.get( fallbackMod, fallbackID );
		((BlockID)target).setFallbackID( fallbackIBlockID.getID() );
		IDsWithForcedFallback.add( fallbackIBlockID.getID() );
	}

	protected static void registerFallbackID(IBlockID target, short fallback) {
		((BlockID)target).setFallbackID( fallback );
	}

	protected static void forceFallback(IBlockID target) {
		IDsWithForcedFallback.add( target.getID() );
	}

	protected static List<IBlockID> getBlockIDs() {
		return IDs.asList();
	}

	protected static void setBlockIDCounter(short blockIDCounter) {
		BlockIDHandler.blockID_counter = blockIDCounter;
	}
}
