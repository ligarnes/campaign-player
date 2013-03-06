package net.alteiar.campaign.player.gui.tools.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.alteiar.server.document.player.PlayerClient;

public class PlayerAdapter extends BasicAdapter<PlayerClient> {

	public PlayerAdapter(PlayerClient src) {
		super(src);
	}

	@Override
	public String toString() {
		return this.getObject().getName();
	}

	public static List<PlayerAdapter> getAdapters(PlayerClient[] list) {
		List<PlayerAdapter> result = new ArrayList<PlayerAdapter>();
		for (PlayerClient playerClient : list) {
			result.add(new PlayerAdapter(playerClient));
		}

		return result;
	}

	public static List<PlayerAdapter> getAdapters(Collection<PlayerClient> list) {
		List<PlayerAdapter> result = new ArrayList<PlayerAdapter>();
		for (PlayerClient playerClient : list) {
			result.add(new PlayerAdapter(playerClient));
		}

		return result;
	}
}
