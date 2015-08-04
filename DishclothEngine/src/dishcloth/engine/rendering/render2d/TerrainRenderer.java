package dishcloth.engine.rendering.render2d;

import dishcloth.engine.AGameEvents;
import dishcloth.engine.events.EventHandler;
import dishcloth.engine.exception.ShaderUniformException;
import dishcloth.engine.rendering.ICamera;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.render2d.sprites.batch.SpriteBatch;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vbo.TerrainVertex;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.world.block.BlockRegistry;
import dishcloth.engine.world.block.BlockTextureAtlas;
import dishcloth.engine.world.level.ITile;
import dishcloth.engine.world.level.TerrainChunk;

import java.util.ArrayList;
import java.util.List;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * TerrainRenderer.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 9.7.2015
 */

public class TerrainRenderer {

	private static List<ITile> renderQueue = new ArrayList<>();
	private static ShaderProgram shader;

	private static SpriteBatch<TerrainVertex> spriteBatch;

	private TerrainRenderer() {}

	@EventHandler
	public static void onGameContentInitializationEvent(AGameEvents.GameContentLoadingEvent event) {
		shader = event.contentManager.loadContent( "/engine/shaders/terrain.shader" );
		spriteBatch = new SpriteBatch<>( TerrainVertex.class );
	}


	@EventHandler
	public static void onGameContentDisposingEvent(AGameEvents.GameContentDisposingEvent event) {
		shader.dispose();
	}

	public static void queueTileForRendering(ITile tile) {
		renderQueue.add( tile );
	}

	public static void render(IRenderer renderer, ICamera camera) {

		spriteBatch.begin( shader, camera, renderer );

		// We handle shader binding manually.
		renderer.bindShader( shader );

		try {
			Texture texture = BlockTextureAtlas.getTexture();

			for (ITile tile : renderQueue) {
				shader.setUniformFloat( "tileSize", TerrainChunk.BLOCK_SIZE / texture.getWidth() );
				BlockRegistry.getBlock( tile.getBlockID() ).render( spriteBatch, tile );
			}
		} catch (ShaderUniformException e) {
			Debug.logException( e, "TerrainRenderer" );
		}

		spriteBatch.end( false );

		renderQueue.clear();
	}
}
