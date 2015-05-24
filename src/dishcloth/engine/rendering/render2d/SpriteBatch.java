package dishcloth.engine.rendering.render2d;

import dishcloth.engine.AGame;
import dishcloth.engine.exception.ShaderUniformException;
import dishcloth.engine.rendering.IRenderable;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vbo.VertexBufferObject;
import dishcloth.engine.rendering.vbo.shapes.Quad;
import dishcloth.engine.util.Color;
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

	public SpriteBatch(ShaderProgram shader) {
		this.renderQueue = new HashMap<>();
		this.renderShader = shader;
		this.dummyQuad = new VertexBufferObject( new Quad( 1f, 1f ) );
	}

	public void queue(Texture texture, Rectangle destination, Rectangle source, float angle, Color tint) {
		if (!renderQueue.containsKey( texture.getGLTexID() )) {
			renderQueue.put( texture.getGLTexID(), new ArrayList<>() );
		}

		renderQueue.get( texture.getGLTexID() ).add( new TextureRenderInfo(
				source, destination, angle, texture, tint ) );
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

				for (TextureRenderInfo sInfo : entry.getValue()) {

					transform = MatrixUtility.createScaling( sInfo.dest.w, sInfo.dest.h, 1f )
							.multiply( MatrixUtility.createTranslation( sInfo.dest.x, sInfo.dest.y, 0f ) )
							.multiply( MatrixUtility.createRotationZ( sInfo.angle ) );

					renderShader.setUniformMat4( "mat_project", AGame.projectionMatrix );
					renderShader.setUniformMat4( "mat_view", AGame.viewMatrix );

					renderShader.setUniformMat4( "mat_modelview", transform );
					renderShader.setUniformVec4f( "subtexture", sInfo.u, sInfo.v, sInfo.uSize, sInfo.vSize );

					renderShader.setUniformVec4f( "color_tint",
					                              sInfo.tint.r, sInfo.tint.g, sInfo.tint.b, sInfo.tint.a );

					dummyQuad.render();
				}
			} catch (ShaderUniformException e) {
				Debug.logException( e, this );
			}
		}

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

		float u, v, uSize, vSize;

		public TextureRenderInfo(Rectangle src, Rectangle dest, float angle, Texture texture, Color tint) {
			this.dest = dest;
			this.texture = texture;
			this.angle = angle;
			this.tint = tint;

			this.uSize = src.w / texture.getWidth();
			this.vSize = src.h / texture.getHeight();

			this.u = src.x / texture.getWidth();
			this.v = src.y / texture.getHeight();
		}
	}
}
