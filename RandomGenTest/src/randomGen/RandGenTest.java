package randomGen;

import dishcloth.engine.world.level.TerrainChunk;
import randomGen.progress.DebugProgress;
import randomGen.progress.PrgComponent;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Lassi on 21.6.2015.
 */
public class RandGenTest {

	public static final int HEIGHTMAP_SIZE = 4; // * CHUNK_SIZE
	public static final int NOISE_PANEL_SIZE = 600;//HEIGHTMAP_SIZE * TerrainChunk.CHUNK_SIZE / 2;

	public static void main(String[] args) {
		//howToUse();

		JFrame frame = new JFrame( "SimplexNoise" );
		frame.setVisible( true );
		frame.setSize( NOISE_PANEL_SIZE, NOISE_PANEL_SIZE );
		frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
		frame.setLocationRelativeTo( null );
		NoisePanel panel = new NoisePanel();
		frame.add( panel );
		frame.addKeyListener( new NoisePanel.NoisePanelKeyListener( panel ) );

		EventQueue.invokeLater( panel::createNoiseImage );
	}

	private static void howToUse() {
		int width = 20, height = 40, numberOfTrees = 26;


		// T‰ss‰ on pieni tutoriali kuinka t‰t‰ progressia ajetaan.
		// DebugProgress on vaan debuggaamiseen. Se printtaa kaikki muutokset
		DebugProgress example = new DebugProgress();

		/* Eli siis tuohon randomGen.progress instanceen pistet‰‰n componentteja.
		 * Componenteilla on constructorissa argumentit:
		 *      int weight      componentin painoarvo
		 *      String status   viesti joka printataan kun generointi on kesken (esim. "Luodaan maastoa")
		 *      String done     viesti joka printataan kun generointi on valmis (esim. "Maasto luotu")
		 *      int total       componentin edistyminen lasketaan: prg = current / total;
		 *                      jossa current on muuttuja jota muutetaan componentin funktiolla
		 *                      update(x); -> current = x+1; (kato component class selitykseks)
		 */

		// 'terrain':lla on painoarvo 3, eli kun se on luotu, kokonaisedistyminen on 3/(3+1+1) = 60%
		PrgComponent terrain = new PrgComponent( 3, "Generating terrain...", "Terrain generated.", width * height );
		PrgComponent trees = new PrgComponent( 1, "Planting trees...", "Trees planted.", numberOfTrees );
		/* 'cave':n viimeinen argumennti on 1. T‰t‰ voidaan k‰ytt‰‰ tilanteissa joissa ei aluksi
		 * tiedet‰ ett‰ kuinka paljon total on. Esim. myˆhemmin laitetaan luolien m‰‰r‰ksi random.
		 */
		PrgComponent caves = new PrgComponent( 1, "Carving caves...", "Caves carved.", 1 );

		// Ja lopuks lis‰t‰‰n componentit progressiin
		example.add( terrain );
		example.add( trees );
		example.add( caves );


		// T‰ss‰ esimerkki:
		int[] blocks = new int[width * height]; // 'blocks' ei k‰ytet‰ mihink‰‰n t‰ss‰, se voisi olla levelin blockit
		for (int y = 0; y < height; y++) {
			int index = 0;
			for (int x = 0; x < width; x++) {
				index = x + y * width;
				blocks[index] = 1; // 1 korvataan jollain block id:ll‰
			}
			example.update( index ); // t‰m‰ voisi olla for(x) loopin sis‰ll‰, mutta ajattelin ettei tarvi niin montaa printti‰
			// kun example updatetaan, ja jos argumentti on yht‰suuri tai suurempi kuin total, niin siiryt‰‰n seuraavaan componenttiin
		}

		for (int i = 0; i < numberOfTrees; i++) { // numberOfTrees = 26
			String tree = "new Tree(randomX, randomY)"; // luo uusi puu ja silleen...
			if (i % 5 == 0) {
				example.update( i - 1 ); // v‰hennet‰‰n v‰h‰n printtien m‰‰r‰‰. Argumentissa pit‰‰ v‰hent‰‰ 1, koska katso update
			}
		}
		// viimeinen kerta kun kutsuttiin 'example.update(a);' oli kun i oli 25, eli ollaan viel‰ tree componentissa
		example.update( numberOfTrees - 1 );


		int numberOfCaves = 21; // numberOfCaves = random.nextInt(50);

		for (int i = 0; i < numberOfCaves; i++) {
			String cave = "new Cave(randomX, randomY)";
			// t‰ss‰ vaiheessa ei voida kutsua 'example.update(i)'
		}
		example.update( 0 ); // t‰ll‰ p‰‰st‰‰n seuraavaan componenttiin


	}


}
