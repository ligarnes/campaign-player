package net.alteiar.test.saveable;

import java.io.File;

import net.alteiar.CampaignClient;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestSave.class })
public class SaveTests {

	public static String getPlayerName() {
		return "player-name";
	}

	private static void deleteRecursive(File base) {
		if (base.listFiles() != null) {
			for (File f : base.listFiles()) {
				deleteRecursive(f);
			}
		}
		base.delete();
	}

	@BeforeClass
	public static void setUp() {
		System.out.println("Setting up test");
		String campaignName = "test-save-campaign";
		String localDirectoryPath = "./test/ressources/campaign/"
				+ campaignName;
		// First remove old save directory
		File campaignDirectory = new File(localDirectoryPath);
		deleteRecursive(campaignDirectory);

		// then setup the environnement
		String address = "127.0.0.1";
		String port = "1099";

		CampaignClient.startServer(address, port, localDirectoryPath);
		CampaignClient.connect(address, address, port, localDirectoryPath,
				getPlayerName(), true);

	}

	@AfterClass
	public static void tearDown() {
		System.out.println("tearing down");
	}
}