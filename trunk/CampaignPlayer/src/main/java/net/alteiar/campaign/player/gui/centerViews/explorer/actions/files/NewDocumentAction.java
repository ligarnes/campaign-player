package net.alteiar.campaign.player.gui.centerViews.explorer.actions.files;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.alteiar.campaign.player.gui.documents.PanelCreateDocument;
import net.alteiar.documents.BeanDirectory;

public class NewDocumentAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final BeanDirectory directory;

	public NewDocumentAction(BeanDirectory dir) {
		this.directory = dir;

		putValue(NAME, "Nouveau document");
		// putValue(LARGE_ICON_KEY, Helpers.getIcon(ICON_SHOW_GRID));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		PanelCreateDocument.createDocument(directory);
	}

}
