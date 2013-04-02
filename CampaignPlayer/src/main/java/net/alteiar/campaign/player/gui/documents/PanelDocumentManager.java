package net.alteiar.campaign.player.gui.documents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.alteiar.CampaignClient;
import net.alteiar.CampaignListener;
import net.alteiar.campaign.player.gui.dashboard.PanelList;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.documents.character.CharacterBean;
import net.alteiar.documents.map.battle.Battle;

public class PanelDocumentManager extends PanelList implements CampaignListener {
	private static final long serialVersionUID = 1L;

	public PanelDocumentManager() {
		super("Documents");

		CampaignClient.getInstance().addCampaignListener(this);

		for (AuthorizationBean bean : CampaignClient.getInstance()
				.getDocuments()) {
			addElement(bean, new PanelDocumentDescription(bean));
		}
	}

	@Override
	protected JPanel createPanelCreate() {
		JPanel pane = new JPanel();
		JButton button = new JButton("Ajouter");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelCreateDocument.createDocument();
			}
		});
		pane.add(button);
		return pane;
	}

	// //// LISTENERS ////
	@Override
	public void beanAdded(AuthorizationBean bean) {
		addElement(bean, new PanelDocumentDescription(bean));
	}

	@Override
	public void beanRemoved(AuthorizationBean bean) {
		removeElement(bean);
	}

	@Override
	public void battleAdded(Battle battle) {
	}

	@Override
	public void battleRemoved(Battle battle) {
	}

	@Override
	public void characterAdded(CharacterBean character) {
	}

	@Override
	public void characterRemoved(CharacterBean character) {
	}
}
