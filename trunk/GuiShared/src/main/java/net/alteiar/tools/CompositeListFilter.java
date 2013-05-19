package net.alteiar.tools;

import java.util.ArrayList;

public class CompositeListFilter<E> extends ListFilter<E> {

	private final ArrayList<ListFilter<E>> filters;

	public CompositeListFilter() {
		filters = new ArrayList<ListFilter<E>>();
	}

	public void addFilter(ListFilter<E> filter) {
		filters.add(filter);
	}

	@Override
	public Boolean accept(E element) {
		for (ListFilter<E> filter : filters) {
			if (!filter.accept(element)) {
				return false;
			}
		}
		return true;
	}

}
