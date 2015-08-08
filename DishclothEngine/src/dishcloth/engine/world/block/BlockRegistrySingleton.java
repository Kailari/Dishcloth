package dishcloth.engine.world.block;

import dishcloth.api.abstractionlayer.events.EventHandler;
import dishcloth.api.world.block.ABlock;
import dishcloth.api.abstractionlayer.world.block.IBlockID;
import dishcloth.api.abstractionlayer.world.block.IBlockRegistry;
import dishcloth.api.events.GameEvents;
import dishcloth.api.util.ANSIColor;
import dishcloth.engine.ADishclothObject;
import dishcloth.engine.events.EventRegistry;
import dishcloth.engine.save.datapaths.AFileDataPath;
import dishcloth.engine.save.datapaths.BlockIDHeaderDataPath;
import dishcloth.engine.util.debug.Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <b>BlockRegistrySingleton</b>
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 7.8.2015 at 10:58
 */

public class BlockRegistrySingleton extends ADishclothObject implements IBlockRegistry {

	private static BlockRegistrySingleton instance;
	/**
	 * List for storing blocks loaded from disk until they are ready to be registered. To finally register blocks,
	 * AGame calls 'BlockRegistry.doBlockRegistration(...)'.
	 * <p>
	 * Block registration is done every time a save is loaded. This is done to allow saves
	 * to be compatible even if new blocks are registered.
	 */
	private List<TemporaryRegistryEntry> temporaryRegistryEntries = new ArrayList<>();
	private HashMap<IBlockID, ABlock> registryEntries = new HashMap<>();

	private BlockRegistrySingleton() {
		super( true );
	}

	public static BlockRegistrySingleton getInstance() {
		if (instance == null) {
			instance = new BlockRegistrySingleton();
		}
		return instance;
	}

	@EventHandler
	public void onGameContentInitializationEvent(GameEvents.GameContentLoadingEvent event) {
		doBlockRegistration( "dummy" );
	}

	@Override
	public ABlock getBlock(IBlockID blockID) {
		return registryEntries.get( blockID );
	}

	@Override
	public void registerBlock(ABlock block, String blockID, String modID) {
		// TODO: Blocks are registered one mod at a time. This allows us to auto-detect modID
		registerBlock( block, blockID, modID, null, null );
	}

	@Override
	public void registerBlock(ABlock block,
	                          String blockID, String modID,
	                          String fallbackBlockID, String fallbackModID) {
		temporaryRegistryEntries.add( new TemporaryRegistryEntry( block, blockID, modID, fallbackBlockID, fallbackModID ) );
	}

	@Override
	public List<ABlock> getRegisteredBlocks() {
		return new ArrayList<>( registryEntries.values() );
	}

	/**
	 * Registers all queued blocks.
	 *
	 * @param saveName name of the savefile
	 */
	private void doBlockRegistration(String saveName) {

		// Load BlockIDHeader
		AFileDataPath path = new BlockIDHeaderDataPath( "/saves/" + saveName + "/" );

		if (!path.fileIsEmpty()) {
			BlockIDHeaderSaveHandler.loadBlockIDHeader( path );
		}

		// Register all queued blocks
		// 'forEach' and method references <3
		temporaryRegistryEntries.forEach( this::registerBlock );

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

	private void applyFallbackID(IBlockID blockID, String fallbackMod, String fallbackID) {
		if (fallbackMod == null || fallbackID == null) {
			return;
		}

		// Register fallback ID
		BlockIDHandler.registerFallbackID( blockID, fallbackMod, fallbackID );

		// Force fallback if there is no block registered with given ID
		boolean matchFound = false;
		for (IBlockID registryBlockID : registryEntries.keySet()) {
			if (registryBlockID == blockID) {
				matchFound = true;
				break;
			}
		}
		if (!matchFound) {
			BlockIDHandler.forceFallback( blockID );
		}
	}

	private void registerBlock(TemporaryRegistryEntry entry) {
		IBlockID blockID = BlockIDHandler.createBlockID( entry.id, entry.mod );

		BlockHelper.setBlockID( entry.block, blockID );

		registryEntries.put( blockID, entry.block );

		BlockTextureAtlas.addBlock( entry.block );

		EventRegistry.fireEvent( new BlockEvents.OnBlockRegistrationEvent( blockID, entry.block ) );
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
