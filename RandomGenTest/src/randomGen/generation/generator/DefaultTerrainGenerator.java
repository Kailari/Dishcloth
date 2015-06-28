package randomGen.generation.generator;

import randomGen.generation.step.RangeFilterGenerationStep;
import randomGen.generation.step.SimplexNoiseHeightmapGenerationStep;
import randomGen.generation.step.YHeightGenerationStep;

/**
 * Created by Lassi on 25.6.2015.
 */
public class DefaultTerrainGenerator extends ATerrainGenerator {

	public DefaultTerrainGenerator(long seed) {
		super( seed );
		//randomGen.progress = new TerrainGenProgress( true, width, height );

		this.steps.add( new SimplexNoiseHeightmapGenerationStep( seed ) );
		this.steps.add( new RangeFilterGenerationStep( 0.2f, true ) );
		//this.steps.add( new RangeFilterGenerationStep( 0.9f, false, 1f ) );
		this.steps.add( new YHeightGenerationStep( 128, true ) );

	}

	public DefaultTerrainGenerator() {
		this( System.currentTimeMillis() );
	}
}
