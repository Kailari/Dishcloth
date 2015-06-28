package randomGen;

import dishcloth.engine.world.level.TerrainChunk;
import randomGen.progress.AProgress;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lassi on 25.6.2015.
 */
public abstract class ATerrainGenerator {

	private final long seed;
	protected List<ITerrainGenerationStep> steps;

	protected AProgress progress;


	public ATerrainGenerator(long seed) {
		this.seed = seed;
		this.steps = new ArrayList<>();
	}

	public final TerrainChunk generateChunk(int chunkX, int chunkY) {
		TerrainChunk newChunk = new TerrainChunk( chunkX, chunkY );
		for (ITerrainGenerationStep step : this.steps) {
			newChunk = step.doGeneration( newChunk, this.seed, chunkX, chunkY );
		}
		return newChunk;
	}

	/**
	 * TEMPORARY
	 */
	public final float[] generateValues(int chunkX, int chunkY, int size) {
		float[] values = new float[size * size];
		for (ITerrainGenerationStep step : this.steps) {
			long startTime = System.currentTimeMillis();
			values = step.doGeneration( values, this.seed, chunkX, chunkY );
			System.out.println("Step \"" + step.getClass().getSimpleName() +"\" took "
					                   + ((double)(System.currentTimeMillis() - startTime) / 1000.0) + " seconds.");
		}
		return values;
	}
}
