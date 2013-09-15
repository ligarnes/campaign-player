package net.alteiar.newversion.client;

import java.io.IOException;

import net.alteiar.newversion.server.kryo.NetworkKryoInit;
import net.alteiar.newversion.shared.reply.ReplyCampaignServer;
import net.alteiar.newversion.shared.reply.ReplyIdsClient;
import net.alteiar.newversion.shared.request.RequestCampaignClient;
import net.alteiar.newversion.shared.request.RequestDelete;
import net.alteiar.newversion.shared.request.RequestIdsServer;
import net.alteiar.shared.UniqueID;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientGeneralDocument extends Listener {
	private final DocumentManager manager;
	private final Client client;

	public ClientGeneralDocument(DocumentManager manager, String ipServeur,
			int portTCP) throws IOException {
		this.manager = manager;

		// int buffere50mo = 52428800;
		int buffer1mo = 1024000;
		int bufferSize = buffer1mo;
		client = new Client(bufferSize, bufferSize);
		client.start();
		client.connect(10000, ipServeur, portTCP, portTCP + 1);

		NetworkKryoInit init = new NetworkKryoInit();
		init.registerKryo(client.getKryo());
		client.addListener(this);
	}

	public void stop() {
		client.close();
		client.stop();
	}

	public void askInitCampaign() {
		client.sendTCP(new RequestCampaignClient());
	}

	public void closeDocument(UniqueID guid) {
		client.sendTCP(new RequestDelete(guid));
	}

	@Override
	public void received(Connection connection, Object object) {
		if (object instanceof RequestDelete) {
			documentClosed((RequestDelete) object);
		} else if (object instanceof RequestIdsServer) {
			client.sendTCP(new ReplyIdsClient(this.manager.getIds()));
		} else if (object instanceof ReplyCampaignServer) {
			if (!manager.isInitialized()) {
				initCampaign((ReplyCampaignServer) object);
			}
		}
	}

	private void initCampaign(ReplyCampaignServer campaign) {
		String campaignName = campaign.getCampaignName();
		UniqueID[] ids = campaign.getIds();

		this.manager.initCampaign(campaignName, ids);
	}

	private void documentClosed(RequestDelete object) {
		this.manager.documentClosed(object.getBeanId());
	}
}
