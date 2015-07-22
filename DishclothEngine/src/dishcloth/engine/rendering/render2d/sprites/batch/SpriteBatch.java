package dishcloth.engine.rendering.render2d.sprites.batch;

import dishcloth.engine.exception.ShaderUniformException;
import dishcloth.engine.rendering.ICamera;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.render2d.sprites.Sprite;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vbo.Vertex;
import dishcloth.engine.rendering.vbo.VertexArrayObject;
import dishcloth.engine.util.Color;
import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.geom.Rectangle;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.util.math.Matrix4;
import dishcloth.engine.util.math.MatrixUtility;
import dishcloth.engine.util.memory.SoftReferencedCache;

import java.util.*;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * SpriteBatch.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Renders bunch of sprites. ShaderProgram is not unloaded between render calls giving a significant performance gain
 * when rendering large quantities of sprites.
 * <p>
 * Batches also wrap shader program and handle setting transformations for drawing.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 22.5.2015
 */

// TODO: Rewrite the whole thing to use dynamic VBO/IBO (Current implementation is just sprite renderer, not batch)
public class SpriteBatch {

	private SoftReferencedCache<SpriteInfo> spriteInfoCache;
	private SpriteBatcher batcher;
	private RenderInfo renderInfo;
	private boolean beginCalled;

	public SpriteBatch() {
		this.batcher = new SpriteBatcher();
		this.spriteInfoCache = new SoftReferencedCache<>();
	}

	public void begin(ShaderProgram shader, ICamera camera, IRenderer renderer) {
		if (beginCalled) {
			Debug.logErr( "SpriteBatch.begin(...) called without calling .end()!", this );
			return;
		}
		beginCalled = true;

		this.renderInfo = new RenderInfo( shader, camera, renderer );
	}

	public void queue(Texture texture, Rectangle destination, Rectangle source, float angle, Color tint, Point origin) {
		if (!beginCalled) {
			Debug.logErr("SpriteBatch.queue(...) called without calling .begin(...) first!", this);
			return;
		}

		batcher.addSpriteInfo( createSpriteInfo( texture,
		                                         destination,
		                                         source,
		                                         (float) Math.toRadians( angle ),
		                                         tint,
		                                         origin ) );
	}

	/**
	 * Tries to save memory by using cache to recycle SpriteInfos as much as possible
	 */
	private SpriteInfo createSpriteInfo(Texture texture, Rectangle destination, Rectangle source, float angle, Color tint, Point origin) {
		SpriteInfo spriteInfo;
		if ((spriteInfo = spriteInfoCache.getItem()) != null) {
			spriteInfo.setData( source, destination, angle, origin, texture, tint );
			return spriteInfo;
		} else {
			return new SpriteInfo( source, destination, angle, origin, texture, tint );
		}
	}

	public void end() {
		end(true);
	}

	public void end(boolean bindShader) {
		beginCalled = false;

		if (bindShader) {
			renderInfo.renderer.bindShader( renderInfo.shader );
		}

		try {
			renderInfo.shader.setUniformMat4( "mat_project", renderInfo.camera.getProjectionMatrix() );
			renderInfo.shader.setUniformMat4( "mat_view", renderInfo.camera.getViewMatrix() );
		} catch (ShaderUniformException e) {
			Debug.logException( e, this );
			return;
		}

		batcher.renderBatch( spriteInfoCache );

		renderInfo.renderer.bindShader( 0 );
	}
}
