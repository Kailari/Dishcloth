package dishcloth.engine.world.level;

import dishcloth.engine.AGame;
import dishcloth.engine.AGameEvents;
import dishcloth.engine.events.EventHandler;
import dishcloth.engine.exception.ShaderUniformException;
import dishcloth.engine.input.InputHandler;
import dishcloth.engine.input.KeyCode;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.OrthographicCamera;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.textures.TextureAtlasBuilder;
import dishcloth.engine.rendering.vbo.Vertex;
import dishcloth.engine.rendering.vbo.VertexBufferObject;
import dishcloth.engine.rendering.vbo.shapes.Polygon;
import dishcloth.engine.util.Color;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.util.math.DishMath;
import dishcloth.engine.util.math.MatrixUtility;
import dishcloth.engine.world.block.BlockRegistry;
import dishcloth.engine.world.block.BlockTextureAtlas;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashMap;
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
	// VBOs for all sizes from 1 to chunkSize
	// Number of different sizes is 1 + 2^n, where n=[0, log2(CHUNK_SIZE)]
	private static VertexBufferObject[] VBOs
			= new VertexBufferObject[DishMath.nearestPowerOfTwo( TerrainChunk.CHUNK_SIZE ) + 1];
	private static List<ITile> renderQueue = new ArrayList<>();
	// <size, VBO-index>
	private static HashMap<Integer, Integer> sizeLookup = new HashMap<>();
	private static ShaderProgram shader;
	private static ShaderProgram shader2;
	private static AGame activeGame;
	private static Texture tmpAtlas;
	private static boolean lastSpaceState = false;

	private TerrainRenderer() {}

	@EventHandler
	public static void onGameContentInitializationEvent(AGameEvents.GameContentInitializationEvent event) {
		prepareShader();

		// TextureAtlas cannot be built yet because it relies on BlockRegistry which does BlockRegistration in
		// GamePostInitializationEvent
	}

	@EventHandler
	public static void onGamePostInitializationEvent(AGameEvents.GamePostInitializationEvent event) {
		prepareVBOs();
		activeGame = event.getGame();
	}

	@EventHandler
	public static void onGameContentDisposingEvent(AGameEvents.GameContentDisposingEvent event) {
		shader.dispose();
	}

	private static void prepareShader() {
		shader = new ShaderProgram( "/engine/shaders/terrain", "/engine/shaders/terrain" );
		// Uncomment this to disable texture tiling. (Useful for debugging tileset generation)
		shader2 = new ShaderProgram( "/engine/shaders/terrain", "/engine/shaders/default" );
	}

	private static void prepareVBOs() {
		for (int i = 0; i < VBOs.length; i++) {
			float size = (float) Math.pow( 2, i );

			VBOs[i] = new VertexBufferObject(
					new Polygon(
							new Vertex( 0f, 0f, 0f, size ),
							new Vertex( size * TerrainChunk.BLOCK_SIZE, 0f, size, size ),
							new Vertex( size * TerrainChunk.BLOCK_SIZE, size * TerrainChunk.BLOCK_SIZE, size, 0f ),
							new Vertex( 0f, size * TerrainChunk.BLOCK_SIZE, 0f, 0f ) )
			);

			sizeLookup.put( Math.round( size ), i );
		}
	}

	public static void queueTileForRendering(ITile tile) {
		renderQueue.add( tile );
	}

	public static void render(IRenderer renderer) {
		tmpAtlas = BlockTextureAtlas.getTexture();

		if (InputHandler.getKey( KeyCode.KEY_SPACE ) == GLFW.GLFW_PRESS && !lastSpaceState) {
			ShaderProgram tmp = shader;
			shader = shader2;
			shader2 = tmp;
			lastSpaceState = true;
		}
		if (InputHandler.getKey( KeyCode.KEY_SPACE ) == GLFW.GLFW_RELEASE) {
			lastSpaceState = false;
		}

		renderer.bindTexture( tmpAtlas );
		renderer.bindShader( shader );

		try {
			shader.setUniformMat4( "mat_project", activeGame.getViewportCamera().getProjectionMatrix() );
			shader.setUniformMat4( "mat_view", activeGame.getViewportCamera().getViewMatrix() );
		} catch (ShaderUniformException e) {
			Debug.logException( e, "TerrainRenderer" );
			return;
		}

		renderQueue.forEach( tile -> BlockRegistry.getBlock( tile.getBlockID() ).render( tile ) );

		renderer.bindShader( 0 );
		renderer.bindTexture( 0 );

		renderQueue.clear();

		tmpAtlas = null;
	}

	public static void renderTile(int frameID, ITile tile) {
		// Air tiles' blockIDs are null and they do not need to be rendered
		if (tile.getBlockID() == null) {
			return;
		}

		int atlasSize = (int) Math.floor( tmpAtlas.getWidth() / TerrainChunk.BLOCK_SIZE );

		int row = (int) Math.floor( (float) frameID / (float) atlasSize );
		int column = frameID % atlasSize;

		try {
			shader.setUniformVec4f( "subtexture",
			                        (float) column / (float) atlasSize,
			                        (float) row / (float) atlasSize,
			                        TerrainChunk.BLOCK_SIZE / tmpAtlas.getWidth(),
			                        TerrainChunk.BLOCK_SIZE / tmpAtlas.getWidth() );

			shader.setUniformMat4( "mat_modelview",
			                       MatrixUtility.createTranslation( tile.getX() * TerrainChunk.BLOCK_SIZE,
			                                                        tile.getY() * TerrainChunk.BLOCK_SIZE,
			                                                        0f ) );

			shader.setUniformVec4f( "color_tint",
			                        Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, Color.WHITE.a );

		} catch (ShaderUniformException e) {
			Debug.logException( e, "TerrainRenderer" );
			return;
		}

		VBOs[sizeLookup.get( tile.getSize() )].render();
	}

	public static OrthographicCamera getCamera() {
		return (OrthographicCamera) activeGame.getViewportCamera();
	}
}
