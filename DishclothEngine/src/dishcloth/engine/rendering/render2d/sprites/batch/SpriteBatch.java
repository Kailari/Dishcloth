package dishcloth.engine.rendering.render2d.sprites.batch;

import dishcloth.api.exception.ShaderUniformException;
import dishcloth.api.util.memory.Cache;
import dishcloth.engine.ADishclothObject;
import dishcloth.api.abstractionlayer.rendering.ICamera;
import dishcloth.api.abstractionlayer.rendering.IRenderer;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.text.TextRenderer;
import dishcloth.engine.rendering.text.bitmapfont.BitmapFont;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.api.util.Color;
import dishcloth.api.util.geom.Point;
import dishcloth.api.util.geom.Rectangle;
import dishcloth.engine.rendering.vao.vertex.VertexFormat;
import dishcloth.engine.util.debug.Debug;
import dishcloth.api.util.memory.SoftReferencedCache;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * SpriteBatch.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Renders bunch of sprites. ShaderProgram is not unloaded between renderBlock calls giving a significant performance gain
 * when rendering large quantities of sprites.
 * <p>
 * Batches also wrap shader program and handle setting transformations for drawing.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 22.5.2015
 */

public class SpriteBatch extends ADishclothObject {

	private Cache<SpriteInfo> spriteInfoCache;
	private SpriteBatcher batcher;
	private RenderInfo renderInfo;
	private boolean beginCalled;

	public SpriteBatch() {
		super( false );
		this.renderInfo = new RenderInfo( null, null, null );
		this.batcher = new SpriteBatcher();
		this.spriteInfoCache = new Cache<>();
	}

	public SpriteBatch(VertexFormat customFormat) {
		this();
		batcher.setCustomFormat( customFormat );
	}

	public void begin(ShaderProgram shader, ICamera camera, IRenderer renderer) {
		if (beginCalled) {
			Debug.logErr( "SpriteBatch.begin(...) called without calling .end()!", this );
			return;
		}
		beginCalled = true;

		this.renderInfo.shader = shader;
		this.renderInfo.camera = camera;
		this.renderInfo.renderer = renderer;
	}

	public void queue
			(
					Texture texture,
					Rectangle destination,
					Rectangle source,
					float angle,
					Color tint,
					Point origin
			) {

		queue( texture, destination, source, angle, tint.toInteger(), origin );
	}

	public void queue
			(
					Texture texture,
					Rectangle destination,
					Rectangle source,
					float angle,
					int tint,
					Point origin
			) {

		if (!beginCalled) {
			Debug.logErr( "SpriteBatch.queue(...) called without calling .begin(...) first!", this );
			return;
		}

		batcher.addSpriteInfo( createSpriteInfo( texture,
		                                         destination,
		                                         source,
		                                         (float) Math.toRadians( angle ),
		                                         tint,
		                                         origin ) );
	}

	public void queueString(BitmapFont font, Point position, Color color, String text) {
		TextRenderer.renderText( this, position, font, color, text );
	}

	private SpriteInfo createSpriteInfo
			(
					Texture texture,
					Rectangle destination,
					Rectangle source,
					float angle,
					Color tint,
					Point origin
			) {
		return createSpriteInfo( texture, destination, source, angle, tint.toInteger(), origin );
	}

	/**
	 * Tries to save memory by using cache to recycle SpriteInfos as much as possible
	 */
	private SpriteInfo createSpriteInfo
	(
			Texture texture,
			Rectangle destination,
			Rectangle source,
			float angle,
			int tint,
			Point origin
	) {
		SpriteInfo spriteInfo;
		if ((spriteInfo = spriteInfoCache.getItem()) != null) {
			spriteInfo.setData( source, destination, angle, origin, texture, tint );
			return spriteInfo;
		} else {
			return new SpriteInfo( source, destination, angle, origin, texture, tint );
		}
	}

	public void end() {
		end( true );
	}

	public void end(boolean bindShader) {
		beginCalled = false;

		if (bindShader) {
			renderInfo.renderer.bindShader( renderInfo.shader );
		}

		try {
			renderInfo.shader.setUniformMat4( "mat_project", renderInfo.camera.getProjectionMatrix() );
			renderInfo.shader.setUniformMat4( "mat_view", renderInfo.camera.getCameraTransformMatrix() );
		} catch (ShaderUniformException e) {
			Debug.logException( e, this );
			return;
		}

		batcher.renderBatch( spriteInfoCache );

		renderInfo.renderer.bindShader( 0 );
	}

	@Override
	public void dispose() {
		batcher.dispose();
	}
}
