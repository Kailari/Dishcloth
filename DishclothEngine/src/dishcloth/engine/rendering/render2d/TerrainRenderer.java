package dishcloth.engine.rendering.render2d;

import dishcloth.api.abstractionlayer.events.EventHandler;
import dishcloth.api.events.GameEvents;
import dishcloth.api.exception.ShaderUniformException;
import dishcloth.api.abstractionlayer.rendering.ICamera;
import dishcloth.api.abstractionlayer.rendering.IRenderer;
import dishcloth.engine.rendering.render2d.sprites.batch.SpriteBatch;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vao.vertex.ColorTextureVertex;
import dishcloth.engine.rendering.vao.vertex.VertexFormat;
import dishcloth.engine.util.debug.Debug;
import dishcloth.engine.world.block.BlockHelper;
import dishcloth.engine.world.block.BlockTextureAtlas;
import dishcloth.api.abstractionlayer.world.level.ITile;
import dishcloth.engine.world.level.TerrainChunk;
import org.lwjgl.opengl.GL11;

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

	private static SpriteBatch spriteBatch;

	private TerrainRenderer() {}

	@EventHandler
	public static void onGameContentInitializationEvent(GameEvents.GameContentLoadingEvent event) {
		shader = event.contentManager.loadContent( "/engine/shaders/terrain.shader" );
		spriteBatch = new SpriteBatch( new VertexFormat(
				new VertexFormat.VertexAttribute( ColorTextureVertex.POSITION_SIZE, ColorTextureVertex.POSITION_TYPE, ColorTextureVertex.SIZE_IN_BYTES, ColorTextureVertex.POSITION_OFFSET, false ),
				new VertexFormat.VertexAttribute( 2, GL11.GL_SHORT, ColorTextureVertex.SIZE_IN_BYTES, ColorTextureVertex.COLOR_OFFSET, true ),
				new VertexFormat.VertexAttribute( ColorTextureVertex.UV_SIZE, ColorTextureVertex.UV_TYPE, ColorTextureVertex.SIZE_IN_BYTES, ColorTextureVertex.UV_OFFSET, false )
		) );
	}

	@EventHandler
	public static void onGameContentDisposingEvent(GameEvents.GameContentDisposingEvent event) {
		shader.dispose();
	}

	public static void beginRender(IRenderer renderer, ICamera camera) {

		spriteBatch.begin( shader, camera, renderer );

		// We handle shader binding manually.
		renderer.bindShader( shader );

		try {
			Texture texture = BlockTextureAtlas.getTexture();

			shader.setUniformFloat( "tileSize", TerrainChunk.BLOCK_SIZE / texture.getWidth() );

		} catch (ShaderUniformException e) {
			Debug.logException( e, "TerrainRenderer" );
		}
	}

	public static void queueTileForRendering(ITile tile, int chunkX, int chunkY) {
		BlockHelper.renderBlock( spriteBatch, tile, chunkX, chunkY );
	}

	public static void endRender() {
		spriteBatch.end( false );

		renderQueue.clear();
	}
}
