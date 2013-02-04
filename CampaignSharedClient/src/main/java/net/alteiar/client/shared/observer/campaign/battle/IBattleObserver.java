package net.alteiar.client.shared.observer.campaign.battle;

import net.alteiar.client.shared.campaign.battle.BattleClient;
import net.alteiar.client.shared.campaign.battle.character.ICharacterCombatClient;

public interface IBattleObserver {

	void characterAdded(BattleClient battle, ICharacterCombatClient character);

	void characterRemove(BattleClient battle, ICharacterCombatClient character);

	void turnChanged(BattleClient battle);

}
