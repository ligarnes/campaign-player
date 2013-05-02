package net.alteiar.test.saveable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.alteiar.CampaignClient;
import net.alteiar.chat.Chat;
import net.alteiar.documents.map.MapBean;
import net.alteiar.image.ImageBean;
import net.alteiar.player.Player;
import net.alteiar.shared.UniqueID;
import net.alteiar.test.NewCampaignTest;
import net.alteiar.test.beans.map.TestMap;
import net.alteiar.utils.images.WebImage;

import org.junit.Test;

public class TestDeleteBeans extends NewCampaignTest {

	@Test
	public void testSave() {

		assertEquals(1, CampaignClient.getInstance().getPlayers().size());
		assertTrue(CampaignClient.getInstance().getChat() != null);

		CampaignClient.getInstance().saveGame();

		assertEquals(1, countObjectFile(Chat.class));
		assertEquals(1, countObjectFile(Player.class));
		assertEquals(0, countObjectFile(String.class));

		CampaignClient.getInstance().getChat().talk("Hello world");
		sleep(10);
		CampaignClient.getInstance().saveGame();

		ImageBean toRemove = null;
		try {
			toRemove = new ImageBean(new WebImage(new URL("http://google.com")));
			CampaignClient.getInstance().addBean(toRemove);
		} catch (MalformedURLException e) {
			fail("no exception should occur");
		}

		sleep(10);
		CampaignClient.getInstance().saveGame();
		assertEquals(1, countObjectFile(ImageBean.class));

		CampaignClient.getInstance().removeBean(toRemove);
		sleep(10);
		CampaignClient.getInstance().saveGame();
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
		CampaignClient.getInstance().saveGame();
		assertEquals(1, countObjectFile(ImageBean.class));

		CampaignClient.getInstance().removeBean(battleId);

		MapBean b = CampaignClient.getInstance().getBean(battleId);
		while (b != null) {
			b = CampaignClient.getInstance().getBean(battleId);
		}
		CampaignClient.getInstance().saveGame();
		assertEquals(0, countObjectFile(ImageBean.class));
	}
}
