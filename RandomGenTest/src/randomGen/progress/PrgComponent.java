package randomGen.progress;

/**
 * Components for randomGen.progress debugging tool.
 * <p>
 * Quick tutorial how to use: <br>
 * PrgComponent c1 = new PrgComponent(2, "Creating terrain", "Terrain generated", 100); <br>
 * PrgComponent c2 = new PrgComponent(1, "Creating forest", "Forest generated", 20); <br>
 * This means that total weight(in randomGen.progress class) will be 3, so after
 * generating terrain(c1 completed) randomGen.progress will be 66%.
 * <p>
 * As for the "int total":
 * Our terrain would have 100 tiles (c1), and forest would have 10 trees (c2).
 * After we have generated 10 tiles, we call 'update(10)'(*). <br>
 * So total randomGen.progress will be(where c = PrgComponent, p = Progress): <br>
 * p.percent * c.percent <br>
 * = (c.weight / p.totalWeight) * (c.current / c.total) * 100F <br>
 *
 * (*) = actually we would call 'update(9)', sine we are probably in a for loop. This will be handled in the update method.
 * <p>
 * Created by Lassi on 25.6.2015.
 */
public class PrgComponent {

	public final int weight, total;
	public final String status, done;

	private int current;


	public PrgComponent(int weight, String status, String done, int total) {
		this.weight = weight;
		this.status = status;
		this.done = done;
		this.total = total;
		current = 0;
	}

	/**
	 * Returns the current randomGen.progress. The number will be multiplied with 100. <br>
	 * (For example returns 20 instead of 0.20)
	 *
	 * @return return current * 100F / total;
	 */
	public float getPercent() {
		float p = current * 100F / total;
		if (p < 0F) p = 0F;
		if (p > 100F) p = 100F;
		return p;
	}

	protected void update(int current) {
		this.current = current;
	}

	public boolean full() {
		return current == total;
	}
}
