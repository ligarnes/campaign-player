package net.alteiar.server.guitest;

import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JLabel;

import net.alteiar.server.shared.campaign.ServerCampaign;
import net.alteiar.server.shared.campaign.battle.IBattleRemote;

public class PanelListBattles extends PanelSimpleListInfo {
	private static final long serialVersionUID = 1L;

	public PanelListBattles() {
		super("Combats");
	}

	@Override
	protected void askRefresh() throws RemoteException {
		List<IBattleRemote> characters = ServerCampaign.SERVER_CAMPAIGN_REMOTE
				.getBattles();

		this.getPanelData().removeAll();
		for (IBattleRemote iCharacterRemote : characters) {
			JLabel lbl = new JLabel(iCharacterRemote.getName());

			this.getPanelData().add(lbl);
		}
		this.revalidate();
		this.repaint();

	}
}
