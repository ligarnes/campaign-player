package net.alteiar.campaign.player.gui.tools.adapter;

import net.alteiar.client.DocumentClient;

public class DocumentAdapter extends BasicAdapter<DocumentClient> {

	public DocumentAdapter(DocumentClient src) {
		super(src);
	}

	@Override
	public String toString() {
		return getObject().getDocumentPath().getName().toString();
	}

}
