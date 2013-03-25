package net.alteiar.character;

import net.alteiar.sharedDocuments.SharedDocumentBasicBeans;

public abstract class CharacterBean extends SharedDocumentBasicBeans {
	private static final long serialVersionUID = 1L;

	public abstract String getVisibleName();
}
