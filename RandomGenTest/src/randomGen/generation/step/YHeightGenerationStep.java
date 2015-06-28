package randomGen.generation.step;

import dishcloth.engine.world.level.TerrainChunk;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * YHeightGenerationStep.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 28.6.2015
 */

public class YHeightGenerationStep implements ITerrainGenerationStep {

	private int yLimit;
	private boolean lessThan;

	public YHeightGenerationStep(int yLimit, boolean lessThan) {
		this.yLimit = yLimit;
		this.lessThan = lessThan;
	}

	@Override
	public TerrainChunk onGenerateChunk(TerrainChunk targetChunk, long seed, int chunkX, int chunkY) {
		return targetChunk;
	}

	@Override
	public float[] onGenerateValues(float[] values, long seed, int chunkX, int chunkY) {
		int size = Math.round( (float) Math.sqrt( values.length ) );

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				float value = values[x + y * size];
				value = (lessThan
						? (y < yLimit ? 0f : value)
						: (y > yLimit ? 0f : value));
				values[x + y * size] = value;
			}
		}

		return values;
	}
}
