package net.alteiar;

import net.alteiar.documents.BeanDocument;
import net.alteiar.player.Player;

public interface CampaignListener {
	void playerAdded(Player player);

	void beanAdded(BeanDocument bean);

	void beanRemoved(BeanDocument bean);
}
