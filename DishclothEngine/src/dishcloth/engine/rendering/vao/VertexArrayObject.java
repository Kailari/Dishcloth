package dishcloth.engine.rendering.vao;

import dishcloth.engine.rendering.vao.vertex.ColorTextureVertex;
import dishcloth.engine.rendering.vao.vertex.VertexFormat;
import dishcloth.engine.rendering.vao.shapes.Polygon;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * VertexArrayObject.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 22.5.2015
 */

public class VertexArrayObject<T extends ColorTextureVertex> {
	private int vboID;  // Vertices
	private int iboID;  // Indices
	private int nIndices;

	private T[] vertices;
	private int[] indices;
	private boolean isDirty;
	private boolean hasBuffers;
	private VertexFormat vertexFormat;

	private int vaoID;  // "renderBlock info"
	private ByteBuffer buffer;
	private IntBuffer iBuff;

	public VertexArrayObject(VertexFormat format, Polygon<T> p) {
		this.vertexFormat = format;
		this.setData( p.getVertices(), p.getIndices(), p.getIndices().length, true );
	}

	public VertexArrayObject(VertexFormat format) {
		this.vertexFormat = format;
	}

	public void setData(T[] vertices, int[] indices, int nIndices, boolean generate) {
		this.vertices = vertices;
		this.indices = indices;
		this.nIndices = nIndices;
		isDirty = true;

		if (generate) {
			generateVBO( nIndices );
		}
	}

	private void generateVBO(int nIndices) {
		if (isDirty) {
			if (this.vertices == null || this.indices == null || this.nIndices <= 0) {
				return;
			}

			if (!hasBuffers) {
				createBuffers();
			}

			glBindVertexArray( vaoID );

			glBindBuffer( GL_ARRAY_BUFFER, vboID );

			int newReqCapacity = vertices.length * vertices[0].getSizeInBytes();

			if (buffer == null || buffer.capacity() < newReqCapacity) {
				buffer = BufferUtils.createByteBuffer( newReqCapacity );
			} else {
				buffer.clear();
				buffer.limit( newReqCapacity );
			}

			glBufferData( GL_ARRAY_BUFFER, ColorTextureVertex.getAsBytes( buffer, vertices ), GL_DYNAMIC_DRAW );

			// Apply vertex format
			VertexFormat.apply( vertexFormat );

			glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, iboID );

			if (iBuff == null || iBuff.capacity() < indices.length) {
				iBuff = BufferUtils.createIntBuffer( indices.length );
			} else {
				iBuff.clear();
				iBuff.limit( indices.length );
			}

			iBuff.put( indices ).flip();

			glBufferData( GL_ELEMENT_ARRAY_BUFFER, iBuff, GL_DYNAMIC_DRAW );

			if (nIndices == -1) {
				this.nIndices = indices.length;
			}

			// Unbind everything
			glBindVertexArray( 0 );
			glBindBuffer( GL_ARRAY_BUFFER, 0 );
			glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, 0 );

			// VBO and IBO/EBO updated; This VAO is no longer dirty.
			isDirty = false;
		}
	}

	private void createBuffers() {
		this.vboID = glGenBuffers();
		this.iboID = glGenBuffers();
		this.vaoID = glGenVertexArrays();
		this.hasBuffers = true;
	}

	public void render() {

		generateVBO( nIndices );

		// Bind VAO. All rendering hints/buffers are stored in VAO, so no further instructions are needed.
		glBindVertexArray( vaoID );

		glDrawElements( GL_TRIANGLES, nIndices, GL_UNSIGNED_INT, 0 );

		// Unbind VAO
		glBindVertexArray( 0 );
	}

	public void dispose() {
		glBindVertexArray( 0 );
		glDeleteVertexArrays( vaoID );

		glBindBuffer( GL_ARRAY_BUFFER, 0 );
		glDeleteBuffers( vboID );
		glDeleteBuffers( iboID );
	}
}
