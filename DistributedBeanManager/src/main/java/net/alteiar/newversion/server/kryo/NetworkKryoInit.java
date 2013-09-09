package net.alteiar.newversion.server.kryo;

import net.alteiar.newversion.shared.message.MessageCreateValue;
import net.alteiar.newversion.shared.message.MessageModifyValue;
import net.alteiar.newversion.shared.message.MessageSplitEnd;
import net.alteiar.newversion.shared.message.MessageSplitReady;
import net.alteiar.newversion.shared.reply.ReplyCampaignServer;
import net.alteiar.newversion.shared.reply.ReplyIdsClient;
import net.alteiar.newversion.shared.request.RequestCampaignClient;
import net.alteiar.newversion.shared.request.RequestDelete;
import net.alteiar.newversion.shared.request.RequestIdsServer;
import net.alteiar.newversion.shared.request.RequestObject;
import net.alteiar.shared.UniqueID;

import com.esotericsoftware.kryo.Kryo;

public class NetworkKryoInit extends KryoInit {

	@Override
	public void registerKryo(Kryo kryo) {
		super.registerKryo(kryo);

		kryo.register(MessageModifyValue.class);
		kryo.register(MessageCreateValue.class);

		kryo.register(MessageSplitReady.class);
		kryo.register(MessageSplitEnd.class);
		kryo.register(RequestObject.class);

		kryo.register(RequestDelete.class);
		kryo.register(RequestCampaignClient.class);

		kryo.register(UniqueID.class);
		kryo.register(UniqueID[].class);
		kryo.register(byte[].class);

		kryo.register(ReplyCampaignServer.class);
		kryo.register(ReplyIdsClient.class);
		kryo.register(RequestIdsServer.class);
	}

}
