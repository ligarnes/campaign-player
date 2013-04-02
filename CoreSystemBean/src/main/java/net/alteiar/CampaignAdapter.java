package net.alteiar;

import net.alteiar.documents.AuthorizationBean;
import net.alteiar.documents.character.CharacterBean;
import net.alteiar.documents.map.battle.Battle;

public class CampaignAdapter implements CampaignListener {
	@Override
	public void battleAdded(Battle battle) {
	}

	@Override
	public void battleRemoved(Battle battle) {
	}

	@Override
	public void characterAdded(CharacterBean character) {
	}

	@Override
	public void characterRemoved(CharacterBean character) {
	}

	@Override
	public void beanAdded(AuthorizationBean bean) {
	}

	@Override
	public void beanRemoved(AuthorizationBean bean) {
	}
}
