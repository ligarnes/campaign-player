package net.alteiar.client.shared.campaign.chat;

import net.alteiar.client.shared.campaign.CampaignClient;

public class PrivateSender implements ChatObject {

	private final String to;
	private final String message;

	public PrivateSender(String to, String message) {
		this.to = to;
		this.message = message;
	}

	public PrivateSender(String msg) {
		String[] vals = msg.split(";");
		to = vals[0];
		message = vals[1];
	}

	@Override
	public String stringFormat() {
		StringBuilder builder = new StringBuilder();
		builder.append(to);
		builder.append(";");
		builder.append(message);

		return builder.toString();
	}

	public Boolean canAccess() {
		String name = CampaignClient.INSTANCE.getCurrentPlayer().getName();
		return to.equalsIgnoreCase(name);
	}

	public String getMessage() {
		return this.message;
	}
}