package net.alteiar.campaign.player.gui.centerViews.explorer.actions.files;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.documents.BeanDocument;

public class DeleteDocumentAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final BeanDocument file;

	public DeleteDocumentAction(BeanDocument file) {
		this.file = file;

		putValue(NAME, "Supprimer document");
		// putValue(LARGE_ICON_KEY, Helpers.getIcon(ICON_SHOW_GRID));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CampaignClient.getInstance().removeBean(file);
	}

}
