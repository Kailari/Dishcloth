package dishcloth.engine.rendering.vbo;

import dishcloth.engine.rendering.vbo.shapes.Polygon;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * VertexArrayObject.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 22.5.2015
 */

// TODO: Rename all references in other classes from "VBO" to "VAO", this is technically a VertexArrayObject. (class renamed)
public class VertexArrayObject<T extends Vertex> {
	private int vboID;  // Vertices
	private int iboID;  // Indices
	private int nIndices;

	private T[] vertices;
	private int[] indices;
	private boolean isDirty;

	private int vaoID;  // render info

	public VertexArrayObject(Polygon<T> p) {
		this();
		this.setData( p.getVertices(), p.getIndices(), p.getIndices().length );
		generateVBO();
	}

	public VertexArrayObject() {
		vboID = glGenBuffers();
		iboID = glGenBuffers();
		vaoID = glGenVertexArrays();
	}

	public void setData(T[] vertices, int[] indices, int nIndices) {
		this.vertices = vertices;
		this.indices = indices;
		this.nIndices = nIndices;
		isDirty = true;
	}

	private void generateVBO() {
		if (isDirty) {
			glBindVertexArray( vaoID );

			glBindBuffer( GL_ARRAY_BUFFER, vboID );

			glBufferData( GL_ARRAY_BUFFER, Vertex.getAsBytes( vertices ), GL_STATIC_DRAW );

			// Apply vertex format
			VertexFormat.apply( vertices[0].getFormat() );

			glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, iboID );

			IntBuffer iBuff = BufferUtils.createIntBuffer( indices.length );
			iBuff.put( indices ).flip();

			glBufferData( GL_ELEMENT_ARRAY_BUFFER, iBuff, GL_STATIC_DRAW );

			nIndices = indices.length;

			// Unbind everything
			glBindVertexArray( 0 );
			glBindBuffer( GL_ARRAY_BUFFER, 0 );
			glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, 0 );

			// VBO and IBO/EBO updated; This VAO is no longer dirty.
			isDirty = false;
		}
	}

	public void render() {

		generateVBO();

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