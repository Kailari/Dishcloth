package dishcloth.engine.rendering.text.bitmapfont;

import java.util.HashMap;

/**
 * Kernings.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 25.7.2015
 */

public class Kernings {
	private HashMap<Character, Integer> pairs;
	
	public Kernings() {
		this.pairs = new HashMap<>();
	}
	
	public void addKerning(char secondID, int amount) {
		this.pairs.put( secondID, amount );
	}
	
	public int getKerningAmmount(char secondID) {
		return (pairs.containsKey( secondID ) ? pairs.get( secondID ) : 0);
	}
}
