package dishcloth.engine.rendering.vbo.shapes;

import dishcloth.engine.rendering.vbo.Vertex;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Quad.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 23.5.2015
 */

public class Quad extends Polygon {
	public Quad(float w, float h) {
		super( new Vertex( -w / 2f, -h / 2f, 0f, 1f ),
		       new Vertex( -w / 2f, +h / 2f, 0f, 0f ),
		       new Vertex( +w / 2f, +h / 2f, 1f, 0f ),
		       new Vertex( +w / 2f, -h / 2f, 1f, 1f ) );
	}
}
