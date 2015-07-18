package dishcloth.engine.rendering.vbo.shapes;

import dishcloth.engine.rendering.vbo.Vertex;
import dishcloth.engine.util.geom.Rectangle;

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
		super( new Vertex( -w / 2f, +h / 2f, 0f, 1f ),
		       new Vertex( +w / 2f, +h / 2f, 1f, 1f ),
		       new Vertex( +w / 2f, -h / 2f, 1f, 0f ),
		       new Vertex( -w / 2f, -h / 2f, 0f, 0f ) );
	}

	public Quad(Rectangle bounds) {
		super(
				new Vertex( bounds.x, bounds.y, 0f, 1f ),
				new Vertex( bounds.x + bounds.w, bounds.y, 1f, 1f ),
				new Vertex( bounds.x + bounds.w, bounds.y + bounds.h, 1f, 0f ),
				new Vertex( bounds.x, bounds.y + bounds.h, 0f, 0f )
		);
	}
}
