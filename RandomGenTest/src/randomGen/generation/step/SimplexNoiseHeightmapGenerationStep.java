package randomGen.generation.step;

import dishcloth.engine.world.level.TerrainChunk;
import randomGen.util.SimplexNoise;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * randomGen.generation.step.SimplexNoiseHeightmapGenerationStep.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 28.6.2015
 */

public class SimplexNoiseHeightmapGenerationStep extends ATerrainGenerationStep {
	private SimplexNoise noise;

	public SimplexNoiseHeightmapGenerationStep(long seed) {
		this( seed,    // Seed
		      16,      // n of octaves
		      0f,       // persistence
		      0.5f,     // gain
		      50.0f,    // initial amplitude
		      0.008f );  // initial frequency
	}

	public SimplexNoiseHeightmapGenerationStep(long seed, int nOctaves,
	                                           float persistence, float gain, float amplitude, float frequency) {
		this.noise = new SimplexNoise( seed, nOctaves, persistence, gain, amplitude, frequency );
	}

	@Override
	public float[] onGenerateValues(float[] values, long seed, int chunkX, int chunkY) {
		int size = (int) Math.sqrt( values.length );

		// Generate simplexNoise
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				float noiseX = (chunkX * size) + x;
				float noiseY = (chunkY * size) + y;
				values[x + y * size] += (float) (noise.generate( noiseX, noiseY ) / 2f + 0.5);
			}
		}

		return values;
	}
}
