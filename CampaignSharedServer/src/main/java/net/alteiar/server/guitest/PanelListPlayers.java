package net.alteiar.server.guitest;

import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JLabel;

import net.alteiar.server.shared.campaign.ServerCampaign;
import net.alteiar.server.shared.campaign.player.IPlayerRemote;

public class PanelListPlayers extends PanelSimpleListInfo {
	private static final long serialVersionUID = 1L;

	public PanelListPlayers() {
		super("Joueurs");
	}

	@Override
	protected void askRefresh() throws RemoteException {
		List<IPlayerRemote> characters = ServerCampaign.SERVER_CAMPAIGN_REMOTE
				.getAllPlayer();

		this.getPanelData().removeAll();
		for (IPlayerRemote iCharacterRemote : characters) {
			JLabel lbl = new JLabel(iCharacterRemote.getName());

			this.getPanelData().add(lbl);
		}
		this.revalidate();
		this.repaint();

	}
}
