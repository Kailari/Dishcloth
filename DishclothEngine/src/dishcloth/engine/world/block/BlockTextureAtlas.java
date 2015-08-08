package dishcloth.engine.world.block;

import dishcloth.api.abstractionlayer.events.EventHandler;
import dishcloth.api.events.GameEvents;
import dishcloth.api.world.block.ABlock;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.textures.TextureAtlasBuilder;
import dishcloth.engine.world.level.TerrainChunk;

/**
 * BlockTextureAtlas.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 20.7.2015
 */

public class BlockTextureAtlas {
	private static boolean isBuilt = false;
	private static TextureAtlasBuilder builder = new TextureAtlasBuilder( Math.round( TerrainChunk.BLOCK_SIZE ) );
	private static Texture texture;

	private BlockTextureAtlas() {}

	@EventHandler
	public static void onGameDisposingEvent(GameEvents.GameContentDisposingEvent event) {
		dispose();
	}

	/**
	 * Adds block's texture to atlas and assigns block a frameID.
	 */
	public static void addBlock(ABlock block) {
		BlockHelper.setFrameID( block, addTexture( block.getBlockTextureFilename() ) );
	}

	/**
	 * Manually adds a new texture and returns its frameID
	 */
	public static int addTexture(String filename) {
		isBuilt = false;
		return builder.addTexture( filename );
	}

	public static Texture getTexture() {
		if (!isBuilt) {
			build();
		}

		return texture;
	}

	private static void build() {
		dispose();
		texture = builder.build();
		isBuilt = true;
	}

	public static void dispose() {
		if (texture != null) {
			texture.dispose();
			texture = null;
		}
	}

	public static void clearTexture() {
		// Clear the builder
		builder = new TextureAtlasBuilder( Math.round( TerrainChunk.BLOCK_SIZE ) );

		// Dispose the texture
		dispose();

		// Reset fields
		isBuilt = false;
	}
}
