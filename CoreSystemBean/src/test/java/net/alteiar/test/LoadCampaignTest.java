package net.alteiar.test;

import java.util.List;

import net.alteiar.CampaignClient;
import net.alteiar.player.Player;

import org.junit.After;
import org.junit.Before;

public class LoadCampaignTest extends BasicTest {
	private String originalName;

	@Before
	public void beforeTest() {
		System.out.println("Setting up test");
		String address = "127.0.0.1";
		String port = "1099";

		CampaignClient
				.loadCampaignServer(address, port, getCampaignDirectory());

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CampaignClient.getInstance().saveGame();
		CampaignClient.leaveGame();
	}

	@Override
	public String getPlayerName() {
		return originalName;
	}

}
