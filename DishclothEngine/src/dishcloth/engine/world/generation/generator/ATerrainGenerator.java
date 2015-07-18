package dishcloth.engine.world.generation.generator;

import dishcloth.engine.world.level.TerrainChunk;
import dishcloth.engine.world.generation.step.ITerrainGenerationStep;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lassi on 25.6.2015.
 */
public abstract class ATerrainGenerator {

	private final long seed;
	protected List<ITerrainGenerationStep> steps;


	public ATerrainGenerator(long seed) {
		this.seed = seed;
		this.steps = new ArrayList<>();
	}

	// TODO: Design the objects/entity -storage. This function should return both TerrainChunk and populator results.
	public final TerrainChunk generate(int chunkX, int chunkY) {
		float[] values = generateValues( chunkX, chunkY );
		TerrainChunk chunk = generateChunk( chunkX, chunkY, values );

		// TODO: Run populator or sth.

		return chunk;
	}

	private TerrainChunk generateChunk(int chunkX, int chunkY, float[] values) {
		TerrainChunk newChunk = new TerrainChunk( chunkX, chunkY );
		for (ITerrainGenerationStep step : this.steps) {
			newChunk = step.onGenerateChunk( newChunk, values, this.seed, chunkX, chunkY );
		}
		return newChunk;
	}

	private float[] generateValues(int chunkX, int chunkY) {
		float[] values = new float[TerrainChunk.CHUNK_SIZE * TerrainChunk.CHUNK_SIZE];
		for (ITerrainGenerationStep step : this.steps) {
			long startTime = System.currentTimeMillis();
			values = step.onGenerateValues( values, this.seed, chunkX, chunkY );
			System.out.println("Step \"" + step.getClass().getSimpleName() +"\" took "
					                   + (double)(System.currentTimeMillis() - startTime) + " ms");
		}
		return values;
	}
}
