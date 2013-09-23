package shadowrun.actions;

import java.awt.Dimension;

import javax.swing.JDialog;

import net.alteiar.beans.map.elements.IAction;
import net.alteiar.campaign.player.gui.MainFrame;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.campaign.player.plugin.PluginSystem;
import net.alteiar.documents.BeanDocument;

public class ShowCharacter extends IAction {

	private final BeanDocument character;

	public ShowCharacter(BeanDocument character) {
		this.character = character;
	}

	@Override
	public String getName() {
		return "Voir le personnage";
	}

	@Override
	public Boolean canDoAction() {
		return true;
	}

	@Override
	public void doAction(int xOnScreen, int yOnScreen) throws Exception {
		PanelViewDocument panelView = PluginSystem.getInstance().getViewPanel(
				character);

		if (panelView != null) {
			JDialog dlg = new JDialog(MainFrame.FRAME,
					character.getDocumentName(), false);
			dlg.add(panelView);
			dlg.setPreferredSize(new Dimension(800, 600));
			dlg.pack();
			dlg.setLocationRelativeTo(null);
			dlg.setVisible(true);
		}
	}

}
