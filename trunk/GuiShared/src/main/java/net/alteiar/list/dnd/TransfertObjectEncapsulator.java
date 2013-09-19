package net.alteiar.list.dnd;

public class TransfertObjectEncapsulator<E> {

	private final E obj;

	public TransfertObjectEncapsulator(E obj) {
		this.obj = obj;
	}

	public E getObject() {
		return obj;
	}
}
