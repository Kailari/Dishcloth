package randomGen.generation.step;

import dishcloth.engine.world.level.TerrainChunk;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * randomGen.generation.step.ITerrainGenerationStep.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 28.6.2015
 */

public interface ITerrainGenerationStep {

	/**
	 * Takes in a terrainChunk and applies a terrain generation operation on it.
	 *
	 * @param targetChunk chunk to modify
	 * @param seed        random generation seed
	 * @param chunkX      Position of the chunk on x-axis. (in chunks)
	 * @param chunkY      Position of the chunk on y-axis. (in chunks)
	 * @return The modified chunk
	 */
	TerrainChunk onGenerateChunk(TerrainChunk targetChunk, float[] values, long seed, int chunkX, int chunkY);

	/**
	 * Temporary. There is no idea in debugging terrain generation directly with TerrainChunks.
	 * Assume values to be float[256 * 256]
	 * (Also, terrain renderer is still WIP and doesn't work yet; thus, its better use heightmaps saved to file for now)
	 */
	float[] onGenerateValues(float[] values, long seed, int chunkX, int chunkY);
}
