package dishcloth.engine.io.file.dishdata.headers;

import java.util.Arrays;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * DefaultHeaders.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 13.6.2015
 */
public enum DefaultHeaders implements IHeaderDefinition {

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* ROOT KEY DEFINITIONS */
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Header indicating that file defines a new block.
	 */
	BLOCK( "block", "blockInfo", "sprites", "sounds" ),
	/**
	 * Header indicating that file defines a new texture.
	 */
	SPRITE( "sprite", "spriteInfo", "textures" ),
	/**
	 * Header indicating that file defines a new sound
	 */
	SOUNDEFFECT( "soundEffect", "soundEffectInfo", "sounds" ),
	/**
	 * Header indicating that file is the main/default configuration file.
	 */
	CONFIG( "config", "input", "video", "game", "debug", "multiplayer" ),

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* SUBKEYS */
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/* BLOCK */

	BLOCK_INFO( "blockInfo" ),
	BLOCK_SPRITES( "sprites" ),
	BLOCK_SOUNDS( "sounds" ),


	/* SPRITE */

	SPRITE_INFO( "spriteInfo" ),
	SPRITE_TEXTURES( "textures" ),

	/* SOUNDEFFECT */

	SOUNDEFFECT_INFO( "soundEffectInfo" ),
	SOUNDEFFECT_SOUNDS( "sounds" ),

	/* CONFIG */

	CONFIG_INPUT( "input" ),
	CONFIG_VIDEO( "video" ),
	CONFIG_GAME( "game" ),
	CONFIG_DEBUG( "debug" ),
	CONFIG_MULTIPLAYER( "multiplayer" );

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Register
	static {
		HeaderRegistry.registerHeaders( DefaultHeaders.values() );
	}

	private final String key;
	private final String[] subKeys;
	
	DefaultHeaders(String key, String... subKeys) {
		this.key = key;
		this.subKeys = subKeys;
	}
	
	
	@Override
	public String getKey() {
		return this.key;
	}
	
	@Override
	public String[] getCompatibleSubkeys() {
		return this.subKeys;
	}
	
	@Override
	public boolean isSubKey(String subKey) {
		return Arrays.asList( this.subKeys ).contains( subKey );
	}
}