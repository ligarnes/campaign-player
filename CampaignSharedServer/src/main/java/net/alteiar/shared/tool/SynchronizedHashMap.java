package net.alteiar.shared.tool;

import java.io.Serializable;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class SynchronizedHashMap<E, F> implements Serializable {

	private static final long serialVersionUID = -9120799793090634427L;

	private final HashMap<E, F> threadMap;
	private int counter = 0;

	public SynchronizedHashMap() {
		super();
		threadMap = new HashMap<E, F>();
	}

	/**
	 * synchronized add
	 * 
	 * @param item
	 *            - the item to add
	 */
	public synchronized void put(E key, F value) {
		try {
			// the is to wait until someone finish to read the list
			while (counter > 0) {
				wait();
			}
			threadMap.put(key, value);
		} catch (InterruptedException e) {
			System.out.println("Addition interrupted.");
		} finally {
			notifyAll();
		}
	}

	/**
	 * synchronized remove
	 * 
	 * @param key
	 *            - the key to remove
	 */
	public synchronized F remove(E key) {
		F removed = null;
		try {
			// the is to wait until someone finish to read the list
			while (counter > 0) {
				wait();
			}
			removed = threadMap.remove(key);
		} catch (InterruptedException e) {
			System.out.println("Removal interrupted.");
		} finally {
			notifyAll();
		}

		return removed;
	}

	/**
	 * The counter is usefull when you iterate over elements to not have
	 * 
	 * @see ConcurrentModificationException
	 */
	public synchronized void incCounter() {
		counter++;
		notifyAll();
	}

	public synchronized void decCounter() {
		counter--;
		notifyAll();
	}

	public F get(E key) {
		return threadMap.get(key);
	}

	public Set<E> keys() {
		return threadMap.keySet();
	}

	public Collection<F> values() {
		return threadMap.values();
	}

	public Set<Entry<E, F>> entrySet() {
		return threadMap.entrySet();
	}

	public int size() {
		return threadMap.size();
	}

	// This methode return an unmofiable collection so you can navigate
	/*
	public List<E> getUnmodifiableList() {
		return Collections.unmodifiableList(threadList);
	}

	public void addCollection(Collection<E> list) {
		threadList.addAll(list);
	}

	public Integer size() {
		return threadList.size();
	}*/
}
