package dishcloth.engine.rendering.render2d;

import dishcloth.engine.AGame;
import dishcloth.engine.exception.ShaderUniformException;
import dishcloth.engine.rendering.ICamera;
import dishcloth.engine.rendering.IRenderable;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vbo.VertexBufferObject;
import dishcloth.engine.rendering.vbo.shapes.Quad;
import dishcloth.engine.util.Color;
import dishcloth.engine.util.geom.Point;
import dishcloth.engine.util.geom.Rectangle;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.util.math.Matrix4;
import dishcloth.engine.util.math.MatrixUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class SpriteBatch implements IRenderable {

	private HashMap<Integer, List<TextureRenderInfo>> renderQueue;
	private ShaderProgram renderShader;
	private VertexBufferObject dummyQuad;
	private AGame activeGame;

	public SpriteBatch(ShaderProgram shader, AGame game) {
		this.renderQueue = new HashMap<>();
		this.renderShader = shader;
		this.dummyQuad = new VertexBufferObject( new Quad( new Rectangle( 0f, 0f, 1f, 1f ) ) );
		this.activeGame = game;
	}

	public void queue(Texture texture, Rectangle destination, Rectangle source, float angle, Color tint, Point origin) {
		if (!renderQueue.containsKey( texture.getGLTexID() )) {
			renderQueue.put( texture.getGLTexID(), new ArrayList<>() );
		}

		renderQueue.get( texture.getGLTexID() ).add( new TextureRenderInfo(
				source, destination, angle, origin, texture, tint ) );
	}

	@Override
	public void render(IRenderer renderer) {

		renderer.bindShader( renderShader );

		Texture texture;
		for (Map.Entry<Integer, List<TextureRenderInfo>> entry : renderQueue.entrySet()) {
			texture = Texture.findByID( entry.getKey() );

			if (texture == null) {
				continue;
			}

			renderer.bindTexture( texture );

			try {
				Matrix4 transform;

				// Projection and view are the same for all sprites.
				renderShader.setUniformMat4( "mat_project", activeGame.getViewportCamera().getProjectionMatrix() );
				renderShader.setUniformMat4( "mat_view", activeGame.getViewportCamera().getViewMatrix() );

				for (TextureRenderInfo info : entry.getValue()) {

					// TODO: Cache modelview matrices. Update them only if object's properties have changed

					// Create modelview matrix
					transform = MatrixUtility.createTranslation( info.dest.x + info.origin.x,
					                                             info.dest.y + info.origin.y, 0f )
							.multiply( MatrixUtility.createRotationZ( info.angle ) )
							.multiply( MatrixUtility.createTranslation( -info.origin.x,
							                                            -info.origin.y, 0f ) )
							.multiply( MatrixUtility.createScaling( info.dest.w, info.dest.h, 1f ) );


					// Set modelview matrix and other per-sprite uniforms
					renderShader.setUniformMat4( "mat_modelview", transform );
					renderShader.setUniformVec4f( "subtexture", info.u, info.v, info.uSize, info.vSize );

					renderShader.setUniformVec4f( "color_tint",
					                              info.tint.r, info.tint.g, info.tint.b, info.tint.a );

					// Render
					dummyQuad.render();
				}
			} catch (ShaderUniformException e) {
				Debug.logException( e, this );
			}
		}

		// Got everything rendered, clear queue
		renderQueue.clear();
	}

	// TODO: Automatize shader disposing --- Will be done in resource manager
	public void dispose() {
		renderShader.dispose();
	}

	private class TextureRenderInfo {
		Rectangle dest;
		Texture texture;
		Color tint;
		float angle;
		Point origin;

		float u, v, uSize, vSize;

		public TextureRenderInfo(Rectangle src, Rectangle dest, float angle, Point origin, Texture texture, Color tint) {
			this.dest = dest;
			this.texture = texture;
			this.angle = angle;
			this.tint = tint;
			this.origin = origin;

			this.uSize = src.w / texture.getWidth();
			this.vSize = src.h / texture.getHeight();

			this.u = src.x / texture.getWidth();
			this.v = src.y / texture.getHeight();

			this.dest.x -= origin.x;
			this.dest.y -= origin.y;
		}
	}
}
