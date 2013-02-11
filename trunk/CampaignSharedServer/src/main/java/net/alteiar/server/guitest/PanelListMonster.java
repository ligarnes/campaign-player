package net.alteiar.server.guitest;

import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JLabel;

import net.alteiar.server.shared.campaign.ServerCampaign;
import net.alteiar.server.shared.campaign.character.ICharacterRemote;

public class PanelListMonster extends PanelSimpleListInfo {
	private static final long serialVersionUID = 1L;

	public PanelListMonster() {
		super("Monstres");
	}

	@Override
	protected void askRefresh() throws RemoteException {
		List<ICharacterRemote> characters = ServerCampaign.SERVER_CAMPAIGN_REMOTE
				.getAllMonsters();

		this.getPanelData().removeAll();
		for (ICharacterRemote iCharacterRemote : characters) {
			JLabel lbl = new JLabel(iCharacterRemote.getCharacterFacade()
					.getName());

			this.getPanelData().add(lbl);
		}
		this.revalidate();
		this.repaint();

	}
}
