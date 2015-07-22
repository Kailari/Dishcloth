package dishcloth.engine.rendering.render2d.sprites.batch;

import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.rendering.vbo.Vertex;
import dishcloth.engine.rendering.vbo.VertexArrayObject;
import dishcloth.engine.util.logger.Debug;
import dishcloth.engine.util.memory.SoftReferencedCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

/**
 * SpriteBatcher.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 21.7.2015
 */

class SpriteBatcher {
	private static final int BATCH_MAX_SIZE_SPRITES = 2048;
	private static final int BATCH_MAX_SIZE_VERTICES = BATCH_MAX_SIZE_SPRITES * 4;
	private static final int BATCH_MAX_SIZE_INDICES = BATCH_MAX_SIZE_SPRITES * 6;
	private static final int BATCH_INITIAL_SIZE_SPRITES = 256;

	private Vertex[] vertices;
	private int[] indices;
	private VertexArrayObject VAO;
	private List<SpriteInfo> renderQueue;

	public SpriteBatcher() {
		this.renderQueue = new ArrayList<>();
		this.VAO = new VertexArrayObject();

		prepareArrays( BATCH_INITIAL_SIZE_SPRITES );
	}

	public void addSpriteInfo(SpriteInfo info) {
		this.renderQueue.add( info );
	}

	private void prepareArrays(int itemCount) {
		int requiredCapacityVertices = itemCount * 4;
		int requiredCapacityIndices = itemCount * 6;

		if (requiredCapacityIndices > BATCH_MAX_SIZE_INDICES
				|| requiredCapacityVertices > BATCH_MAX_SIZE_VERTICES) {
			Debug.logErr("Error initializing batcher arrays. Capacity requirement too high.", this);
		}

		// If arrays are not null and have already enough capacity, return.
		if ((this.indices != null && this.indices.length >= requiredCapacityIndices)
				&& (this.vertices != null && this.vertices.length >= requiredCapacityVertices)) {
			return;
		}

		// Create/Update arrays

		int[] newIndexArray = new int[requiredCapacityIndices];
		int oldLength = 0;

		// If indices is not null, recycle old values
		if (this.indices != null) {

			oldLength = indices.length;
			System.arraycopy( this.indices, 0, newIndexArray, 0, oldLength );
		}

		// Fill in the remaining (new) values
		for (int i = oldLength / 6; i < itemCount; i++) {
			// Top-left triangle
			newIndexArray[i * 6] = (i * 4);
			newIndexArray[i * 6 + 1] = (i * 4) + 1;
			newIndexArray[i * 6 + 2] = (i * 4) + 2;

			// Bottom-right triangle
			newIndexArray[i * 6 + 3] = (i * 4) + 1;
			newIndexArray[i * 6 + 4] = (i * 4) + 2;
			newIndexArray[i * 6 + 5] = (i * 4) + 3;
		}
		this.indices = newIndexArray;

		this.vertices = new Vertex[requiredCapacityVertices];
	}

	public void renderBatch(SoftReferencedCache<SpriteInfo> spriteInfoCache) {
		// Return if there is nothing to render.
		if (renderQueue.size() == 0) {
			return;
		}

		int itemsToProcess = renderQueue.size();
		int renderQueueIndex = 0;

		while (itemsToProcess > 0) {

			int itemsToProcessInThisBatch = Math.min( itemsToProcess, BATCH_MAX_SIZE_SPRITES );
			prepareArrays( itemsToProcessInThisBatch );

			int index = 0;
			int textureID = -1;
			for (int i = 0; i < itemsToProcessInThisBatch; i++, renderQueueIndex++) {

				// Get next spriteInfo
				SpriteInfo info = renderQueue.get( renderQueueIndex );

				// If texture changed, issue draw-call
				if (info.texture.getGLTexID() != textureID) {
					// Render
					doRender( index, textureID );

					// Change texture
					textureID = info.texture.getGLTexID();
					index = 0;
				}

				// Store vertices
				vertices[index++] = info.botLeft;
				vertices[index++] = info.botRight;
				vertices[index++] = info.topLeft;
				vertices[index++] = info.topRight;

				// Cache the SpriteInfo for recycling
				info.texture = null;
				spriteInfoCache.addItem( info );
			}

			// Render remaining batches
			doRender( index, textureID );

			// Update item counter
			itemsToProcess -= itemsToProcessInThisBatch;
		}

		// Everything rendered, clear.
		renderQueue.clear();
	}

	private void doRender(int endIndex, int textureID) {
		// Check if there is anything to render at all
		if (endIndex == 0) {
			return;
		}

		// Bind texture
		glBindTexture( GL_TEXTURE_2D, textureID );

		// Update VAO
		VAO.setData( vertices, indices, endIndex / 4 * 2 * 3 );

		// Render VAO
		VAO.render();

		Arrays.fill( vertices, null );
	}
}
