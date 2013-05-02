package net.alteiar.test.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import net.alteiar.CampaignClient;
import net.alteiar.chat.Chat;
import net.alteiar.chat.message.DiceSender;
import net.alteiar.chat.message.MessageRemote;
import net.alteiar.chat.message.MjSender;
import net.alteiar.chat.message.PrivateSender;
import net.alteiar.player.Player;
import net.alteiar.test.NewCampaignTest;

import org.junit.Before;
import org.junit.Test;

public class TestChat extends NewCampaignTest {

	@Override
	@Before
	public void beforeTest() {
		super.beforeTest();

		int currentCount = getChat().getMessages().size();

		while (currentCount < 1) {
			currentCount = getChat().getMessages().size();
		}
	}

	public Chat getChat() {
		Chat chat = CampaignClient.getInstance().getChat();
		assertNotNull("Current chat must exist", chat);
		return chat;
	}

	public synchronized MessageRemote getLastMessage(Runnable runnable) {
		int previousCount = getChat().getMessages().size();

		runnable.run();
		int currentCount = getChat().getMessages().size();

		while (previousCount == currentCount) {
			currentCount = getChat().getMessages().size();
		}

		return getChat().getMessages().get(previousCount);
	}

	@Test(timeout = 5000)
	public void testMessageRemote() {
		String tgtExp1 = "expediteur1";
		String tgtExp2 = "expediteur2";
		String tgtExp3 = null;
		String tgtMsg1 = "msg1";
		String tgtMsg2 = "msg2";
		String tgtMsg3 = null;
		String tgtCmd1 = "command1";
		String tgtCmd2 = "command2";
		String tgtCmd3 = null;
		MessageRemote msg1 = new MessageRemote(tgtExp1, tgtMsg1, tgtCmd1);
		MessageRemote msg2 = new MessageRemote(tgtExp1, tgtMsg1, tgtCmd1);
		MessageRemote msg3 = new MessageRemote(tgtExp2, tgtMsg1, tgtCmd1);
		MessageRemote msg4 = new MessageRemote(tgtExp1, tgtMsg2, tgtCmd1);
		MessageRemote msg5 = new MessageRemote(tgtExp1, tgtMsg1, tgtCmd2);
		MessageRemote msg6 = new MessageRemote(tgtExp3, tgtMsg1, tgtCmd1);
		MessageRemote msg7 = new MessageRemote(tgtExp1, tgtMsg3, tgtCmd1);
		MessageRemote msg8 = new MessageRemote(tgtExp1, tgtMsg1, tgtCmd3);

		assertTrue("message should be different with null", !msg1.equals(null));
		assertTrue("message should be different with different object",
				!msg1.equals(""));
		assertTrue("message should be same to itself", msg1.equals(msg1));
		assertTrue("message should be same to another similar message",
				msg1.equals(msg2));
		assertTrue("message should be different if the sender is different",
				!msg1.equals(msg3));
		assertTrue("message should be different if the text is different",
				!msg1.equals(msg4));
		assertTrue("message should be different if the command is different",
				!msg1.equals(msg5));
		assertTrue("message should be different if the command is different",
				!msg6.equals(msg1));
		assertTrue("message should be different if the command is different",
				!msg7.equals(msg2));
		assertTrue("message should be different if the command is different",
				!msg8.equals(msg3));

		assertTrue("message should have the same hashcode to equal object",
				msg1.hashCode() == msg2.hashCode());
		assertTrue("message should have a different hashcode to other",
				msg1.hashCode() != msg3.hashCode());

		assertEquals("sender should be same", msg1.getSender(), tgtExp1);
		assertEquals("message should be same", msg1.getMessage(), tgtMsg1);
		assertEquals("command should be same", msg1.getCommand(), tgtCmd1);
	}

	/*
	 * @Test(timeout = 10000) public void testChatComplete() { testChat();
	 * testChatDiceSender(); testChatPrivateSender(); testChatMjSender(); }
	 */

	@Test(timeout = 5000)
	public void testChat() {
		assertEquals("Pseudo should be same as player name", getPlayerName(),
				getChat().getPseudo());

		final String expectedMsg = "Salut";

		Runnable sendTextMessage = new Runnable() {
			@Override
			public void run() {
				getChat().talk(expectedMsg);
			}
		};
		MessageRemote msg = getLastMessage(sendTextMessage);

		assertEquals("the message must be the same", getPlayerName(),
				msg.getSender());

		assertEquals("the message must be the same", expectedMsg,
				msg.getMessage());

		String pseudo = "my pseudo";
		getChat().setPseudo(pseudo);
		assertEquals("Pseudo should be same as new pseudo changed", pseudo,
				getChat().getPseudo());

		ArrayList<MessageRemote> allMsgExpected = new ArrayList<MessageRemote>();
		allMsgExpected.add(new MessageRemote("expediteur", "my message",
				"command"));

		getChat().setMessages(allMsgExpected);
		sleep(10);
		List<MessageRemote> allMsg = getChat().getMessages();

		for (int i = 0; i < allMsgExpected.size(); ++i) {
			assertEquals("Message should be same", allMsgExpected.get(i),
					allMsg.get(i));
		}
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
								expectedModifier),
						DiceSender.DICE_SENDER_COMMAND);
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

	@Test(timeout = 5000)
	public void testChatPrivateSender() {
		final String expectedMessage = "my_message";

		Runnable sendPrivateMessage = new Runnable() {
			@Override
			public void run() {
				getChat().talk(
						new PrivateSender(getPlayerName(), expectedMessage),
						"/to");
			}
		};

		MessageRemote msg = getLastMessage(sendPrivateMessage);

		PrivateSender resultMessage = new PrivateSender(msg.getMessage());
		assertEquals("The message should be the same", expectedMessage,
				resultMessage.getMessage());

		assertTrue("The message is for us so we can access it",
				resultMessage.canAccess());

		sendPrivateMessage = new Runnable() {
			@Override
			public void run() {
				getChat().talk(new PrivateSender("nobody", "msg"), "/to");
			}
		};

		msg = getLastMessage(sendPrivateMessage);
		resultMessage = new PrivateSender(msg.getMessage());
		assertTrue("The message is not for us so we can't access it",
				!resultMessage.canAccess());
	}

	@Test(timeout = 5000)
	public void testChatMjSender() {
		final String name = CampaignClient.getInstance().getCurrentPlayer()
				.getName();
		final String expectedMessage = "my message";

		Runnable sendMjMessage = new Runnable() {
			@Override
			public void run() {
				getChat().talk(new MjSender(name, expectedMessage), "/mj");
			}
		};

		MessageRemote msg = getLastMessage(sendMjMessage);

		MjSender resultMessage = new MjSender(msg.getMessage());
		assertEquals("The message should be the same", expectedMessage,
				resultMessage.getMessage());

		assertTrue("The message is for us so we can access it",
				resultMessage.canAccess());

		sendMjMessage = new Runnable() {
			@Override
			public void run() {
				getChat().talk(new MjSender("no-one", expectedMessage), "/mj");
			}
		};

		msg = getLastMessage(sendMjMessage);

		Player current = CampaignClient.getInstance().getCurrentPlayer();
		current.setMj(false);
		sleep(10);

		resultMessage = new MjSender(msg.getMessage());
		assertTrue(
				"The message is for the mj and we are not the mj so we cannot access it",
				!resultMessage.canAccess());

		current.setMj(true);
	}
}
