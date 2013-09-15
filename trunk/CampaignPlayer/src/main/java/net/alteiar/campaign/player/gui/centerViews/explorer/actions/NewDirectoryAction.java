package net.alteiar.campaign.player.gui.centerViews.explorer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.dialog.DialogOkCancel;
import net.alteiar.documents.BeanDirectory;

public class NewDirectoryAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final BeanDirectory parent;

	public NewDirectoryAction(BeanDirectory dir) {
		this.parent = dir;

		putValue(NAME, "Ajouter un dossier");
		// putValue(LARGE_ICON_KEY, Helpers.getIcon(ICON_SHOW_GRID));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DialogOkCancel<PanelChangeName> dlg = new DialogOkCancel<PanelChangeName>(
				null, "Nouveau dossier", true, new PanelChangeName(
						"nouveau dossier"));

		dlg.setLocationRelativeTo(null);
		dlg.setVisible(true);

		if (dlg.getReturnStatus() == DialogOkCancel.RET_OK) {
			String dirName = dlg.getMainPanel().getDocumentName();
			CampaignClient.getInstance().addBean(
					new BeanDirectory(parent, dirName));

		}
	}
}
