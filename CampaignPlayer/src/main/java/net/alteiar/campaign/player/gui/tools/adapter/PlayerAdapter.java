package net.alteiar.campaign.player.gui.tools.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.alteiar.client.shared.campaign.player.IPlayerClient;

public class PlayerAdapter extends BasicAdapter<IPlayerClient> {

	public PlayerAdapter(IPlayerClient src) {
		super(src);
	}

	@Override
	public String toString() {
		return this.getObject().getName();
	}

	public static List<PlayerAdapter> getAdapters(Collection<IPlayerClient> list) {
		List<PlayerAdapter> result = new ArrayList<PlayerAdapter>();
		for (IPlayerClient playerClient : list) {
			result.add(new PlayerAdapter(playerClient));
		}

		return result;
	}
}
