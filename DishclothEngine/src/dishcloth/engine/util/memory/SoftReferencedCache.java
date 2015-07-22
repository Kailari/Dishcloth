package dishcloth.engine.util.memory;

import dishcloth.engine.util.Queue;

import java.lang.ref.SoftReference;

/**
 * SoftReferencedCache.java
 * <p>
 * Contains softly referenced cache of objects for allowing recycling them without having to
 * worry about running out of memory.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 21.7.2015
 */

public class SoftReferencedCache<T> {

	private Queue<SoftReference<T>> cache;

	public SoftReferencedCache() {
		this.cache = new Queue<>();
	}

	/**
	 * Tries to get a valid item from queue. If none are found, null is returned.
	 *
	 * @return T if a valid instance is found. Otherwise null.
	 */
	public T getItem() {

		while (cache.size() > 0) {
			T result;
			if ((result = cache.poll().get()) != null) {
				return result;
			}
		}

		return null;
	}

	public void addItem(T item) {
		this.cache.add( new SoftReference<>( item ) );
	}
}
