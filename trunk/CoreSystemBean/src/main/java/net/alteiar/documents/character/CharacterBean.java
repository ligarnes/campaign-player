package net.alteiar.documents.character;

import net.alteiar.documents.AuthorizationBean;

public abstract class CharacterBean extends AuthorizationBean {
	private static final long serialVersionUID = 1L;

	public abstract String getVisibleName();
}
