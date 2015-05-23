package dishcloth.engine.rendering.vbo;

import org.lwjgl.BufferUtils;

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

	private int vaoID;  // Render info

	public VertexBufferObject(float[] vertices, int[] elements) {
		vboID = glGenBuffers();

		vaoID = glGenVertexArrays();
		glBindVertexArray( vaoID );

		glBindBuffer( GL_ARRAY_BUFFER, vboID );

		FloatBuffer buff = BufferUtils.createFloatBuffer( vertices.length );
		buff.put( vertices ).flip();

		glBufferData( GL_ARRAY_BUFFER, buff, GL_STATIC_DRAW );

		glEnableVertexAttribArray( 0 );
		glVertexAttribPointer( 0, 2, GL_FLOAT, false, 0, 0 );



		iboID = glGenBuffers();

		glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, iboID );

		IntBuffer iBuff = BufferUtils.createIntBuffer( elements.length );
		iBuff.put( elements ).flip();

		glBufferData( GL_ELEMENT_ARRAY_BUFFER, iBuff, GL_STATIC_DRAW );

		nIndices = elements.length;

		// Unbind everything
		glBindVertexArray( 0 );
		glBindBuffer( GL_ARRAY_BUFFER, 0 );
		glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, 0 );
	}

	public void render() {

		// Bind VAO. All rendering hints/buffers are stored in VAO, so no further instructions are needed.
		glBindVertexArray( vaoID );

		glDrawElements(GL_TRIANGLES, nIndices, GL_UNSIGNED_INT, 0);

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
