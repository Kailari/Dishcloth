package randomGen.progress;

/**
 * Created by Lassi on 25.6.2015.
 */
public class TerrainGenProgress extends AProgress {
	/**
	 *
	 * @param printAlways Always print randomGen.progress (debugging)
	 * @param width width of the level
	 * @param height height of the level
	 */
	public TerrainGenProgress(boolean printAlways, int width, int height) {
		super( printAlways );

		PrgComponent terrain = new PrgComponent( 10, "Generating terrain...", "Terrain generated!", width * height );
		PrgComponent caves = new PrgComponent( 2, "Generating caves...", "Caves generated!", 1 );
		PrgComponent water = new PrgComponent( 2, "Generating water...", "Water generated!", 1 );
		PrgComponent ores = new PrgComponent( 2, "Generating ores...", "Ores generated!", 1 );


		add( terrain );
		add( caves );
		add( water );
		add( ores );
	}
}
