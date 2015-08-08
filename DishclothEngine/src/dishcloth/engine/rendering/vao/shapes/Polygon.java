package dishcloth.engine.rendering.vao.shapes;

import dishcloth.engine.rendering.vao.vertex.ColorTextureVertex;
import dishcloth.api.util.logger.APIDebug;

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

public class Polygon<T extends ColorTextureVertex> {

	private T[] vertices;
	private int[] indices;

	public Polygon(T[] vertices) {
		if (vertices.length < 3) {
			APIDebug.logErr( "Not enough vertices! n=" + vertices.length, this );
			return;
		}

		this.vertices = vertices;
		indices = new int[3 + (3 * (vertices.length - 3))];

		// Create triangles for all vertices.
		// OpenGL wants triangle indices counter-clockwise
		int j = 2;
		for (int i = 2; i < indices.length; i+=3) {
			indices[i - 2] = 0;
			indices[i - 1] = j - 1;
			indices[i] = j;

			j++;
		}
	}

	public T[] getVertices() {
		return vertices;
	}

	public int[] getIndices() {
		return indices;
	}
}
