package net.alteiar.factory;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.sharedDocuments.SharedDocumentBasicBeans;

public class BeanFactory {

	public static void createBean(String documentName,
			SharedDocumentBasicBeans bean) {
		Long playerId = CampaignClient.getInstance().getCurrentPlayer().getId();
		bean.addOwner(playerId);
		bean.setDocumentName(documentName);

		CampaignClient.getInstance().addBean(bean);
	}

	public static void createBean(SharedDocumentBasicBeans bean) {
		Long playerId = CampaignClient.getInstance().getCurrentPlayer().getId();
		bean.addOwner(playerId);
		bean.setDocumentName(bean.getId().toString());

		CampaignClient.getInstance().addBean(bean);
	}

	public static void createBean(BasicBeans bean) {
		CampaignClient.getInstance().addBean(bean);
	}
}
