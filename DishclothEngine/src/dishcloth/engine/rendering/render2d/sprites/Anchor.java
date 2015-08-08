package dishcloth.engine.rendering.render2d.sprites;

import dishcloth.api.util.geom.Point;
import dishcloth.api.util.memory.PointCache;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Anchor.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 25.5.2015
 */
public enum Anchor {
	TOPLEFT( 0f, 1f ),
	TOPMID( 0.5f, 1f ),
	TOPRIGHT( 1f, 1f ),
	MIDLEFT( 0f, 0.5f ),
	CENTER( 0.5f, 0.5f ),
	MIDRIGHT( 1f, 0.5f ),
	BOTLEFT( 0f, 0f ),
	BOTMID( 0.5f, 0f ),
	BOTRIGHT( 1f, 0f );

	private float xOffsetMultiplier, yOffsetMultiplier;

	Anchor(float x, float y) {
		xOffsetMultiplier = x;
		yOffsetMultiplier = y;
	}

	public Point createPivot(float w, float h) {
		return PointCache.getPoint( xOffsetMultiplier * w, yOffsetMultiplier * h );
	}
}
