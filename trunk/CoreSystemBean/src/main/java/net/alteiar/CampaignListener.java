package net.alteiar;

import net.alteiar.documents.AuthorizationBean;
import net.alteiar.documents.character.CharacterBean;
import net.alteiar.documents.map.MapBean;

public interface CampaignListener {
	void battleAdded(MapBean battle);

	void battleRemoved(MapBean battle);

	void characterAdded(CharacterBean character);

	void characterRemoved(CharacterBean character);

	void beanAdded(AuthorizationBean bean);

	void beanRemoved(AuthorizationBean bean);
}
