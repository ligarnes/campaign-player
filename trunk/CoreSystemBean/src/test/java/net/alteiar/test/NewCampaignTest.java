package net.alteiar.test;

import java.awt.Color;
import java.io.File;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.CampaignFactoryNew;
import net.alteiar.kryo.MyKryoInit;
import net.alteiar.newversion.server.ServerDocuments;

import org.junit.After;
import org.junit.Before;

public class NewCampaignTest extends BasicTest {

	private ServerDocuments server;

	@Before
	public void beforeTest() {
		// Start a server
		/*
		 * try { server = new ServerDocuments(4545, "abc"); } catch (IOException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */

		System.out.println("Setting up test");
		String address = "127.0.0.1";
		int port = 4545;

		String localDirectoryPath = getCampaignDirectory();

		deleteRecursive(new File(localDirectoryPath));

		CampaignFactoryNew.startNewCampaign(address, port, "./save",
				getGlobalDirectory(), new MyKryoInit());

		CampaignClient.getInstance().createPlayer(getPlayerName(), true,
				Color.BLUE);

		System.out.println("test started");
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
		CampaignFactoryNew.leaveGame();

		System.out.println("tearing down");
	}

}
