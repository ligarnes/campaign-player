package net.alteiar.tools;

import java.util.Collection;

public abstract class ListFilter<E> {

	public abstract Boolean accept(E element);

	public final static <E> void filterList(Collection<E> org,
			Collection<E> target, ListFilter<E> filter) {

		for (E element : org) {
			if (filter.accept(element)) {
				target.add(element);
			}
		}
	}
}
