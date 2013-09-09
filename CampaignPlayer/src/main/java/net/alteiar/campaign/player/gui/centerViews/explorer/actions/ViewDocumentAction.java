package net.alteiar.campaign.player.gui.centerViews.explorer.actions;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

import net.alteiar.campaign.player.gui.MainFrame;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.campaign.player.plugin.PluginSystem;
import net.alteiar.documents.BeanDocument;

public class ViewDocumentAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final BeanDocument file;

	public ViewDocumentAction(BeanDocument file) {
		this.file = file;

		putValue(NAME, "Voir document");
		// putValue(LARGE_ICON_KEY, Helpers.getIcon(ICON_SHOW_GRID));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		PanelViewDocument panelView = PluginSystem.getInstance().getViewPanel(
				file);

		if (panelView != null) {
			JDialog dlg = new JDialog(MainFrame.FRAME, file.getDocumentName(),
					false);
			dlg.add(panelView);
			dlg.setPreferredSize(new Dimension(800, 600));
			dlg.pack();
			dlg.setLocationRelativeTo(null);
			dlg.setVisible(true);
		}
	}

}
