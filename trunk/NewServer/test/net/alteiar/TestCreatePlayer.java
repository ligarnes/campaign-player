package net.alteiar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.chat.ChatRoomClient;
import net.alteiar.server.document.chat.IChatRoomObserver;
import net.alteiar.server.document.chat.message.MessageRemote;
import net.alteiar.server.document.player.PlayerClient;

import org.junit.Test;

public class TestCreatePlayer {

	@Test
	public void testCreatePlayer() {
		PlayerClient current = CampaignClient.getInstance().getCurrentPlayer();
		assertNotNull("Current player must exist", current);

		assertEquals(CampaignClient.getInstance().getPlayers().size(), 1);

	}

	@Test
	public void testChat() {
		ChatRoomClient chat = CampaignClient.getInstance().getChat();
		assertNotNull("Current chat must exist", chat);

		final String expectedMsg = "Salut";

		chat.addChatRoomListener(new IChatRoomObserver() {

			@Override
			public void talk(MessageRemote message) {
				assertEquals(message.getMessage(), expectedMsg);
			}
		});

		chat.talk(expectedMsg);

		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals("the message must be the same", chat.getAllMessage()
				.get(0).getMessage(), expectedMsg);
	}

}
