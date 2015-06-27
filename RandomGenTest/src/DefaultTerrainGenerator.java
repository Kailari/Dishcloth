import progress.TerrainGenProgress;

/**
 * Created by Lassi on 25.6.2015.
 */
public class DefaultTerrainGenerator extends ATerrainGenerator {

	// All floats are [0,1]
	protected float terrainRoughness, caveAbundance, waterAbundance, goodies;

	public DefaultTerrainGenerator(long seed, int width, int height) {
		super( seed, width, height, getDirtMin( height ), getDirtMax( height ) );

		progress = new TerrainGenProgress( true, width, height );

		terrainRoughness = 0.20F;
	}

	public DefaultTerrainGenerator(int width, int height) {
		this( System.currentTimeMillis(), width, height );
	}


	private static int getDirtMin(int height) {
		return height * 5 / 10;
	}

	private static int getDirtMax(int height) {
		return height * 7 / 10;
	}

	@Override
	protected void generateTerrain() {

	}

	@Override
	protected void generateCaves() {

	}

	@Override
	protected void generateWater() {

	}

	@Override
	protected void generateGoodies() {

	}

}
