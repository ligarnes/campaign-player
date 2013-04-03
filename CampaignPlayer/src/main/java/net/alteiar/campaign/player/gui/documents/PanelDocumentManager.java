package net.alteiar.campaign.player.gui.documents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.alteiar.CampaignClient;
import net.alteiar.CampaignListener;
import net.alteiar.documents.AuthorizationAdapter;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.documents.character.CharacterBean;
import net.alteiar.documents.map.battle.Battle;
import net.alteiar.panel.PanelList;

public class PanelDocumentManager extends PanelList<AuthorizationBean>
		implements CampaignListener {
	private static final long serialVersionUID = 1L;

	private final AuthorizationChangeListener beanListener;

	public PanelDocumentManager() {
		super("Documents");

		beanListener = new AuthorizationChangeListener();
		CampaignClient.getInstance().addCampaignListener(this);

		for (AuthorizationBean bean : CampaignClient.getInstance()
				.getDocuments()) {
			beanAdded(bean);
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

	private class AuthorizationChangeListener extends AuthorizationAdapter {
		@Override
		public void authorizationChanged(PropertyChangeEvent evt) {
			AuthorizationBean bean = (AuthorizationBean) evt.getSource();
			if (bean.isAllowedToSee(CampaignClient.getInstance()
					.getCurrentPlayer())) {
				addElement(bean, new PanelDocumentDescription(bean));
			} else {
				removeElement(bean);
			}
		}
	}

	// //// LISTENERS ////
	@Override
	public void beanAdded(AuthorizationBean bean) {
		bean.addPropertyChangeListener(beanListener);
		if (bean.isAllowedToSee(CampaignClient.getInstance().getCurrentPlayer())) {
			addElement(bean, new PanelDocumentDescription(bean));
		}
	}

	@Override
	public void beanRemoved(AuthorizationBean bean) {
		bean.removePropertyChangeListener(beanListener);
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
