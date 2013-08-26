package net.alteiar.campaign;

import net.alteiar.documents.BeanBasicDocument;
import net.alteiar.player.Player;

public interface CampaignListener {
	void playerAdded(Player player);

	void beanAdded(BeanBasicDocument bean);

	void beanRemoved(BeanBasicDocument bean);
}
