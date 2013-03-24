package net.alteiar.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import net.alteiar.CampaignClient;
import net.alteiar.chat.Chat;
import net.alteiar.player.Player;

import org.junit.Test;

public class TestPlayer extends BasicTest {
	@Test
	public void testCompareDocument() {
		Player current = CampaignClient.getInstance().getCurrentPlayer();

		assertTrue("Documents should be equals", current.equals(current));
		assertTrue("Documents should be equals", !current.equals(null));

		Chat chat = CampaignClient.getInstance().getChat();
		assertTrue("Documents should not be equals", !current.equals(chat));
	}

	@Test
	public void testPlayer() {
		Player emptyPlayer = new Player();
		assertEquals("verify empty player equals", emptyPlayer, emptyPlayer);

		Player current = CampaignClient.getInstance().getCurrentPlayer();
		assertEquals("player name should be same", AllTests.getPlayerName(),
				current.getName());

		String targetName = "test-new-name";
		current.setName(targetName);
		sleep(5);
		assertEquals("player name should be same", targetName,
				current.getName());

		Boolean isMj = !current.isMj();
		current.setMj(isMj);
		sleep(5);
		assertEquals("player mj should be same", isMj, current.isMj());

		Color color = Color.RED;
		current.setColor(color);
		sleep(5);
		assertEquals("player color should be same", color, current.getColor());

		current.setMj(!current.isMj());
		current.setName(AllTests.getPlayerName());
		sleep(5);
	}
}
