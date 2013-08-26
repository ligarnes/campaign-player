package net.alteiar.campaign;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import net.alteiar.chat.Chat;
import net.alteiar.client.DocumentManager;
import net.alteiar.combatTraker.CombatTraker;
import net.alteiar.dice.DiceRoller;
import net.alteiar.player.Player;
import net.alteiar.server.ServerDocuments;
import net.alteiar.thread.ThreadPoolUtils;

import org.apache.log4j.Logger;

public class CampaignFactory {

	public static synchronized void loadCampaignServer(String serverAdress,
			String port, String globalDocumentPath, String campaignPath) {
		CampaignFactory.startServer(serverAdress, port, campaignPath);
		CampaignFactory.connectToServer(serverAdress, serverAdress, port,
				globalDocumentPath);

		try {
			CampaignClient.getInstance().loadGame(campaignPath);
		} catch (Exception e) {
			Logger.getLogger(CampaignClient.class).error(
					"Impossible de charger la campagne", e);
		}

		for (Player current : CampaignClient.getInstance().getPlayers()) {
			current.setConnected(false);
		}
	}

	public static synchronized void leaveGame() {
		CampaignClient.INSTANCE.disconnect();

		// wait max 3sec
		long maxDisconnectedTime = 3000L;
		long begin = System.currentTimeMillis();
		long time = System.currentTimeMillis() - begin;
		while (CampaignClient.INSTANCE.getCurrentPlayer().getConnected()
				&& time < maxDisconnectedTime) {

			// Wait for disconnection received
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Logger.getLogger(CampaignClient.class).warn(
						"Exception during shutdown", e);
			}
			time = System.currentTimeMillis() - begin;
		}

		CampaignClient.INSTANCE = null;
		ThreadPoolUtils.getClientPool().shutdown();
		if (CampaignClient.IS_SERVER) {
			ServerDocuments.stopServer();
		}
	}

	private static synchronized ServerDocuments startServer(String addressIp,
			String port, String path) {
		ServerDocuments server = null;
		try {
			server = ServerDocuments.startServer(addressIp, port, path);
			CampaignClient.IS_SERVER = true;
		} catch (RemoteException e) {
			Logger.getLogger(CampaignClient.class).error(
					"Remote exception when start server", e);
		} catch (MalformedURLException e) {
			Logger.getLogger(CampaignClient.class).error(
					"MalformedURLException exception when start server", e);
		} catch (NotBoundException e) {
			Logger.getLogger(CampaignClient.class).error(
					"NotBoundException exception when start server", e);
		}
		return server;
	}

	public static synchronized void connectToServer(String localAdress,
			String serverAdress, String port, String globalDocumentPath) {

		if (CampaignClient.INSTANCE != null) {
			return;
		}

		DocumentManager manager = null;
		try {
			manager = DocumentManager.connect(localAdress, serverAdress, port,
					globalDocumentPath);
			CampaignClient.INSTANCE = new CampaignClient(manager, localAdress,
					serverAdress, port);
		} catch (RemoteException e) {
			Logger.getLogger(CampaignClient.class)
					.error("Aucune partie n'a \u00E9t\u00E9 trouv\u00E9e sur le serveur s\u00E9lectionn\u00E9.",
							e);
		} catch (MalformedURLException e) {
			Logger.getLogger(CampaignClient.class).error(
					"Malformed Url when connect to server", e);
		} catch (NotBoundException e) {
			Logger.getLogger(CampaignClient.class).error(
					"Not Bound exception when connect to server", e);
		}
	}

	public static synchronized void startNewCampaignServer(String serverAdress,
			String port, String globalDocumentPath, String campaignPath) {

		ServerDocuments server = startServer(serverAdress, port, campaignPath);
		Chat chat = new Chat();
		DiceRoller diceRoller = new DiceRoller();
		CombatTraker traker = new CombatTraker();
		try {
			server.createDocument(chat);
			server.createDocument(diceRoller);
			server.createDocument(traker);
		} catch (RemoteException e) {
			Logger.getLogger(CampaignClient.class).error(
					"start new campaign server, enable to join server", e);
		}
		connectToServer(serverAdress, serverAdress, port, globalDocumentPath);
	}

}
