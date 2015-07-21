package dishcloth.engine.world.generation.generator;

import dishcloth.engine.util.logger.ANSIColor;
import dishcloth.engine.util.logger.Debug;
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
		Debug.log( "Starting chunk generation: "
				           + ANSIColor.BLACK + ANSIColor.GREEN_BACKGROUND
				           + "( " + chunkX + ", " + chunkY + " )"
				           + ANSIColor.RESET
				, this );

		long startTime = System.currentTimeMillis();

		// Value generation
		Debug.log("\tStarting value generation phase", this);
		float[] values = generateValues( chunkX, chunkY );

		long valuesTime = System.currentTimeMillis();
		Debug.log( "\tValue generation phase completed in "
				           + ANSIColor.BLUE
				           + (valuesTime - startTime)
				           + ANSIColor.RESET
				           + " ms", this );

		// Chunk generation
		Debug.log("\tStarting chunk generation phase", this);
		TerrainChunk chunk = generateChunk( chunkX, chunkY, values );

		long chunksTime = System.currentTimeMillis();
		Debug.log( "\tChunk generation phase completed in "
				           + ANSIColor.BLUE
				           + (chunksTime - valuesTime)
				           + ANSIColor.RESET
				           + " ms", this );

		// World population
		Debug.log("\tStarting world population phase", this);
		// TODO: Run populator or sth.

		long populatorTime = System.currentTimeMillis();
		Debug.log( "\tWorld population phase completed in "
				           + ANSIColor.BLUE
				           + (populatorTime - chunksTime)
				           + ANSIColor.RESET
				           + " ms", this );

		Debug.log( "Chunk generated in "
				           + ANSIColor.BLUE
				           + (populatorTime - startTime)
				           + ANSIColor.RESET
				           + " ms"
				, this );

		return chunk;
	}

	private TerrainChunk generateChunk(int chunkX, int chunkY, float[] values) {
		TerrainChunk newChunk = new TerrainChunk( chunkX, chunkY );
		for (ITerrainGenerationStep step : this.steps) {
			long startTime = System.currentTimeMillis();
			newChunk = step.onGenerateChunk( newChunk, values, this.seed, chunkX, chunkY );

			Debug.log( "\t\tStep \"" + step.getClass().getSimpleName() + "\" took "
					           + ANSIColor.BLUE
					           + (System.currentTimeMillis() - startTime)
					           + ANSIColor.RESET + " ms", this );
		}
		return newChunk;
	}

	private float[] generateValues(int chunkX, int chunkY) {
		float[] values = new float[TerrainChunk.CHUNK_SIZE * TerrainChunk.CHUNK_SIZE];
		for (ITerrainGenerationStep step : this.steps) {
			long startTime = System.currentTimeMillis();
			values = step.onGenerateValues( values, this.seed, chunkX, chunkY );

			Debug.log( "\t\tStep \"" + step.getClass().getSimpleName() + "\" took "
					           + ANSIColor.BLUE
					           + (System.currentTimeMillis() - startTime)
					           + ANSIColor.RESET + " ms", this );
		}
		return values;
	}
}
