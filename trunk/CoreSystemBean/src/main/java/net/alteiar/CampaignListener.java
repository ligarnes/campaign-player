package net.alteiar;

import net.alteiar.documents.AuthorizationBean;
import net.alteiar.documents.character.Character;
import net.alteiar.documents.map.MapBean;
import net.alteiar.player.Player;

public interface CampaignListener {
	void battleAdded(MapBean battle);

	void battleRemoved(MapBean battle);

	void characterAdded(Character character);

	void characterRemoved(Character character);

	void playerAdded(Player player);

	void beanAdded(AuthorizationBean bean);

	void beanRemoved(AuthorizationBean bean);
}
