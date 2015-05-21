package dishcloth.random;

/**
 * Created by Lassi on 20.5.2015.
 */
public class Acceleration {

	public double value;

	public Acceleration() {
		value = 0;
	}

	public Acceleration(double value) {
		this.value = value;
	}


	public String toString() {
		return value + " m/s^2";
	}
}
