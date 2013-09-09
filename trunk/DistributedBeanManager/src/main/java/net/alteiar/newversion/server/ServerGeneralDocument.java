package net.alteiar.newversion.server;

import java.io.IOException;
import java.util.Arrays;

import net.alteiar.newversion.server.kryo.NetworkKryoInit;
import net.alteiar.newversion.shared.reply.ReplyCampaignServer;
import net.alteiar.newversion.shared.reply.ReplyIdsClient;
import net.alteiar.newversion.shared.request.RequestCampaignClient;
import net.alteiar.newversion.shared.request.RequestDelete;
import net.alteiar.newversion.shared.request.RequestIdsServer;
import net.alteiar.shared.UniqueID;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerGeneralDocument extends Listener {
	private final ServerDocuments manager;
	private final Server server;

	public ServerGeneralDocument(ServerDocuments manager, int portTCP)
			throws IOException {
		this.manager = manager;

		// int buffere50mo = 52428800;
		int buffer1mo = 1024000;
		int bufferSize = buffer1mo;
		server = new Server(bufferSize, bufferSize);
		server.start();
		server.bind(portTCP, portTCP + 1);

		NetworkKryoInit init = new NetworkKryoInit();
		init.registerKryo(server.getKryo());

		server.addListener(this);
	}

	public void stop() {
		server.close();
		server.stop();
	}

	@Override
	public void connected(Connection conn) {
		conn.updateReturnTripTime();
	}

	@Override
	public void received(Connection connection, Object object) {
		if (object instanceof RequestDelete) {
			server.sendToAllTCP(object);
			// this.manager.documentClosed((RequestDelete) object);
		} else if (object instanceof RequestCampaignClient) {
			System.out.println("Request campaign: "
					+ server.getConnections().length);
			boolean otherClient = server.getConnections().length > 1;

			if (otherClient) {
				requestReceived(connection, (RequestCampaignClient) object);
			} else {
				replyReceived(connection, null);
			}
		} else if (object instanceof ReplyIdsClient) {
			replyReceived(connection, ((ReplyIdsClient) object).getIds());
		}
	}

	private void requestReceived(Connection conn, RequestCampaignClient request) {
		System.out.println("request received");

		Connection dest = null;

		int min = Integer.MAX_VALUE;
		for (Connection destConn : server.getConnections()) {
			if (conn.getID() != destConn.getID()
					&& destConn.getReturnTripTime() < min) {
				min = destConn.getReturnTripTime();
				dest = destConn;
			}
		}

		dest.sendTCP(new RequestIdsServer());
	}

	private void replyReceived(Connection conn, UniqueID[] ids) {
		System.out.println("request reply " + Arrays.toString(ids));
		server.sendToAllTCP(new ReplyCampaignServer(manager.getSpecificPath(),
				ids));
	}

}
