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
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * VertexBufferObject.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 22.5.2015
 */

public class VertexBufferObject {
	private int vboID;  // Vertices
	private int iboID;  // Indices
	private int nIndices;

	private int vaoID;  // render info

	public VertexBufferObject(Polygon p) {
		this(p.getVertices(), p.getIndices());
	}

	public VertexBufferObject(Vertex[] vertices, int[] indices) {
		vboID = glGenBuffers();

		vaoID = glGenVertexArrays();
		glBindVertexArray( vaoID );

		glBindBuffer( GL_ARRAY_BUFFER, vboID );

		glBufferData( GL_ARRAY_BUFFER, Vertex.getAsBytes( vertices ), GL_STATIC_DRAW );

		// Position (float vec2)
		glEnableVertexAttribArray( 0 );
		glVertexAttribPointer( 0,
		                       Vertex.POSITION_SIZE,
		                       Vertex.POSITION_TYPE,
		                       false,
		                       Vertex.POSITION_STRIDE,
		                       Vertex.POSITION_OFFSET );

		// Texture coordinates (float vec2)
		glEnableVertexAttribArray( 1 );
		glVertexAttribPointer( 1,
		                       Vertex.UV_SIZE,
		                       Vertex.UV_TYPE,
		                       false,
		                       Vertex.UV_STRIDE,
		                       Vertex.UV_OFFSET );

		iboID = glGenBuffers();

		glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, iboID );

		IntBuffer iBuff = BufferUtils.createIntBuffer( indices.length );
		iBuff.put( indices ).flip();

		glBufferData( GL_ELEMENT_ARRAY_BUFFER, iBuff, GL_STATIC_DRAW );

		nIndices = indices.length;

		// Unbind everything
		glBindVertexArray( 0 );
		glBindBuffer( GL_ARRAY_BUFFER, 0 );
		glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, 0 );
	}

	public void render() {

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
