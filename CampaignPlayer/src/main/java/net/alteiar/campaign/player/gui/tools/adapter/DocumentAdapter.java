package net.alteiar.campaign.player.gui.tools.adapter;

import net.alteiar.server.document.DocumentClient;

public class DocumentAdapter extends BasicAdapter<DocumentClient> {

	public DocumentAdapter(DocumentClient src) {
		super(src);
	}

	@Override
	public String toString() {
		return getObject().getFilename();
	}

}
