package net.alteiar.campaign.player.gui.tools.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.alteiar.player.Player;

public class PlayerAdapter extends BasicAdapter<Player> {

	public PlayerAdapter(Player src) {
		super(src);
	}

	@Override
	public String toString() {
		return this.getObject().getName();
	}

	public static List<PlayerAdapter> getAdapters(Player[] list) {
		List<PlayerAdapter> result = new ArrayList<PlayerAdapter>();
		for (Player playerClient : list) {
			result.add(new PlayerAdapter(playerClient));
		}

		return result;
	}

	public static List<PlayerAdapter> getAdapters(Collection<Player> list) {
		List<PlayerAdapter> result = new ArrayList<PlayerAdapter>();
		for (Player playerClient : list) {
			result.add(new PlayerAdapter(playerClient));
		}

		return result;
	}
}
