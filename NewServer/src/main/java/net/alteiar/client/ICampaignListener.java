package net.alteiar.client;

import net.alteiar.server.document.map.battle.BattleClient;

public interface ICampaignListener {
	void battleAdded(BattleClient battle);

	void battleRemoved(BattleClient battle);
}
