package dishcloth.engine.rendering.vbo.shapes;

import dishcloth.engine.rendering.vbo.Vertex;
import dishcloth.engine.util.Color;
import dishcloth.engine.util.logger.Debug;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * RegularNGon.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 23.5.2015
 */

public class RegularNGon extends Polygon {

	public RegularNGon(int n, float radius) {
		super(RegularNGon.calculateVertices( n, radius ));
	}

	private static Vertex[] calculateVertices(int n, float radius) {
		if (n < 3) {
			Debug.logErr( "N is too small. N must be greater than or equal to 3", RegularNGon.class );
			return null;
		}

		double angleStep = Math.toRadians( 360 ) / n;
		double currentAngle = Math.toRadians( (n % 2 == 0 ? 135 : 90) );

		Vertex[] vertices = new Vertex[n];

		for (int i = 0; i < n; i++) {
			float x = (float)(Math.cos(currentAngle) * radius);
			float y = (float)(Math.sin(currentAngle) * radius);

			// TODO: Calculate UV coordinates
			vertices[i] = new Vertex( x, y, x, y);

			currentAngle -= angleStep;
		}

		return vertices;
	}

}
