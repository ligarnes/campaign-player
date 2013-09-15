package net.alteiar.test;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.CampaignFactoryNew;
import net.alteiar.kryo.MyKryoInit;
import net.alteiar.newversion.server.ServerDocuments;

import org.junit.After;
import org.junit.Before;

public class NewCampaignNoGMTest extends BasicTest {
	private ServerDocuments server;

	@Before
	public void beforeTest() {
		// Start a server
		try {
			server = new ServerDocuments(4545, "abc");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Setting up test");
		String address = "127.0.0.1";
		int port = 4545;

		String localDirectoryPath = getCampaignDirectory();

		deleteRecursive(new File(localDirectoryPath));

		CampaignFactoryNew.startNewCampaign(address, port, "./save",
				getGlobalDirectory(), new MyKryoInit());

		CampaignClient.getInstance().createPlayer(getPlayerName(), false,
				Color.BLUE);

		while (!CampaignClient.getInstance().isInitialized()) {
			System.out.println("wait for initialization");
		}
	}

	@After
	public void afterTest() {
		CampaignFactoryNew.leaveGame();

		server.stopServer();
		System.out.println("tearing down");
	}

}
