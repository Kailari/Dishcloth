package dishcloth.engine.rendering.vbo.shapes;

import dishcloth.engine.rendering.vbo.Vertex;
import dishcloth.engine.util.Color;
import dishcloth.engine.util.logger.Debug;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Polygon.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Shape with n corners
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 23.5.2015
 */

public class Polygon {

	private Vertex[] vertices;
	private int[] indices;

	public Polygon(Vertex... vertices) {
		if (vertices.length < 3) {
			Debug.logErr( "Not enough vertices! n=" + vertices.length, this );
			return;
		}

		this.vertices = vertices;
		indices = new int[3 + (3 * (vertices.length - 3))];

		// Create triangles for all vertices.
		// OpenGL wants triangle indices counter-clockwise
		int j = 2;
		for (int i = 2; i < indices.length; i+=3) {
			indices[i - 2] = 0;
			indices[i - 1] = j;
			indices[i] = j - 1;

			j++;
		}
	}

	public Vertex[] getVertices() {
		return vertices;
	}

	public int[] getIndices() {
		return indices;
	}

	public void setVertexUV(int index, float u, float v) {
		vertices[index].setUV( u, v );
	}
}
