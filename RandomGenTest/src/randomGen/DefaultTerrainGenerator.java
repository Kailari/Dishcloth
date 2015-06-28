package randomGen;

/**
 * Created by Lassi on 25.6.2015.
 */
public class DefaultTerrainGenerator extends ATerrainGenerator {

	public DefaultTerrainGenerator(long seed) {
		super( seed );
		//randomGen.progress = new TerrainGenProgress( true, width, height );

		this.steps.add( new SimplexNoiseHeightmapGenerationStep( seed ) );
	}

	public DefaultTerrainGenerator() {
		this( System.currentTimeMillis() );
	}
}
