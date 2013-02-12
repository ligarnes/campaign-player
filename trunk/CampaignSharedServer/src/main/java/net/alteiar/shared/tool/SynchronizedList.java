package net.alteiar.shared.tool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class SynchronizedList<E> implements Serializable {

	private static final long serialVersionUID = -9120799793090634427L;

	private final List<E> threadList;
	private int counter = 0;

	public SynchronizedList() {
		super();
		threadList = new ArrayList<E>();
	}

	/**
	 * synchronized add
	 * 
	 * @param item
	 *            - the item to add
	 */
	public synchronized void add(E item) {
		try {
			// the is to wait until someone finish to read the list
			while (counter > 0) {
				wait();
			}
			threadList.add(item);
		} catch (InterruptedException e) {
			System.out.println("Addition interrupted.");
		} finally {
			notifyAll();
		}
	}

	/**
	 * synchronized remove
	 * 
	 * @param item
	 *            - the item to remove
	 */
	public synchronized void remove(E item) {
		try {
			// the is to wait until someone finish to read the list
			while (counter > 0) {
				wait();
			}
			threadList.remove(item);
		} catch (InterruptedException e) {
			System.out.println("Removal interrupted.");
		} finally {
			notifyAll();
		}
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

	// This methode return an unmofiable collection so you can navigate
	/*
	public List<E> getUnmodifiableList() {
		return Collections.unmodifiableList(threadList);
	}*/

	public void toArray(E[] values) {
		threadList.toArray(values);
	}

	/*
	public void addCollection(Collection<E> list) {
		threadList.addAll(list);
	}*/

	public Integer size() {
		return threadList.size();
	}
}
