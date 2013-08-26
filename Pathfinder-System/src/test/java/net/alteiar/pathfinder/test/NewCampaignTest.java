package net.alteiar.pathfinder.test;

import java.awt.Color;
import java.io.File;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.CampaignFactory;

import org.junit.After;
import org.junit.Before;

public class NewCampaignTest extends BasicTest {

	@Before
	public void beforeTest() {
		System.out.println("Setting up test");
		String address = "127.0.0.1";
		String port = "1099";

		String localDirectoryPath = getCampaignDirectory();

		deleteRecursive(new File(localDirectoryPath));

		CampaignFactory.startNewCampaignServer(address, port,
				getGlobalDirectory(), localDirectoryPath);

		CampaignClient.getInstance().createPlayer(getPlayerName(), true,
				Color.BLUE);
	}

	@After
	public void afterTest() {
		System.out.println("tearing down");
		sleep(100);
		try {
			CampaignClient.getInstance().saveGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CampaignFactory.leaveGame();
	}

}
