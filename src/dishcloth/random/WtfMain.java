package dishcloth.random;

import java.text.DecimalFormat;

/**
 * Created by Lassi on 20.5.2015.
 */
public class WtfMain implements Runnable {

	private Speed speed;
	private Acceleration ac;

	public WtfMain() {

		speed = new Speed( 5.0 );
		ac = new Acceleration( 1.2 );
		Thread thread = new Thread( this, "WTF" );
		thread.start();

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 7; x++) {
				new Ilari( x, y );
			}
		}

	}

	public static String approximate(double d) {
		DecimalFormat df = new DecimalFormat( "#.##" );
		return df.format( d );
	}


	public void run() {

		try {
			printSpeed();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Yritin printata sanicin mutta editori ei tunne jotain unicode merkkejä :( <br>
	 * Mutta silti gotta go fast
	 */
	private void printSpeed() throws InterruptedException {
		double t = 0;
		System.out.println( "Speed:" + tab( 3 ) + "Acceleration:" );
		for (int i = 0; i < 10; i++) {
			System.out.println( speed + tab( 3 ) + ac );

			t++;
			Thread.sleep( 500 );
			speed.update( t, ac );
		}

		Thread.sleep( 500 );
		System.out.println( "Let's speed thing up" );
		Thread.sleep( 2000 );
		ac.value = 99999;
		for (int i = 0; i < 15; i++) {
			String s = speed + tab( 3 ) + ac;
			System.out.println( s );
			t++;
			Thread.sleep( 250 - i * 15 );
			speed.update( t, ac );
		}

		for (int i = 0; i < 80; i++) {
			String s = speed + tab( 3 ) + ac;
			String s2 = "";
			if (i % 2 == 0) s2 = " ";
			s2 += s;
			System.out.println( s2 );
			t += 0.5;
			Thread.sleep( 50 - i / 3 );
			speed.update( t, ac );
		}

	}


	private String tab(int n) {
		String tab = "  ";
		String s = "";
		for (int i = 0; i < n; i++) {
			s += tab;
		}
		return s;
	}
}

