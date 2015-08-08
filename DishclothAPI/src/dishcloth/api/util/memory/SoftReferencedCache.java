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

public class SoftReferencedCache<T> extends Cache<SoftReference<T>> {
	SoftReference<T> tmp;

	public T getReferencedItem() {
		tmp = super.getItem();
		return tmp == null ? null : tmp.get();
	}

	public void addReferencedItem(T item) {
		super.addItem( new SoftReference<>( item ) );
	}
}
