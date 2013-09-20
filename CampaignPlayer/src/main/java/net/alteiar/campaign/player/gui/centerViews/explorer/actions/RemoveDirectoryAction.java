package net.alteiar.campaign.player.gui.centerViews.explorer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.documents.BeanDirectory;
import net.alteiar.shared.UniqueID;

public class RemoveDirectoryAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final BeanDirectory dir;

	public RemoveDirectoryAction(BeanDirectory dir) {
		this.dir = dir;

		putValue(NAME, "Supprimer le dossier");

		if (dir.isAllowedToApplyChange(CampaignClient.getInstance()
				.getCurrentPlayer())) {
			setEnabled(true);
		} else {
			setEnabled(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int returnVal = JOptionPane
				.showConfirmDialog(null,
						"Êtes vous sur de vouloir supprimer le dossier et tout ce qu'il contient ?");

		if (returnVal == JOptionPane.YES_OPTION) {
			System.out.println("remove ");
			for (UniqueID bean : dir.getDocuments()) {
				CampaignClient.getInstance().removeBean(bean);
			}

			CampaignClient.getInstance().removeBean(dir);
		}
	}
}
