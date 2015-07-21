package dishcloth.engine.world.block;

import dishcloth.engine.AGameEvents;
import dishcloth.engine.events.EventHandler;
import dishcloth.engine.events.EventRegistry;
import dishcloth.engine.io.save.datapaths.AFileDataPath;
import dishcloth.engine.io.save.datapaths.BlockIDHeaderDataPath;
import dishcloth.engine.util.logger.ANSIColor;
import dishcloth.engine.util.logger.Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * BlockRegistry.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Simplified registry for registering/storing/getting blocks by IDs or whatever you want to do with them.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 11.6.2015
 */

public final class BlockRegistry {

	/**
	 * List for storing blocks loaded from disk until they are ready to be registered. To finally register blocks,
	 * AGame calls 'BlockRegistry.doBlockRegistration(...)'.
	 * <p>
	 * Block registration is done every time a save is loaded. This is done to allow saves
	 * to be compatible even if new blocks are registered.
	 */
	private static List<TemporaryRegistryEntry> temporaryRegistryEntries = new ArrayList<>();
	private static HashMap<BlockID, ABlock> registryEntries = new HashMap<>();

	@EventHandler
	//public static void onGamePostInitializeEvent(AGameEvents.GamePostInitializationEvent event) {
	public static void onGameContentInitializationEvent(AGameEvents.GameContentInitializationEvent event) {
		doBlockRegistration( "dummy" );
	}

	public static ABlock getBlock(BlockID blockID) {
		return registryEntries.get( blockID );
	}

	/**
	 * Registers all queued blocks.
	 *
	 * @param saveName name of the savefile
	 */
	public static void doBlockRegistration(String saveName) {

		// Load BlockIDHeader
		AFileDataPath path = new BlockIDHeaderDataPath( "/saves/" + saveName + "/" );

		if (!path.fileIsEmpty()) {
			BlockIDHeaderSaveHandler.loadBlockIDHeader( path );
		}

		// Register all queued blocks
		// 'forEach' and method references <3
		temporaryRegistryEntries.forEach( BlockRegistry::registerBlock );

		// Apply fallback IDs
		for (TemporaryRegistryEntry entry : temporaryRegistryEntries) {
			BlockIDHandler.getBlockIDs().stream()
					.filter( blockID -> entry.mod.equals( blockID.getMod() )
							&& entry.id.equals( blockID.getIDString() ) )
					.forEach( blockID -> applyFallbackID( blockID, entry.fallbackModID, entry.fallbackID ) );
		}

		registryEntries.keySet().forEach( key -> Debug.logNote( "Registered block: "
				                                                        + ANSIColor.MAGENTA
				                                                        + key.toString()
				                                                        + ANSIColor.RESET, "BlocRegistry" ) );
	}

	private static void applyFallbackID(BlockID blockID, String fallbackMod, String fallbackID) {
		if (fallbackMod == null || fallbackID == null) {
			return;
		}

		// Register fallback ID
		BlockIDHandler.registerFallbackID( blockID, fallbackMod, fallbackID );

		// Force fallback if there is no block registered with given ID
		boolean matchFound = false;
		for (BlockID registryBlockID : registryEntries.keySet()) {
			if (registryBlockID == blockID) {
				matchFound = true;
				break;
			}
		}
		if (!matchFound) {
			BlockIDHandler.forceFallback( blockID );
		}
	}

	public static void registerBlock(ABlock block, String blockID, String modID) {
		// TODO: Blocks are registered one mod at a time. This allows us to auto-detect modID
		registerBlock( block, blockID, modID, null, null );
	}

	public static void registerBlock(ABlock block,
	                                 String blockID, String modID,
	                                 String fallbackBlockID, String fallbackModID) {
		temporaryRegistryEntries.add( new TemporaryRegistryEntry( block, blockID, modID, fallbackBlockID, fallbackModID ) );
	}

	private static void registerBlock(TemporaryRegistryEntry entry) {
		BlockID blockID = BlockIDHandler.createBlockID( entry.id, entry.mod );
		entry.block.setBlockID( blockID );
		registryEntries.put( blockID, entry.block );

		BlockTextureAtlas.addBlock( entry.block );

		//EventRegistry.fireEvent( new BlockEvents.OnBlockRegistrationEvent( blockID, entry.block ) );
	}

	public static List<ABlock> getRegisteredBlocks() {
		return new ArrayList<>( registryEntries.values() );
	}

	private static class TemporaryRegistryEntry {
		private final ABlock block;
		private final String id;
		private final String mod;
		private final String fallbackID;
		private final String fallbackModID;

		public TemporaryRegistryEntry(ABlock block, String id, String mod, String fallbackID, String fallbackModID) {
			this.block = block;
			this.id = id;
			this.mod = mod;
			this.fallbackID = fallbackID;
			this.fallbackModID = fallbackModID;
		}
	}
}
