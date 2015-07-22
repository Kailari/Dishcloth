package dishcloth.engine.util;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Queue.java
 * <p>
 * Simple quick-n-dirty ArrayList-backed implementation of Queue
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 21.7.2015
 */

public class Queue<T> extends AbstractQueue<T> {
	private List<T> internalList;

	public Queue() {
		this.internalList = new ArrayList<>();
	}

	@Override
	public Iterator<T> iterator() {
		// TODO: Implement iterator
		return internalList.iterator();
	}

	@Override
	public int size() {
		return internalList.size();
	}

	@Override
	public boolean offer(T t) {
		return internalList.add( t );
	}

	@Override
	public T poll() {
		if (size() > 0) {
			int index = size() - 1;
			return internalList.remove( index );
		}
		else {
			return null;
		}
	}

	@Override
	public T peek() {
		if (size() > 0) {
			int index = size() - 1;
			return internalList.get( index );
		}
		else {
			return null;
		}
	}
}
