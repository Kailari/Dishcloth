package dishcloth.engine.world.block;

import dishcloth.api.abstractionlayer.world.block.IBlockID;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * BlockIDHelper.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Helper functions for converting numeric or literal blockIDs to BlockIDs.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 24.6.2015
 */

public class BlockIDHelper {
	private BlockIDHelper() {}

	public static IBlockID getBlockID(short id) {
		return BlockIDHandler.getBlockID( id );
	}

	public static IBlockID getBlockID(String combinedID) {
		return getBlockID( combinedID.split( ":" )[0], combinedID.split( ":" )[1] );
	}

	public static IBlockID getBlockID(String mod, String idString) {
		return BlockIDHandler.getBlockID( mod, idString );
	}
}
