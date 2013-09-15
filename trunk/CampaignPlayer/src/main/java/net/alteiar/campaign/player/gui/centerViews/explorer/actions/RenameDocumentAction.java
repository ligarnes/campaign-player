package net.alteiar.campaign.player.gui.centerViews.explorer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.alteiar.dialog.DialogOkCancel;
import net.alteiar.documents.BeanBasicDocument;

public class RenameDocumentAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final BeanBasicDocument doc;

	public RenameDocumentAction(BeanBasicDocument doc) {
		this.doc = doc;

		putValue(NAME, "Renommer");
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		DialogOkCancel<PanelChangeName> dlg = new DialogOkCancel<PanelChangeName>(
				null, "Renommer le document", true, new PanelChangeName(
						doc.getDocumentName()));

		dlg.setLocationRelativeTo(null);
		dlg.setVisible(true);

		if (dlg.getReturnStatus() == DialogOkCancel.RET_OK) {
			doc.setDocumentName(dlg.getMainPanel().getDocumentName());
		}
	}
}
