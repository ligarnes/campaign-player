package net.alteiar.newversion.shared.reply;

import net.alteiar.shared.UniqueID;

public class ReplyCampaignServer {
	private String name;
	private UniqueID[] ids;

	public ReplyCampaignServer() {
	}

	public ReplyCampaignServer(String name, UniqueID[] ids) {
		this.name = name;
		this.ids = ids;
	}

	public String getCampaignName() {
		return name;
	}

	public UniqueID[] getIds() {
		return ids;
	}
}
