package net.alteiar.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.chat.ChatRoomClient;
import net.alteiar.server.document.chat.IChatRoomObserver;
import net.alteiar.server.document.chat.message.DiceSender;
import net.alteiar.server.document.chat.message.MessageRemote;
import net.alteiar.server.document.chat.message.PrivateSender;

import org.junit.Test;

public class TestChat extends BasicTest {

	public ChatRoomClient getChat() {
		ChatRoomClient chat = CampaignClient.getInstance().getChat();
		assertNotNull("Current chat must exist", chat);
		return chat;
	}

	public synchronized MessageRemote getLastMessage(Runnable runnable) {
		int previousCount = getChat().getAllMessage().size();

		runnable.run();
		int currentCount = getChat().getAllMessage().size();

		while (previousCount == currentCount) {
			currentCount = getChat().getAllMessage().size();
			sleep(50);
		}

		return getChat().getAllMessage().get(previousCount);
	}

	@Test(timeout = 5000)
	public void testChat() {
		final String expectedMsg = "Salut";

		IChatRoomObserver observer = new IChatRoomObserver() {
			@Override
			public void talk(MessageRemote message) {
				assertEquals(message.getMessage(), expectedMsg);
			}
		};
		getChat().addChatRoomListener(observer);

		Runnable sendTextMessage = new Runnable() {
			@Override
			public void run() {
				System.out.println("send message : " + expectedMsg);
				getChat().talk(expectedMsg);
			}
		};

		assertEquals("the message must be the same",
				getLastMessage(sendTextMessage).getMessage(), expectedMsg);

		getChat().removeChatRoomListener(observer);
	}

	@Test(timeout = 5000)
	public void testChatDiceSender() {
		final Integer expectedDiceCount = 2;
		final Integer expectedDiceValue = 6;
		final Integer expectedModifier = 3;

		int totalMin = 5;
		int totalMax = 15;

		int diceMin = 1;
		int diceMax = 6;

		Runnable sendDiceMessage = new Runnable() {
			@Override
			public void run() {
				getChat().talk(
						new DiceSender(expectedDiceCount, expectedDiceValue,
								expectedModifier), "/dice");
			}
		};

		MessageRemote msg = getLastMessage(sendDiceMessage);

		DiceSender diceSender = new DiceSender(msg.getMessage());

		assertEquals("The dice count should be same", expectedDiceCount,
				Integer.valueOf(diceSender.getDiceCount()));
		assertEquals("The dice value should be same", expectedDiceValue,
				Integer.valueOf(diceSender.getDiceValue()));
		assertEquals("The dice modifier should be same", expectedModifier,
				Integer.valueOf(diceSender.getModifier()));

		assertTrue("The total should be between " + totalMin + " and "
				+ totalMax, Integer.valueOf(diceSender.getTotal()) >= totalMin
				&& Integer.valueOf(diceSender.getTotal()) <= totalMax);

		for (String result : diceSender.getResults()) {
			assertTrue(
					"The dice should be between " + diceMin + " and " + diceMax,
					Integer.valueOf(result) >= diceMin
							&& Integer.valueOf(result) <= diceMax);
		}
	}

	// @Test(timeout = 5000) TODO fix it
	public void testChatPrivateSender() {
		final String expectedMessage = "my_message";

		Runnable sendPrivateMessage = new Runnable() {
			@Override
			public void run() {
				getChat().talk(
						new PrivateSender(AllTests.getPlayerName(),
								expectedMessage), "/to");
			}
		};

		MessageRemote msg = getLastMessage(sendPrivateMessage);

		PrivateSender resultMessage = new PrivateSender(msg.getMessage());
		assertEquals("The message should be the same", expectedMessage,
				resultMessage.getMessage());
	}
}
