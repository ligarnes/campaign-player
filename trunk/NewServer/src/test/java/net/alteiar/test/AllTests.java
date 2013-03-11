package net.alteiar.test;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.ServerDocuments;
import net.alteiar.test.map.TestCharacterCombat;
import net.alteiar.test.map.TestMap;
import net.alteiar.test.map.TestMapElement;
import net.alteiar.test.map.TestNoise;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestCreatePlayer.class, TestMap.class, TestChat.class,
		TestMapElement.class, TestNoise.class, TestImageUtils.class,
		TestCharacterCombat.class
/* , TestBenchmark.class */})
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
		ServerDocuments.startServer(address, Integer.valueOf(port));
		CampaignClient.connect(address, address, port, localDirectoryPath,
				getPlayerName(), true);
	}

	@AfterClass
	public static void tearDown() {
		System.out.println("tearing down");

		ServerDocuments.SERVER_THREAD_POOL.stopWorkers(2);
		ServerDocuments.SERVER_THREAD_POOL.stopWorkers();
	}
}