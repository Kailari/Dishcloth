package dishcloth.api.util.memory;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * SoftReferencedCache.java
 * <p>
 * Contains softly referenced cache of objects for allowing recycling them without having to
 * worry about running out of memory.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 21.7.2015
 */

public class Cache<T> {

	private List<T> cache;

	public Cache() {
		this.cache = new ArrayList<>();
	}

	/**
	 * Tries to get a valid item from queue. If none are found, null is returned.
	 *
	 * @return T if a valid instance is found. Otherwise null.
	 */
	public T getItem() {

		int index;
		while (cache.size() > 0) {
			index = cache.size() - 1;
			T result = cache.get( index );
			cache.remove( index );
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	public void addItem(T item) {
		this.cache.add( item );
	}
}
