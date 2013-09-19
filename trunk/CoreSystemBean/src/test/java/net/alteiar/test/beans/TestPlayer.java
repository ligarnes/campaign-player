package net.alteiar.test.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import net.alteiar.beans.chat.Chat;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.newversion.shared.bean.BeanEncapsulator;
import net.alteiar.player.Player;
import net.alteiar.test.NewCampaignTest;

import org.junit.Test;

public class TestPlayer extends NewCampaignTest {
	@Test
	public void testCompareDocument() {
		Player current = CampaignClient.getInstance().getCurrentPlayer();

		assertTrue("Documents should be equals", current.equals(current));
		assertTrue("Documents should be equals", !current.equals(null));

		Chat chat = CampaignClient.getInstance().getChat();
		assertTrue("Documents should not be equals", !current.equals(chat));

		Player newBean = new Player();
		newBean.setId(null);

		assertTrue("Documents should not be equals", !newBean.equals(current));

		newBean.setId(current.getId());
		assertTrue("Documents should not be equals", newBean.equals(current));

		assertEquals("Documents should not be equals", newBean.hashCode(),
				current.hashCode());
	}

	@Test
	public void testBeanEncapsulator() {
		Player current = CampaignClient.getInstance().getCurrentPlayer();
		BeanEncapsulator playerEncapsulator = new BeanEncapsulator(current);
		playerEncapsulator.beanRemoved();
	}

	@Test
	public void testPlayer() {
		Player emptyPlayer = new Player();
		assertEquals("verify empty player equals", emptyPlayer, emptyPlayer);

		Player current = CampaignClient.getInstance().getCurrentPlayer();
		assertEquals("player name should be same", getPlayerName(),
				current.getName());

		String targetName = "test-new-name";
		current.setName(targetName);
		waitForChange(current, "getName", targetName);

		Boolean isDm = !current.isDm();
		current.setDm(isDm);
		waitForChange(current, "isDm", isDm);

		Color color = Color.RED;
		current.changeColor(color);
		sleep();
		assertEquals("player color should be same", color,
				current.getRealColor());

		isDm = !current.isDm();
		current.setDm(isDm);
		waitForChange(current, "isDm", isDm);

		targetName = getPlayerName();
		current.setName(getPlayerName());
		waitForChange(current, "getName", targetName);

		assertTrue("player should be connected", current.getConnected());
		current.setConnected(false);
		waitForChange(current, "getConnected", false);
	}
}
