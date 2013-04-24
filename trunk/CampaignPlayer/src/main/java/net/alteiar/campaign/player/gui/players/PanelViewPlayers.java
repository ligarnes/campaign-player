package net.alteiar.campaign.player.gui.players;

import java.util.List;

import javax.swing.JPanel;

import net.alteiar.CampaignClient;
import net.alteiar.panel.PanelList;
import net.alteiar.player.Player;

public class PanelViewPlayers extends PanelList<Player> {
	private static final long serialVersionUID = 1L;

	public PanelViewPlayers() {
		super("Joueurs");
		List<Player> players = CampaignClient.getInstance().getPlayers();
		for (Player player : players) {
			addElement(player, new PlayerPanel(player));
		}
	}

	@Override
	protected JPanel createPanelCreate() {
		return new JPanel();
	}
}
