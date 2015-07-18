package dishcloth.engine.world.generation.step;

import dishcloth.engine.world.level.TerrainChunk;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * ATerrainGenerationStep.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 29.6.2015
 */

public abstract class ATerrainGenerationStep implements ITerrainGenerationStep {

	@Override
	public TerrainChunk onGenerateChunk(TerrainChunk targetChunk, float[] values, long seed, int chunkX, int chunkY) {
		return targetChunk;
	}

	@Override
	public float[] onGenerateValues(float[] values, long seed, int chunkX, int chunkY) {
		return values;
	}
}
