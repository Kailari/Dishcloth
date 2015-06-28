package randomGen.generation.step;

import dishcloth.engine.world.level.TerrainChunk;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * RangeFilterGenerationStep.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 28.6.2015
 */

public class RangeFilterGenerationStep implements ITerrainGenerationStep {
	private float limit;
	private float defaultValue;
	private boolean lessThan;

	public RangeFilterGenerationStep(float limit, boolean lessThan) {
		this( limit, lessThan, 0f );
	}

	public RangeFilterGenerationStep(float limit, boolean lessThan, float defaultValue) {
		this.limit = limit;
		this.lessThan = lessThan;
		this.defaultValue = defaultValue;
	}

	@Override
	public TerrainChunk onGenerateChunk(TerrainChunk targetChunk, long seed, int chunkX, int chunkY) {
		// Just pass trough
		return targetChunk;
	}

	@Override
	public float[] onGenerateValues(float[] values, long seed, int chunkX, int chunkY) {

		int size = Math.round( (float) Math.sqrt( values.length ) );

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				float value = values[x + y * size];
				value = (lessThan
						? (value < limit ? defaultValue : value)
						: (value > limit ? defaultValue : value));
				values[x + y * size] = value;
			}
		}

		return values;
	}
}
