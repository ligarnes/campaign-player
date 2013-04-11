package net.alteiar.test;

import net.alteiar.CampaignClient;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ /* TestChat.class, */TestPlayer.class /*
													 * , TestImageUtils.class,
													 * TestMap.class,
													 * TestMapElement.class,
													 * TestNoise.class,
													 * TestAuthorizableBasicBeans
													 * .class,
													 * TestUniqueId.class, /*
													 * TestBenchmark.class
													 */})
public class AllTests {

	public static String getPlayerName() {
		return "player-name";
	}

	@BeforeClass
	public static void setUp() {
		System.out.println("Setting up test");
		String address = "127.0.0.1";
		String port = "1099";

		String localDirectoryPath = "./test/ressources/campaign/";
		CampaignClient.startServer(address, port, localDirectoryPath);
		CampaignClient.connect(address, address, port, localDirectoryPath,
				getPlayerName(), true);
	}

	@AfterClass
	public static void tearDown() {
		CampaignClient.getInstance().saveGame();
		System.out.println("tearing down");
	}
}
