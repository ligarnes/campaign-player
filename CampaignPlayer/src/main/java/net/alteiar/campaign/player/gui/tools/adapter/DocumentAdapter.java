package net.alteiar.campaign.player.gui.tools.adapter;

import net.alteiar.sharedDocuments.SharedDocumentBasicBeans;

public class DocumentAdapter extends BasicAdapter<SharedDocumentBasicBeans> {

	public DocumentAdapter(SharedDocumentBasicBeans src) {
		super(src);
	}

	@Override
	public String toString() {
		return getObject().getId().toString();
	}

}
