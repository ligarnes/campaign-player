package net.alteiar.client.shared.observer.campaign.battle.character;

import net.alteiar.client.shared.campaign.battle.character.ICharacterCombatClient;

public interface ICharacterCombatObserver {

	void characterChange(ICharacterCombatClient character);

	void highLightChange(ICharacterCombatClient character, Boolean isHighlighted);

	void visibilityChange(ICharacterCombatClient character);

	void initiativeChange(ICharacterCombatClient character);

	void positionChanged(ICharacterCombatClient character);

	void rotationChanged(ICharacterCombatClient character);
}
