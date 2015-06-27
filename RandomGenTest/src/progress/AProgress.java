package progress;

import java.util.ArrayList;
import java.util.List;

/**
 * A debug tool to keep track of level's generation.
 * See PrgComponent for tutorial.
 * <p>
 * Created by Lassi on 25.6.2015.
 */
public abstract class AProgress {

	private List<PrgComponent> components;
	private boolean finished;
	private int currentIndex;

	private boolean printAlways;


	public AProgress(boolean printAlways) {
		components = new ArrayList<PrgComponent>();
		finished = false;
		currentIndex = 0;
		this.printAlways = printAlways;
	}

	public void add(PrgComponent c) {
		components.add( c );
	}


	private int getTotalWeight() {
		int r = 0;
		for (int i = 0; i < components.size(); i++) {
			r += components.get( i ).weight;
		}
		return r;
	}

	/**
	 * Return components[index].weight + components[index-1].weight+...
	 */
	private int getAccumulatedWeight(int index) {
		int r = 0;
		for (int i = 0; i < index; i++) {
			r += components.get( i ).weight;
		}
		return r;
	}


	public void update(int current) {
		current += 1; // usually 'int total' will be a size of array, and max index(=current) is one less than array size
		if (finished) return;
		if (currentIndex >= components.size()) {
			// Debug.logErr( "Index exceeds list capacity!", "Progress" );
			System.out.println( "Index >= components.size!!!" );
			return;
		}
		PrgComponent c = components.get( currentIndex );
		c.update( current );
		doPrint( false );
		if (c.full()) {
			next();
		}

	}

	private void next() {
		currentIndex++;
		if (currentIndex >= components.size()) {
			finished = true;
			doPrint( false );
		}
	}


	/**
	 * Returns the progress, this number will be multiplied by 100. <br>
	 * Will return for example 20 instead of 0.20.
	 */
	public float getProgress() {
		/* To clarify:
		* Progress p (this) has components c1,c2,c3. Each of them weight = 1
		* c1 is 100% completed  (c1 progress = 100%, total progress = 33%)
		* c2 is 50% completed   (c2 progress = 50%,  total progress = 33%+33%*50% = 50%)
		* c3 is 0% completed    (c3 progress = 0%,   total progress = 50%)
		*
		* Total progress = sum(completed.weight) + current.weight * current.percent
		* */

		if (finished) return 1F;
		if (components.size() == 0) return 0F;

		if (currentIndex >= components.size()) {
			// Debug.errLog("Error! Index > components size!", "Progress");
			System.out.println( "Error! Index > components size!" );
			return 0F;
		}

		PrgComponent c = components.get( currentIndex );

		float p1;
		if (currentIndex == 0) {
			p1 = 0;
		} else {
			// sum of completed components
			p1 = ((float) getAccumulatedWeight( currentIndex )) * 100F / getTotalWeight();
		}

		return p1 + (c.getPercent() * c.weight / getTotalWeight());
	}

	public String getProgressText() {
		if (finished) return "Completed!   100%" + "   Total progress: 100%";

		if (currentIndex >= components.size()) {
			// Debug.errLog("Error! Index > components size!", "Progress");
			System.out.println( "Error! Index > components size!" );
			return "Error! Index > components size!";
		}

		PrgComponent c = components.get( currentIndex );

		if (c.full()) return c.done + "   Total progress: " + getProgress() + "%";


		return c.status + "   " + c.getPercent() + "%" + "   Total progress: " + getProgress() + "%";
	}

	public void print() {
		doPrint( true );
	}

	/**
	 * @param external Did the request to print info come from other class
	 */
	private void doPrint(boolean external) {
		/* External  always  outcome    comment
		*  true      true    true       if other class requests, print
		*  true      false   true       same as above
		*  false     true    true       if 'printAlways' is true, print this class' requests
		*  false     false   false      if 'printAlways' is false, do not print this class' requests
		* */
		if (!external && !printAlways) return;

		if (finished) {
			// Debug.logOK(getProgressText(), "Progress");
			System.out.println( "OK: " + getProgressText() );
			return;
		}

		if (currentIndex >= components.size()) {
			// Debug.errLog("Error! Index > components size!", "Progress");
			System.out.println( "Error! Index > components size!" );
			return;
		}

		if (components.get( currentIndex ).full()) {
			// Debug.logOK(getProgressText(), "Progress");
			System.out.println( "OK: " + getProgressText() );
		} else {
			// Debug.logNote(getProgressText(), "Progress");
			System.out.println( "Note: " + getProgressText() );
		}

	}

}
