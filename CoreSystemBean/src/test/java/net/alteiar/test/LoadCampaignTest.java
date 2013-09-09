package net.alteiar.test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.CampaignFactoryNew;
import net.alteiar.newversion.server.ServerDocuments;
import net.alteiar.player.Player;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

public class LoadCampaignTest extends BasicTest {
	private String originalName;
	private ServerDocuments server;

	@Before
	public void beforeTest() throws TimeoutException {
		// Start a server
		try {
			server = new ServerDocuments(4545, "abc");
		} catch (IOException e) {
			Logger.getLogger(CampaignClient.class).error(
					"fail to start server", e);
		}

		System.out.println("Setting up test");
		String address = "127.0.0.1";
		int port = 4545;

		CampaignFactoryNew.loadCampaign(address, port, getGlobalDirectory(),
				getCampaignDirectory());

		long before = System.currentTimeMillis();
		int currentSize = CampaignClient.getInstance().getPlayers().size();
		while (currentSize == 0) {
			currentSize = CampaignClient.getInstance().getPlayers().size();

			long end = System.currentTimeMillis();
			if ((end - before) == 3000L) {
				throw new TimeoutException("take to much time");
			}
		}
		List<Player> players = CampaignClient.getInstance().getPlayers();
		CampaignClient.getInstance().selectPlayer(players.get(0));
		originalName = players.get(0).getName();
	}

	@After
	public void afterTest() {
		System.out.println("tearing down");
		try {
			// finalyze all actions
			Thread.sleep(100);
		} catch (InterruptedException e) {
			Logger.getLogger(CampaignClient.class).error("fail to sleep", e);
		}

		try {
			CampaignClient.getInstance().saveGame();
		} catch (Exception e) {
			Logger.getLogger(CampaignClient.class).error("Fail to save", e);
		}
		CampaignFactoryNew.leaveGame();

		server.stopServer();
	}

	@Override
	public String getPlayerName() {
		return originalName;
	}

}
