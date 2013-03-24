package net.alteiar.chat.message;

import net.alteiar.CampaignClient;

public class MjSender extends PrivateSender {

	public MjSender(String expediteur, String message) {
		super(expediteur, message);
	}

	public MjSender(String msg) {
		super(msg);
	}

	@Override
	public Boolean canAccess() {
		return super.canAccess()
				|| CampaignClient.getInstance().getCurrentPlayer().isMj();
	}
}
