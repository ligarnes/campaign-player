package net.alteiar.chat.message;

public class MjSender implements ChatObject {

	private final String command;
	private final String message;

	public MjSender(String command, String message) {
		this.command = command;
		this.message = message;
	}

	public MjSender(String message) {
		String[] vals = message.split(";");
		this.command = vals[0];

		StringBuilder builder = new StringBuilder();
		for (int i = 1; i < vals.length; ++i) {
			builder.append(vals[i] + ";");
		}
		this.message = builder.toString();
	}

	@Override
	public String stringFormat() {
		return command + ";" + message;
	}

	public String getCommand() {
		return this.command;
	}

	/*
	 * public Boolean canAccess() { return
	 * CampaignClient.getInstance().getCurrentPlayer().isMj(); }
	 */

	public String getMessage() {
		return this.message;
	}
}
