package net.alteiar.test;

import java.io.File;

import net.alteiar.CampaignClient;
import net.alteiar.test.map.TestMap;
import net.alteiar.test.map.TestMapElement;
import net.alteiar.test.map.TestNoise;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestChat.class, TestPlayer.class, TestImageUtils.class,
		TestMap.class, TestMapElement.class, TestNoise.class,
		TestAuthorizableBasicBeans.class, TestUniqueId.class,
/* TestBenchmark.class */})
public class AllTests {

	public static String getPlayerName() {
		return "player-name";
	}

	@BeforeClass
	public static void setUp() {
		System.out.println("Setting up test");
		String address = "127.0.0.1";
		String port = "1099";

		String localDirectoryPath = "./test/ressources/campaign/general-test";

		deleteRecursive(new File(localDirectoryPath));

		CampaignClient.startNewCampaignServer(address, address, port,
				localDirectoryPath);

		CampaignClient.getInstance().createPlayer(getPlayerName(), true);
	}

	@AfterClass
	public static void tearDown() {
		CampaignClient.getInstance().saveGame();
		System.out.println("tearing down");
	}

	public static void deleteRecursive(File base) {
		if (base.listFiles() != null) {
			for (File f : base.listFiles()) {
				deleteRecursive(f);
			}
		}
		base.delete();
	}
}
