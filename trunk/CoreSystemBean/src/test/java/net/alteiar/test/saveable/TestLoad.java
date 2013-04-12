package net.alteiar.test.saveable;

import static org.junit.Assert.assertEquals;

import java.util.List;

import net.alteiar.CampaignClient;
import net.alteiar.player.Player;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestLoad extends TestSaveAndLoad {

	@BeforeClass
	public static void setUp() {
		System.out.println("Setting up test");
		// then setup the environnement
		String address = "127.0.0.1";
		String port = "1099";

		CampaignClient.loadCampaignServer(address, address, port,
				getCampaignDirectory());

		List<Player> players = CampaignClient.getInstance().getPlayers();
		CampaignClient.getInstance().selectPlayer(players.get(0));
	}

	@Test
	public void testLoad() {
		assertEquals(CampaignClient.getInstance().getPlayers().get(0),
				CampaignClient.getInstance().getCurrentPlayer());
	}
}
