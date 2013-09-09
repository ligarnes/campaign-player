package net.alteiar.newversion.shared.reply;

import net.alteiar.shared.UniqueID;

public class ReplyIdsClient {
	private UniqueID[] ids;

	public ReplyIdsClient() {

	}

	public ReplyIdsClient(UniqueID[] ids) {
		this.ids = ids;
	}

	public UniqueID[] getIds() {
		return ids;
	}
}
