package dishcloth.engine.world.generation.step;

import dishcloth.engine.world.level.TerrainChunk;
import dishcloth.engine.util.SimplexNoise;

import java.util.Arrays;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * GroundSurfaceGenerationStep.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 28.6.2015
 */

// TODO: Apply gradient to surface values
public class GroundSurfaceGenerationStep extends ATerrainGenerationStep {

	private static final float EMPTINESS_VALUE = 0.0f;

	private float vScalingFactor;
	private float hScalingFactor;
	private int groundYLevel;
	private int shallowDepth;

	public GroundSurfaceGenerationStep(float hillSize, float hScale, int groundYLevel, int shallowDepth) {
		this.groundYLevel = groundYLevel;
		this.vScalingFactor = hillSize;
		this.hScalingFactor = 1.0f / hScale;
		this.shallowDepth = shallowDepth;
	}

	@Override
	public float[] onGenerateValues(float[] values, long seed, int chunkX, int chunkY) {
		int size = Math.round( (float) Math.sqrt( values.length ) );

		SimplexNoise noise = new SimplexNoise( seed,
		                                       8,
		                                       1.75f,
		                                       0.5f,
		                                       75.0f,
		                                       0.004f );
		float[] tmp = new float[size];
		boolean skip = true;
		int chunkTopY = chunkY * TerrainChunk.CHUNK_SIZE;
		int chunkBotY = chunkTopY + TerrainChunk.CHUNK_SIZE;
		
		for (int i = 0; i < size; i++) {
			int index = (chunkX * size) + i;
			float value = tmp[i] = noise.generate( index * hScalingFactor, 1.0f ) * vScalingFactor;
			if (groundYLevel + value >= chunkTopY - shallowDepth && groundYLevel + value <= chunkBotY + shallowDepth) {
				skip = false;
			}
		}

		// Chunk contains one or more blocks that need to be modified
		if (skip) {
			// All above, turn everything into air. (assuming 0.0f is air.)
			if (groundYLevel + tmp[0] < chunkTopY) {
				// Nullify all values
				Arrays.fill( values, EMPTINESS_VALUE );
				//values = new float[values.length];
				return values;
			}
			// All blocks below ground level, no change required.
			else {
				return values;
			}
		}

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (chunkTopY + y > groundYLevel + tmp[x]) {
					values[x + y * size] = EMPTINESS_VALUE;
				} else {
					int distanceToSurface = Math.abs( (chunkTopY + y) - (groundYLevel + (int)Math.floor( tmp[x] )) );

					if (distanceToSurface < 1f) {
						values[x + y * size] = 1.1f;
					} else if (distanceToSurface < shallowDepth) {
						values[x + y * size] = values[x + y * size] * (distanceToSurface / (float) shallowDepth);
					}
				}
			}
		}

		return values;
	}
}
