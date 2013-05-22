package net.alteiar;

import net.alteiar.documents.AuthorizationBean;
import net.alteiar.documents.character.Character;
import net.alteiar.documents.map.MapBean;
import net.alteiar.player.Player;

public class CampaignAdapter implements CampaignListener {
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

	@Override
	public void beanAdded(AuthorizationBean bean) {
	}

	@Override
	public void beanRemoved(AuthorizationBean bean) {
	}

	@Override
	public void playerAdded(Player player) {
	}
}
