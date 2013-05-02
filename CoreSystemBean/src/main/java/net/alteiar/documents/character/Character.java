package net.alteiar.documents.character;

import net.alteiar.documents.AuthorizationBean;

public abstract class Character extends AuthorizationBean {
	private static final long serialVersionUID = 1L;

	public Character() {
		super();
	}

	public Character(String name) {
		super(name);
	}
}
