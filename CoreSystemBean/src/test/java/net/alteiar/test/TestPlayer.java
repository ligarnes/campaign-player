package net.alteiar.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.alteiar.CampaignClient;
import net.alteiar.player.Player;

import org.junit.Test;

public class TestPlayer extends BasicTest {
	@Test
	public void testCompareDocument() {
		Player current = CampaignClient.getInstance().getCurrentPlayer();

		assertTrue("Documents should be equals", current.equals(current));
		assertTrue("Documents should be equals", !current.equals(null));

		// ChatRoomClient chat = CampaignClient.getInstance().getChat();
		// assertTrue("Documents should not be equals", !current.equals(chat));
	}

	@Test
	public void testCreatePlayer() {
		Player current = CampaignClient.getInstance().getCurrentPlayer();
		assertNotNull("Current player must exist", current);

		assertEquals("Player names should be same", current.getName(),
				AllTests.getPlayerName());

		assertEquals(CampaignClient.getInstance().getPlayers().size(), 1);
	}
}
