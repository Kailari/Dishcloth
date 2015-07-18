package dishcloth.engine.world.level;

import dishcloth.engine.AGame;
import dishcloth.engine.exception.ShaderUniformException;
import dishcloth.engine.rendering.ICamera;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.OrthographicCamera;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vbo.Vertex;
import dishcloth.engine.rendering.vbo.VertexBufferObject;
import dishcloth.engine.rendering.vbo.shapes.Quad;
import dishcloth.engine.util.Color;
import dishcloth.engine.util.geom.Rectangle;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.util.math.DishMath;
import dishcloth.engine.util.math.MatrixUtility;
import dishcloth.engine.world.ITile;
import dishcloth.engine.world.block.BlockID;
import dishcloth.engine.world.block.BlockRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

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
	private static Texture blockAtlas;
	private static ShaderProgram shader;
	private static AGame activeGame;

	private TerrainRenderer() {}

	public static void initialize(AGame game) {
		prepareShader();
		prepareTexture();
		prepareVBOs();
		activeGame = game;
	}

	private static void prepareShader() {
		shader = new ShaderProgram( "/engine/shaders/terrain", "/engine/shaders/default" );
	}

	private static void prepareTexture() {
		// TODO: Generate blockAtlas from registered blocks
		blockAtlas = new Texture( "game/textures/blocks/dirt.png" );
	}

	private static void prepareVBOs() {
		for (int i = 0; i < VBOs.length; i++) {
			float size = (float) Math.pow( 2, i );
			VBOs[i] = new VertexBufferObject( new Quad( new Rectangle( 0f,
			                                                           0f,
			                                                           size * TerrainChunk.BLOCK_SIZE,
			                                                           size * TerrainChunk.BLOCK_SIZE ) ) );

			sizeLookup.put( Math.round( size ), i );
		}
	}

	public static void queueTileForRendering(ITile tile) {
		renderQueue.add( tile );
	}

	public static void render(IRenderer renderer) {
		renderer.bindTexture( blockAtlas );
		renderer.bindShader( shader );

		try {
			shader.setUniformMat4( "mat_project", activeGame.getViewportCamera().getProjectionMatrix() );
			shader.setUniformMat4( "mat_view", activeGame.getViewportCamera().getViewMatrix() );
		} catch (ShaderUniformException e) {
			Debug.logException( e, "TerrainRenderer" );
			return;
		}

		renderQueue.forEach( TerrainRenderer::render );

		renderer.bindShader( 0 );
		renderer.bindTexture( 0 );

		renderQueue.clear();
	}

	// TODO: Move this shit over to the block or add some way for block to influence its rendering
	private static void render(ITile tile) {
		// Air tiles' blockIDs are null and they do not need to be rendered
		if (tile.getBlockID() == null) {
			return;
		}

		int atlasSize = 32 - Integer.numberOfLeadingZeros( blockAtlas.getWidth() - 1 );
		int index = tile.getBlockID().getID();

		int row = (int) Math.floor( (float) index / (float) atlasSize );
		int column = index % atlasSize;

		try {
			shader.setUniformVec4f( "subtexture",
			                        (float) column / (float) atlasSize,
			                        (float) row / (float) atlasSize,
			                        TerrainChunk.BLOCK_SIZE / blockAtlas.getWidth(),
			                        TerrainChunk.BLOCK_SIZE / blockAtlas.getWidth() );

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
