package dishcloth.random;

/**
 * Created by Lassi on 20.5.2015.
 */
public class Speed {

	public double value;

	public Speed() {
		value = 0;
	}

	public Speed(double value) {
		this.value = value;
	}

	public void update(double time, Acceleration a) {
		value = time * a.value;

	}

	public String toString() {
		return WtfMain.approximate( value ) + " m/s";
	}
}
