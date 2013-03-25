package net.alteiar;

import net.alteiar.character.CharacterBean;
import net.alteiar.map.battle.Battle;

public interface CampaignListener {
	void battleAdded(Battle battle);

	void battleRemoved(Battle battle);

	void characterAdded(CharacterBean character);

	void characterRemoved(CharacterBean character);
}
