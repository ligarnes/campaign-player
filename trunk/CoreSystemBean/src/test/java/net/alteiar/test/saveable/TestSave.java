package net.alteiar.test.saveable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import net.alteiar.CampaignClient;
import net.alteiar.chat.Chat;
import net.alteiar.player.Player;
import net.alteiar.test.map.TestMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSave extends TestSaveAndLoad {

	@BeforeClass
	public static void setUp() {
		System.out.println("Setting up test");
		// then setup the environnement
		String address = "127.0.0.1";
		String port = "1099";

		CampaignClient.startNewCampaignServer(address, address, port,
				getCampaignDirectory());
		CampaignClient.getInstance().createPlayer(getPlayerName(), true);
	}

	@Before
	public void beforeTest() {
		clearSaveCampaign();
	}

	@Test
	public void testSave() {
		CampaignClient.getInstance().saveGame();

		assertEquals(1, countObjectFile(Chat.class));
		assertEquals(1, countObjectFile(Player.class));
		assertEquals(0, countObjectFile(String.class));

		CampaignClient.getInstance().getChat().talk("Hello world");
		sleep(10);
		CampaignClient.getInstance().saveGame();

		try {
			TestMap.createBattle("test-map-save", TestMap.getDefaultImage());
		} catch (IOException e) {
			fail("not exception should occur");
		}

		sleep(10);
		CampaignClient.getInstance().saveGame();
	}
}
