import progress.AProgress;

import java.util.Random;

/**
 * Created by Lassi on 25.6.2015.
 */
public abstract class ATerrainGenerator {

	protected final Random random;
	private final long seed;
	protected int width, height;
	protected int dirtMin, dirtMax; // Min and max levels for dirt layer


	protected int[] blocks; // to be replaced with 'protected Block[] blocks;' or something


	protected AProgress progress;


	public ATerrainGenerator(long seed, int width, int height, int dirtMin, int dirtMax) {
		this.seed = seed;
		random = new Random( seed );

		this.width = width;
		this.height = height;

		this.dirtMin = dirtMin;
		this.dirtMax = dirtMax;

		blocks = new int[width * height];
	}

	private final void doGeneration() {
		generateTerrain();
		generateCaves();
		generateWater();
		generateGoodies();
	}

	protected abstract void generateTerrain();

	protected abstract void generateCaves();

	protected abstract void generateWater();

	protected abstract void generateGoodies();

}
