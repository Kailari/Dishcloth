package dishcloth.engine.rendering.vao.shapes;

import dishcloth.engine.rendering.vao.vertex.ColorTextureVertex;
import dishcloth.api.util.Color;
import dishcloth.api.util.logger.APIDebug;

/**
 * SimpleRegularNGon.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 23.5.2015
 */

public class SimpleRegularNGon extends Polygon<ColorTextureVertex> {

	public SimpleRegularNGon(int n, float radius) {
		super( SimpleRegularNGon.calculateVertices( n, radius ) );
	}

	private static ColorTextureVertex[] calculateVertices(int n, float radius) {
		if (n < 3) {
			APIDebug.logErr( "N is too small. N must be greater than or equal to 3", SimpleRegularNGon.class );
			return null;
		}

		double angleStep = Math.toRadians( 360 ) / n;
		double currentAngle = Math.toRadians( (n % 2 == 0 ? 135 : 90) );

		ColorTextureVertex[] vertices = new ColorTextureVertex[n];

		for (int i = 0; i < n; i++) {
			float x = (float) (Math.cos( currentAngle ) * radius);
			float y = (float) (Math.sin( currentAngle ) * radius);

			// TODO: Calculate UV coordinates
			vertices[i] = new ColorTextureVertex( x, y, Color.WHITE.toInteger(), x, y );

			currentAngle -= angleStep;
		}

		return vertices;
	}

}
