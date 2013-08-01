package net.alteiar.test;

import java.awt.Color;
import java.io.File;

import net.alteiar.CampaignClient;

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

		try {
			CampaignClient.startNewCampaignServer(address, port,
					getGlobalDirectory(), localDirectoryPath);

		} catch (Exception e) {
			e.printStackTrace();
		}

		CampaignClient.getInstance().createPlayer(getPlayerName(), true,
				Color.BLUE);

	}

	@After
	public void afterTest() {
		sleep();
		try {
			CampaignClient.getInstance().saveGame();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CampaignClient.leaveGame();
		System.out.println("tearing down");
	}

}
