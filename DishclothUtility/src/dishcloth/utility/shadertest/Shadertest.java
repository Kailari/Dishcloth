package dishcloth.utility.shadertest;

import dishcloth.api.abstractionlayer.content.IContentManager;
import dishcloth.api.abstractionlayer.events.EventHandler;
import dishcloth.api.abstractionlayer.rendering.IRenderer;
import dishcloth.engine.rendering.vao.shapes.Polygon;
import dishcloth.engine.rendering.vao.shapes.SimpleQuad;
import dishcloth.engine.rendering.vao.shapes.SimpleRegularNGon;
import dishcloth.api.exception.ShaderUniformException;
import dishcloth.engine.rendering.vao.vertex.ColorTextureVertex;
import dishcloth.api.util.memory.RectangleCache;
import dishcloth.engine.AGame;
import dishcloth.engine.input.InputHandler;
import dishcloth.engine.input.KeyCode;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vao.VertexArrayObject;
import dishcloth.api.util.Color;
import dishcloth.engine.util.debug.Debug;
import dishcloth.engine.world.block.BlockRegistry;
import dishcloth.engine.world.block.BlockTextureAtlas;
import dishcloth.game.world.blocks.DishclothBlocks;

/**
 * Shadertest.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 21.7.2015
 */

public class Shadertest extends AGame {
	private static final String[] shaderFiles = new String[]{
			"/engine/shaders/test.shader",
			"/engine/shaders/default.shader",
			"/engine/shaders/terrain.shader",
	};
	
	private static final String[] textureFiles = new String[]{
			"/engine/textures/logger/uv_checker.png",
	};
	
	private ShaderProgram[] shaders;
	private Texture[] textures;
	private VertexArrayObject[] VAO;
	private boolean spaceResetFlag;
	private boolean enterResetFlag;
	private boolean tabResetFlag;
	private int activeShader;
	private int activeTexture;
	private int activeVAO;
	
	@Override
	@SuppressWarnings("unchecked")
	public void initialize() {
		float w = 500f;
		float h = 500f;
		VAO = new VertexArrayObject[]{
				new VertexArrayObject( new SimpleQuad( w, h ) ),
				new VertexArrayObject( new SimpleRegularNGon( 8, w / 2f ) ),
				new VertexArrayObject( new Polygon<>( new ColorTextureVertex[]{
						new ColorTextureVertex( -w / 2f, -h / 2f, Color.WHITE.toInteger(), 0f, 32f ),     // Left bot
						new ColorTextureVertex( +w / 2f, -h / 2f, Color.WHITE.toInteger(), 32f, 32f ),     // right top
						new ColorTextureVertex( +w / 2f, +h / 2f, Color.WHITE.toInteger(), 32f, 0f ),     // Right bottom
						new ColorTextureVertex( -w / 2f, +h / 2f, Color.WHITE.toInteger(), 0f, 0f )} ) ), // Left top
				new VertexArrayObject( new SimpleQuad( RectangleCache.getRectangle( -w / 2f, -h / 2f, w, h ) ) )
		};
	}
	
	@Override
	public void loadContent(IContentManager contentManager) {

		BlockRegistry.registerBlock( DishclothBlocks.DIRT, "dishcloth", "dirt" );
		BlockRegistry.registerBlock( DishclothBlocks.GRASS, "dishcloth", "grass" );
		BlockRegistry.registerBlock( DishclothBlocks.CLAY, "dishcloth", "clay" );
		BlockRegistry.registerBlock( DishclothBlocks.STONE, "dishcloth", "stone" );
		BlockRegistry.registerBlock( DishclothBlocks.BRICKS, "dishcloth", "bricks" );

		createShaders( contentManager );
		loadTextures( contentManager );
	}
	
	private void loadTextures(IContentManager contentManager) {
		textures = new Texture[textureFiles.length];
		for (int i = 0; i < textureFiles.length; i++) {
			textures[i] = contentManager.loadContent( textureFiles[i] );
		}
	}
	
	private void createShaders(IContentManager contentManager) {
		shaders = new ShaderProgram[(int) Math.floor( shaderFiles.length / 2f )];
		
		int j = 0;
		for (int i = 0; i < shaderFiles.length; i += 2, j++) {
			Debug.log( "Creating shader #" + j, this );
			shaders[j] = contentManager.loadContent( shaderFiles[i] );
		}
	}
	
	
	@Override
	public void update(float delta) {
		if (!spaceResetFlag && InputHandler.getKeyPress( KeyCode.KEY_SPACE )) {
			activeShader++;
			if (activeShader >= shaders.length) {
				activeShader = 0;
			}
			Debug.log( "Swapping shaders! New shader index: " + activeShader, this );
			
			spaceResetFlag = true;
		} else if (InputHandler.getKeyPress( KeyCode.KEY_SPACE )) {
			spaceResetFlag = false;
		}
		
		if (!enterResetFlag && InputHandler.getKeyPress( KeyCode.KEY_ENTER )) {
			activeTexture++;
			if (activeTexture > textures.length) {
				activeTexture = 0;
			}
			Debug.log( "Swapping textures! New texture: " + activeTexture + ":"
					           + (activeTexture < textures.length
					? textureFiles[activeTexture]
					: "BlockTextureAtlas"), this );
			
			enterResetFlag = true;
		} else if (InputHandler.getKeyPress( KeyCode.KEY_ENTER )) {
			enterResetFlag = false;
		}
		
		if (!tabResetFlag && InputHandler.getKeyPress( KeyCode.KEY_TAB )) {
			activeVAO++;
			if (activeVAO >= VAO.length) {
				activeVAO = 0;
			}
			Debug.log( "Swapping VAO! New VAO index: " + activeVAO, this );
			
			tabResetFlag = true;
		} else if (InputHandler.getKeyPress( KeyCode.KEY_TAB )) {
			tabResetFlag = false;
		}
	}
	
	@Override
	public void fixedUpdate() {
		
	}
	
	@Override
	public void render(IRenderer renderer) {
		renderer.bindShader( shaders[activeShader] );
		if (activeTexture < textures.length) {
			renderer.bindTexture( textures[activeTexture] );
		} else {
			renderer.bindTexture( BlockTextureAtlas.getTexture() );
		}
		
		try {
			shaders[activeShader].setUniformMat4( "mat_project", getViewportCamera().getProjectionMatrix() );
			shaders[activeShader].setUniformMat4( "mat_view", getViewportCamera().getCameraTransformMatrix() );
		} catch (ShaderUniformException e) {
			Debug.logException( e, this );
		}
		
		VAO[activeVAO].render();
		
		renderer.bindTexture( 0 );
		renderer.bindShader( 0 );
	}
	
	@Override
	public void unloadContent() {
		disposeContent();
	}
	
	private void disposeContent() {
		for (ShaderProgram shader : shaders) {
			shader.dispose();
		}
		
		for (Texture t : textures) {
			t.dispose();
		}
		
		for (VertexArrayObject vao : VAO) {
			vao.dispose();
		}
	}
	
	@Override
	public void shutdown() {
		
	}
	
	@EventHandler
	public void onPostInitializeEvent() {
		textures[textures.length - 1] = BlockTextureAtlas.getTexture();
	}
}
