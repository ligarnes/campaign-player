package net.alteiar;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.ServerDocuments;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestCreatePlayer.class, TestBenchmark.class })
public class AllTests {
	@BeforeClass
	public static void setUp() {
		System.out.println("Setting up test");
		String address = "127.0.0.1";
		String port = "1099";
		ServerDocuments.startServer(address, Integer.valueOf(port));
		CampaignClient.connect(address, address, port, "test", true);
	}

	@AfterClass
	public static void tearDown() {
		System.out.println("tearing down");
	}
}
