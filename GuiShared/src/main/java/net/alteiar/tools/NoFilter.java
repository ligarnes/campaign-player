package net.alteiar.tools;

public class NoFilter<E> extends ListFilter<E> {

	@Override
	public Boolean accept(E element) {
		return true;
	}

}
