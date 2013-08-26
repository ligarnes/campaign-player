package net.alteiar.campaign.player.gui.players;

import java.util.List;

import javax.swing.JPanel;

import net.alteiar.campaign.CampaignAdapter;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.panel.PanelList;
import net.alteiar.player.Player;

public class PanelViewPlayers extends PanelList<Player> {
	private static final long serialVersionUID = 1L;

	public PanelViewPlayers() {
		super("Joueurs");
		List<Player> players = CampaignClient.getInstance().getPlayers();
		for (Player player : players) {
			addElement(player, new PanelPlayer(player));
		}

		CampaignClient.getInstance().addCampaignListener(new CampaignAdapter() {
			@Override
			public void playerAdded(Player player) {
				addElement(player, new PanelPlayer(player));
			}
		});
	}

	@Override
	protected JPanel createPanelCreate() {
		return new JPanel();
	}
}
