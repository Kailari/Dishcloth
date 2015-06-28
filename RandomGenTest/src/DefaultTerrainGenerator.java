import dishcloth.engine.world.level.TerrainChunk;
import progress.TerrainGenProgress;

/**
 * Created by Lassi on 25.6.2015.
 */
public class DefaultTerrainGenerator extends ATerrainGenerator {

	public DefaultTerrainGenerator(long seed) {
		super( seed );
		//progress = new TerrainGenProgress( true, width, height );

		this.steps.add( new SimplexNoiseHeightmapGenerationStep( seed ) );
	}

	public DefaultTerrainGenerator() {
		this( System.currentTimeMillis() );
	}
}
