package dishcloth.engine.world.block;

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

	public static BlockID getBlockID(short id) {
		return BlockIDHandler.getBlockID( id );
	}

	public static BlockID getBlockID(String combinedID) {
		return getBlockID( combinedID.split( ":" )[0], combinedID.split( ":" )[1] );
	}

	public static BlockID getBlockID(String mod, String idString) {
		return BlockIDHandler.getBlockID( mod, idString );
	}
}
