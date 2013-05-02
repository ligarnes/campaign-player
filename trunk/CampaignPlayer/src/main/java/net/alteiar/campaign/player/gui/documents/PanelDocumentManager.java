package net.alteiar.campaign.player.gui.documents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.alteiar.CampaignClient;
import net.alteiar.CampaignListener;
import net.alteiar.campaign.player.Helpers;
import net.alteiar.documents.AuthorizationAdapter;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.documents.character.Character;
import net.alteiar.documents.map.MapBean;
import net.alteiar.panel.PanelList;

public class PanelDocumentManager extends PanelList<AuthorizationBean>
		implements CampaignListener {
	private static final long serialVersionUID = 1L;

	private static final String ICON_SAVE = "save.png";

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
		JButton btnAdd = new JButton("Ajouter");
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelCreateDocument.createDocument();
			}
		});

		JButton btnSave = new JButton(Helpers.getIcon(ICON_SAVE, 24, 24));
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CampaignClient.getInstance().saveGame();
				JOptionPane.showMessageDialog(null,
						"La campagne à bien \u00E9t\u00E9 sauvegard\u00E9",
						"Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		pane.add(btnSave);
		pane.add(btnAdd);
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
	public void battleAdded(MapBean battle) {
	}

	@Override
	public void battleRemoved(MapBean battle) {
	}

	@Override
	public void characterAdded(Character character) {
	}

	@Override
	public void characterRemoved(Character character) {
	}
}
