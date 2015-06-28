import dishcloth.engine.world.level.TerrainChunk;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * SimplexNoiseHeightmapGenerationStep.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 28.6.2015
 */

public class SimplexNoiseHeightmapGenerationStep implements ITerrainGenerationStep {
	private long seed;
	private SimplexNoise noise;

	public SimplexNoiseHeightmapGenerationStep(long seed) {
		this.seed = seed;
		this.noise = new SimplexNoise( seed,    // Seed
		                               16,      // n of octaves
		                               0.2,     // lacunarity
		                               0.65,    // gain
		                               50.0,    // initial amplitude
		                               0.004);  // initial frequency
	}

	@Override
	public TerrainChunk doGeneration(TerrainChunk targetChunk, long seed, int chunkX, int chunkY) {
		return null;
	}

	@Override
	public float[] doGeneration(float[] values, long seed, int chunkX, int chunkY) {
		int size = (int) Math.sqrt( values.length );

		float min = Float.MAX_VALUE;
		float max = Float.MIN_VALUE;

		// Generate simplexNoise
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				float noiseX = chunkX + x;
				float noiseY = chunkY + y;
				float tmp = values[x + y * size] = (float) noise.generate( noiseX, noiseY );
				if (tmp < min) {
					min = tmp;
				}
				if (tmp > max) {
					max = tmp;
				}
			}
		}

		// Normalize the heightmap to [0.0, 1.0]
		for (int i = 0; i < values.length; i++) {
			values[i] = (values[i] + Math.abs( min )) / (Math.abs( max ) + Math.abs( min ));
		}

		return values;
	}
}
