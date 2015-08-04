package dishcloth.utility.shadertest;

import dishcloth.engine.AGame;
import dishcloth.engine.events.EventHandler;
import dishcloth.engine.exception.ShaderUniformException;
import dishcloth.engine.input.InputHandler;
import dishcloth.engine.input.KeyCode;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vbo.ColorTextureVertex;
import dishcloth.engine.rendering.vbo.Vertex;
import dishcloth.engine.rendering.vbo.VertexArrayObject;
import dishcloth.engine.rendering.vbo.shapes.Polygon;
import dishcloth.engine.rendering.vbo.shapes.SimpleQuad;
import dishcloth.engine.rendering.vbo.shapes.SimpleRegularNGon;
import dishcloth.engine.util.Color;
import dishcloth.engine.util.geom.Rectangle;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.world.block.BlockRegistry;
import dishcloth.engine.world.block.BlockTextureAtlas;
import dishcloth.game.world.blocks.DishclothBlocks;
import org.lwjgl.glfw.GLFW;

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
			"/engine/shaders/test", "/engine/shaders/test",
			"/engine/shaders/default", "/engine/shaders/default",
			"/engine/shaders/terrain", "/engine/shaders/terrain",
	};
	
	private static final String[] textureFiles = new String[]{
			"/engine/textures/debug/uv_checker.png",
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
				new VertexArrayObject( new SimpleQuad( new Rectangle( -w / 2f, -h / 2f, w, h ) ) )
		};
	}
	
	@Override
	public void loadContent() {
		createShaders();
		
		BlockRegistry.registerBlock( DishclothBlocks.DIRT, "dishcloth", "dirt" );
		BlockRegistry.registerBlock( DishclothBlocks.GRASS, "dishcloth", "grass" );
		BlockRegistry.registerBlock( DishclothBlocks.CLAY, "dishcloth", "clay" );
		BlockRegistry.registerBlock( DishclothBlocks.STONE, "dishcloth", "stone" );
		BlockRegistry.registerBlock( DishclothBlocks.BRICKS, "dishcloth", "bricks" );
		
		loadTextures();
	}
	
	private void loadTextures() {
		textures = new Texture[textureFiles.length];
		for (int i = 0; i < textureFiles.length; i++) {
			textures[i] = new Texture( textureFiles[i] );
		}
	}
	
	private void createShaders() {
		shaders = new ShaderProgram[(int) Math.floor( shaderFiles.length / 2f )];
		
		int j = 0;
		for (int i = 0; i < shaderFiles.length; i += 2, j++) {
			Debug.log( "Creating shader #" + j, this );
			shaders[j] = new ShaderProgram( shaderFiles[i], shaderFiles[i + 1] );
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
			shaders[activeShader].setUniformMat4( "mat_view", getViewportCamera().getViewMatrix() );
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