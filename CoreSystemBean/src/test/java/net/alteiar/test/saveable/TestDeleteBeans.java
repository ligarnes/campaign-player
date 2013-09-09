package net.alteiar.test.saveable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.chat.Chat;
import net.alteiar.chat.MessageFactory;
import net.alteiar.map.MapBean;
import net.alteiar.media.ImageBean;
import net.alteiar.player.Player;
import net.alteiar.shared.UniqueID;
import net.alteiar.test.NewCampaignTest;
import net.alteiar.test.beans.map.TestMap;
import net.alteiar.utils.file.images.WebImage;

import org.junit.Test;

public class TestDeleteBeans extends NewCampaignTest {

	public void save() {
		try {
			CampaignClient.getInstance().saveGame();
		} catch (Exception e) {
			e.printStackTrace();
			fail("not able to save");
		}
	}

	@Test
	public void testSave() {

		assertEquals(1, CampaignClient.getInstance().getPlayers().size());
		assertTrue(CampaignClient.getInstance().getChat() != null);

		save();

		assertEquals(1, countObjectFile(Chat.class));
		assertEquals(1, countObjectFile(Player.class));
		assertEquals(0, countObjectFile(String.class));

		CampaignClient.getInstance().getChat()
				.talk(MessageFactory.currentPlayer("Hello world"));
		sleep();
		save();

		ImageBean toRemove = null;
		try {
			toRemove = new ImageBean(new WebImage(new URL("http://google.com")));
			CampaignClient.getInstance().addBean(toRemove);
		} catch (MalformedURLException e) {
			fail("no exception should occur");
		}

		sleep();
		save();
		assertEquals(1, countObjectFile(ImageBean.class));

		CampaignClient.getInstance().removeBean(toRemove);
		sleep();
		save();
		assertEquals(0, countObjectFile(ImageBean.class));
	}

	@Test(timeout = 1000)
	public void testSaveBattle() {
		UniqueID battleId = null;
		try {
			battleId = TestMap.createBattle("test-map-save",
					TestMap.getDefaultImage());
		} catch (IOException e) {
			fail("no exception should occur");
		}
		save();
		assertEquals(1, countObjectFile(ImageBean.class));

		CampaignClient.getInstance().removeBean(battleId);

		MapBean b = CampaignClient.getInstance().getBean(battleId);
		while (b != null) {
			b = CampaignClient.getInstance().getBean(battleId);
		}
		save();
		assertEquals(0, countObjectFile(ImageBean.class));
	}
}
