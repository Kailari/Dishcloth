package randomGen.generation.generator;

import randomGen.generation.step.*;

/**
 * Created by Lassi on 25.6.2015.
 */
public class DefaultTerrainGenerator extends ATerrainGenerator {

	public DefaultTerrainGenerator(long seed) {
		super( seed );
		//randomGen.progress = new TerrainGenProgress( true, width, height );

		this.steps.add( new SimplexNoiseHeightmapGenerationStep( seed,      // Seed
		                                                         8,         // n of octaves
		                                                         2.0f,       // persistence
		                                                         0.5f,       // gain
		                                                         50.0f,      // amplitude
		                                                         0.002f ) ); // frequency
		//this.steps.add( new NormalizeGenerationStep() );

		// TODO: Extend gradient generation to support curves. This would allow merging all gradient calls
		// TODO: Create MaskedStep that evaluates another step, but only to values from given mask.
		this.steps.add( new GradientGenerationStep( -512, 512, 0.0f, 0.1f ) );
		this.steps.add( new GradientGenerationStep( 512, 1024, 0.1f, 1.0f ) );

		//this.steps.add( new RangeFilterGenerationStep( 0.35f, true, 0.5f ) );
		//this.steps.add( new RangeFilterGenerationStep( 0.1f, false, 1f ) );

		ATerrainGenerationStep surfaceStep = new GroundSurfaceGenerationStep( 512.0f, 16.0f, 512, 128 );
		this.steps.add( surfaceStep );

	}

	public DefaultTerrainGenerator() {
		this( System.currentTimeMillis() );
	}
}
