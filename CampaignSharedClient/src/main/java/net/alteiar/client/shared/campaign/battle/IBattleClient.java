package net.alteiar.client.shared.campaign.battle;

import java.awt.Point;

import net.alteiar.client.shared.campaign.battle.character.ICharacterCombatClient;
import net.alteiar.client.shared.campaign.character.ICharacterSheetClient;
import net.alteiar.client.shared.campaign.map.IMap2DClient;
import net.alteiar.client.shared.observer.campaign.battle.IBattleObserver;
import net.alteiar.server.shared.campaign.battle.IBattleRemote;

public interface IBattleClient extends IMap2DClient<IBattleRemote> {
	void addBattleListener(IBattleObserver listener);

	void removeBattleListener(IBattleObserver listener);

	void addCharacter(ICharacterSheetClient character, Integer init,
			Point position);

	void addMonster(ICharacterSheetClient character, Integer init,
			Boolean isVisible, Point position);

	void removeCharacter(ICharacterCombatClient character);

	ICharacterCombatClient getCharacterCombat(Long guid);

	ICharacterCombatClient[] getAllCharacter();

	Integer getCurrentTurn();

	void nextTurn();

	ICharacterCombatClient getCharacterAt(Point location);
}
