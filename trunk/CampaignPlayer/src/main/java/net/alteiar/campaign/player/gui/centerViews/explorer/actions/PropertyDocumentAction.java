package net.alteiar.campaign.player.gui.centerViews.explorer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.alteiar.campaign.player.gui.MainFrame;
import net.alteiar.campaign.player.gui.centerViews.explorer.mine.PanelPropertyEditor;
import net.alteiar.dialog.DialogOkCancel;
import net.alteiar.documents.BeanBasicDocument;

public class PropertyDocumentAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final BeanBasicDocument doc;

	public PropertyDocumentAction(BeanBasicDocument doc) {
		this.doc = doc;

		putValue(NAME, "Propriété");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DialogOkCancel<PanelPropertyEditor> dlg = new DialogOkCancel<PanelPropertyEditor>(
				MainFrame.FRAME, "Propriété du document", true,
				new PanelPropertyEditor(doc));

		dlg.setLocationRelativeTo(null);
		dlg.setVisible(true);

		if (dlg.getReturnStatus() == DialogOkCancel.RET_OK) {
			PanelPropertyEditor prop = dlg.getMainPanel();
			doc.setDocumentName(prop.getDocumentName());
			doc.setPublic(prop.isPublic());
		}
	}
}
