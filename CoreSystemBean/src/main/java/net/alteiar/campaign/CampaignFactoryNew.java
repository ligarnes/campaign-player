package net.alteiar.campaign;

import net.alteiar.beans.chat.Chat;
import net.alteiar.beans.combatTraker.CombatTraker;
import net.alteiar.beans.dice.DiceRoller;
import net.alteiar.kryo.MyKryoInit;
import net.alteiar.player.Player;
import net.alteiar.thread.ThreadPoolUtils;

import org.apache.log4j.Logger;

public class CampaignFactoryNew {

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
	}

	/**
	 * Connect the client to the server and wait for the initialization done
	 * 
	 * We have a 30sec timeout
	 * 
	 * @param serverAdress
	 * @param port
	 * @param globalDocumentPath
	 */
	public static synchronized void connectToServer(String serverAdress,
			int port, String specificDir, String globalDocumentPath,
			MyKryoInit kryoInit) {
		if (CampaignClient.INSTANCE != null) {
			return;
		}

		CampaignClient.INSTANCE = new CampaignClient(serverAdress, port,
				specificDir, globalDocumentPath, kryoInit);

		// Sysout
		// 30 sec timeout
		long timeout = 30000L;
		long begin = System.currentTimeMillis();
		long end = System.currentTimeMillis();
		while (!CampaignClient.INSTANCE.isInitialized()
				&& (end - begin) < timeout) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Logger.getLogger(CampaignFactoryNew.class).warn(
						"Problème lors de l'initialization", e);
			}
			end = System.currentTimeMillis();
		}

		if (!CampaignClient.getInstance().isInitialized()) {
			Logger.getLogger(CampaignFactoryNew.class).error(
					"Impossible de se conneter au server!");
		}
	}

	public static synchronized void loadCampaign(String serverAdress, int port,
			String specificDir, String globalDocumentPath, String campaignName,
			MyKryoInit kryoInit) {
		connectToServer(serverAdress, port, specificDir, globalDocumentPath,
				kryoInit);

		try {
			CampaignClient.getInstance().loadGame(campaignName);
		} catch (Exception e) {
			Logger.getLogger(CampaignClient.class).error(
					"Impossible de charger la campagne", e);
		}

		for (Player current : CampaignClient.getInstance().getPlayers()) {
			current.setConnected(false);
		}
	}

	public static synchronized void startNewCampaign(String serverAdress,
			int port, String specificDir, String globalDocumentPath,
			MyKryoInit kryoInit) {
		connectToServer(serverAdress, port, specificDir, globalDocumentPath,
				kryoInit);

		Chat chat = new Chat();
		DiceRoller diceRoller = new DiceRoller();
		CombatTraker traker = new CombatTraker();

		CampaignClient.INSTANCE.addBean(chat);
		CampaignClient.INSTANCE.addBean(diceRoller);
		CampaignClient.INSTANCE.addBean(traker);

	}
}
