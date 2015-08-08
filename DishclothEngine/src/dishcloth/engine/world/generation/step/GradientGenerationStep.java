package dishcloth.engine.world.generation.step;

import dishcloth.api.util.math.DishMath;
import dishcloth.engine.world.level.TerrainChunk;

import static java.lang.Math.*;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * GradientGenerationStep.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 1.7.2015
 */

public class GradientGenerationStep extends ATerrainGenerationStep {
	private int zeroYLevel;
	private int oneYLevel;
	private float[] gradientLookupTable;

	public GradientGenerationStep(int zeroYLevel, int oneYLevel, float zeroValue, float oneValue) {
		this.zeroYLevel = min( zeroYLevel, oneYLevel );
		this.oneYLevel = max( zeroYLevel, oneYLevel );

		this.gradientLookupTable = new float[abs( min( zeroYLevel, oneYLevel ) - max( zeroYLevel, oneYLevel ) )];
		for (int i = 0; i < this.gradientLookupTable.length; i++) {
			this.gradientLookupTable[i] = DishMath.lerp( zeroValue,
			                                             oneValue,
			                                             (float) i / this.gradientLookupTable.length );
		}
	}

	@Override
	public float[] onGenerateValues(float[] values, long seed, int chunkX, int chunkY) {
		int size = (int) Math.sqrt( values.length );

		int chunkTopY = chunkY * TerrainChunk.CHUNK_SIZE;

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (chunkTopY + y >= zeroYLevel && chunkTopY + y < oneYLevel) {
					values[x + y * size] = values[x + y * size] * this.gradientLookupTable[chunkTopY + y - zeroYLevel];
				}
			}
		}

		return values;
	}
}
