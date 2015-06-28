package randomGen;

import dishcloth.engine.util.math.DishMath;

import java.util.Random;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * randomGen.SimplexNoise.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Simplex noise.
 * <p>
 * Detailed explanation about how simplex noise works:
 * - http://webstaff.itn.liu.se/~stegu/simplexnoise/simplexnoise.pdf
 * - https://en.wikipedia.org/wiki/Simplex_noise
 * - https://code.google.com/p/fractalterraingeneration/wiki/Simplex_Noise
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 28.6.2015
 */

public class SimplexNoise {

	private static final int N_GRADIENTS = 12;
	private static final double GRADIENT_STEP = 360f / N_GRADIENTS;
	private static final double SKEWING_FACTOR = (double) ((Math.sqrt( 3.0 ) - 1.0) / 2.0);
	private static final double UNSKEWING_FACTOR = (double) ((1.0 / Math.sqrt( 3.0 ) - 1.0) / -2.0);

	private short[] permutationTable;
	private Gradient[] gradientTable = new Gradient[N_GRADIENTS];

	private double amplitude;
	/**
	 * Octave modifier for amplitude
	 * <p>
	 * amplitude for n:th octave is calculated as 'amplitude * gain^n'
	 */
	private double gain;
	private double frequency;
	/**
	 * Octave modifier for frequency
	 * <p>
	 * frequency for n:th octave is calculated as 'frequency * lacunarity^n'
	 */
	private double lacunarity;

	private int octaves;

	public SimplexNoise(long seed, int octaves) {
		this( seed, octaves, 0.2, 0.65 );
	}

	public SimplexNoise(long seed, int octaves, double lacunarity, double gain) {
		this( seed, octaves, lacunarity, gain, 50.0, 0.002 );
	}

	public SimplexNoise(long seed, int octaves, double lacunarity, double gain, double amplitude, double frequency) {
		this.octaves = octaves;
		this.lacunarity = lacunarity;
		this.gain = gain;
		this.amplitude = amplitude;
		this.frequency = frequency;

		prepareGradientTable();
		preparePermutationTable( seed );
	}

	private static double dot(Gradient g, double x, double y) {
		return g.x * x + g.y * y;
	}

	private void preparePermutationTable(long seed) {

		short[] tmpSourceTable = new short[256];

		// Throw indices into the table
		for (int i = 0; i < tmpSourceTable.length; i++) {
			tmpSourceTable[i] = (short) i;
		}

		// Shuffle the table
		Random random = new Random( seed );
		for (int i = 0; i < tmpSourceTable.length; i++) {
			short tmp = tmpSourceTable[i];
			int index = Math.abs( random.nextInt() % tmpSourceTable.length );
			tmpSourceTable[i] = tmpSourceTable[index];
			tmpSourceTable[index] = tmp;
		}

		permutationTable = new short[512];
		for (int i = 0; i < permutationTable.length; i++) {
			permutationTable[i] = tmpSourceTable[i & 255];
		}
	}

	private void prepareGradientTable() {
		double angle;
		for (int i = 0; i < gradientTable.length; i++) {
			angle = Math.toRadians( i * GRADIENT_STEP );
			gradientTable[i] = new Gradient( Math.cos( angle ), Math.sin( angle ) );
		}
	}

	public double generate(double x, double y) {
		double freq = frequency;
		double ampl = amplitude;
		double value = 0.0;

		for (int octave = 0; octave < octaves; octave++) {
			value += ampl * simplexNoise( x * freq, y * freq );

			freq *= lacunarity;
			ampl *= gain;
		}

		return value;
	}

	private double simplexNoise(double x, double y) {
		double skew = (x + y) * SKEWING_FACTOR;

		// Simplex cell coordinates
		int i = DishMath.fastFloor( x + skew );
		int j = DishMath.fastFloor( y + skew );

		double unskew = (i + j) * UNSKEWING_FACTOR;

		// Unskewed cell origin x/y
		double ox = i - unskew;
		double oy = j - unskew;

		// x/y distances from the cell origin
		double x0 = x - ox;
		double y0 = y - oy;

		// middle corner offsets
		int i1 = x0 > y0 ? 1 : 0;
		int j1 = x0 > y0 ? 0 : 1;

		double x1 = x0 - i1 + UNSKEWING_FACTOR;
		double y1 = y0 - j1 + UNSKEWING_FACTOR;
		double x2 = x0 - 1.0f + 2.0f * UNSKEWING_FACTOR;
		double y2 = y0 - 1.0f + 2.0f * UNSKEWING_FACTOR;

		// Some weird hashing
		// TODO: Find out if this is necessary and simplify the hell out of it if possible
		// XXX: Maybe the permutation table could be replaced with hash-function?
		int ii = i & 255;
		int jj = j & 255;
		int firstCornerGradientIndex = permutationTable[ii + permutationTable[jj]] % N_GRADIENTS;
		int middleCornerGradientIndex = permutationTable[ii + i1 + permutationTable[jj + j1]] % N_GRADIENTS;
		int lastCornerGradientIndex = permutationTable[ii + 1 + permutationTable[jj + 1]] % N_GRADIENTS;


		// Calculate contributions from the three corners.
		// XXX: I have no idea how this thing can work.
		//      (From here on code is almost directly copied from other implementation. There have probably been some
		//      major adjustments in the algorithms so I haven't quite figured out yet which is which.)

		double firstCornerContribution, middleCornerContribution, lastCornerContribution;

		// XXX: t0 might be radius of the circle around the simplex on first corner?
		double t0 = 0.5f - x0 * x0 - y0 * y0;
		if (t0 < 0f) {
			firstCornerContribution = 0.0f;
		} else {
			t0 *= t0;
			firstCornerContribution = t0 * t0 * dot( gradientTable[firstCornerGradientIndex], x0, y0 );
		}

		double t1 = 0.5f - (x1 * x1) - (y1 * y1);
		if (t1 < 0f) {
			middleCornerContribution = 0.0f;
		} else {
			t1 *= t1;
			middleCornerContribution = t1 * t1 * dot( gradientTable[middleCornerGradientIndex], x1, y1 );
		}

		double t2 = 0.5f - (x2 * x2) - (y2 * y2);
		if (t2 < 0f) {
			lastCornerContribution = 0.0f;
		} else {
			t2 *= t2;
			lastCornerContribution = t2 * t2 * dot( gradientTable[lastCornerGradientIndex], x2, y2 );
		}

		// Calculate the sum and scale the result to be [-1.0, 1.0]
		// XXX: Where the fuck does that 70.0 come from!?
		return 70.0 * (firstCornerContribution + middleCornerContribution + lastCornerContribution);
	}

	private static class Gradient {
		private double x, y;

		private Gradient(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}
}
