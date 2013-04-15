package net.alteiar.chat.message;

import net.alteiar.CampaignClient;

import org.simpleframework.xml.Element;

public class PrivateSender implements ChatObject {

	@Element
	private String to;
	@Element
	private String message;

	public PrivateSender(String to, String message) {
		this.to = to;
		this.message = message;
	}

	public PrivateSender(String msg) {
		String[] vals = msg.split(";");
		if (vals.length == 2) {
			to = vals[0];
			message = vals[1];
		}
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
		String name = CampaignClient.getInstance().getCurrentPlayer().getName();
		return to.equalsIgnoreCase(name);
	}

	public String getMessage() {
		return this.message;
	}
}
