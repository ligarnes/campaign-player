package net.alteiar.test.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import net.alteiar.beans.chat.Chat;
import net.alteiar.beans.chat.Message;
import net.alteiar.beans.chat.MessageFactory;
import net.alteiar.campaign.CampaignClient;
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

	public synchronized Message getLastMessage(Runnable runnable) {
		int previousCount = getChat().getMessages().size();

		runnable.run();
		int currentCount = getChat().getMessages().size();

		while (previousCount == currentCount) {
			currentCount = getChat().getMessages().size();
		}

		return getChat().getMessages().get(previousCount);
	}

	@Test(timeout = 5000)
	public void testMessage() {
		System.out.println("test message");

		String tgtMsg1 = "**msg1**";
		String tgtMsg2 = "**msg1**";
		String tgtMsg3 = "msg1";
		Message msg1 = new Message(tgtMsg1);
		Message msg2 = new Message(tgtMsg2);
		Message msg3 = new Message(tgtMsg3);

		assertTrue("message should be different with null", !msg1.equals(null));
		assertTrue("message should be different with different object",
				!msg1.equals(""));
		assertTrue("message should be same to itself", msg1.equals(msg1));
		assertTrue("message should be same to another similar message",
				msg1.equals(msg2));
		assertTrue("message should'nt be same to another different message",
				!msg1.equals(msg3));

		assertTrue("message should be same to another similar message", msg1
				.getHtmlFormat().equals(msg2.getHtmlFormat()));
		assertTrue("message should'nt be same to another different message",
				!msg1.getHtmlFormat().equals(msg3.getHtmlFormat()));

		assertEquals("message should be same", msg1.getText(), tgtMsg1);

		System.out.println("test message");
	}

	@Test(timeout = 5000)
	public void testChat() {
		final String expectedMsg = "Salut";

		Runnable sendTextMessage = new Runnable() {
			@Override
			public void run() {
				getChat().talk(new Message(expectedMsg));
			}
		};
		Message msg = getLastMessage(sendTextMessage);

		assertEquals("the message must be the same", expectedMsg, msg.getText());

		ArrayList<Message> allMsgExpected = new ArrayList<Message>();
		allMsgExpected.add(MessageFactory.textMessage(CampaignClient
				.getInstance().getCurrentPlayer(), "my message"));

		getChat().setMessages(allMsgExpected);
		sleep();
		List<Message> allMsg = getChat().getMessages();

		for (int i = 0; i < allMsgExpected.size(); ++i) {
			assertEquals("Message should be same", allMsgExpected.get(i)
					.getHtmlFormat(), allMsg.get(i).getHtmlFormat());
		}
	}

	@Test(timeout = 5000)
	public void testChatPrivateSender() {
		final String expectedMessage = "my_message";

		Message msg = new Message(expectedMessage);
		msg.addReceiver(CampaignClient.getInstance().getCurrentPlayer());

		Runnable sendTextMessage = new Runnable() {
			@Override
			public void run() {
				Message msg = new Message(expectedMessage);
				msg.addReceiver(CampaignClient.getInstance().getCurrentPlayer());
				getChat().talk(msg);
			}
		};

		System.out.println("wait for last message 1");
		Message result = getLastMessage(sendTextMessage);

		assertTrue("The message is for us so we can access it",
				result.accept(CampaignClient.getInstance().getCurrentPlayer()));

		sendTextMessage = new Runnable() {
			@Override
			public void run() {
				Message msg = new Message(expectedMessage);
				msg.addReceiver(new Player());
				getChat().talk(msg);
			}
		};

		System.out.println("wait for last message 2");
		result = getLastMessage(sendTextMessage);
		assertTrue("The message is not for us so we can't access it",
				!result.accept(CampaignClient.getInstance().getCurrentPlayer()));

		sendTextMessage = new Runnable() {
			@Override
			public void run() {
				getChat().talk(
						MessageFactory
								.privateMessage("unknow", expectedMessage));
			}
		};

		System.out.println("wait for last message 3");
		result = getLastMessage(sendTextMessage);
		assertTrue("The message is not for us so we can't access it",
				result.accept(CampaignClient.getInstance().getCurrentPlayer()));

		assertTrue(
				"the message has been send but fail so we should get fail message",
				!result.getText().equals(expectedMessage));
	}

	@Test(timeout = 5000)
	public void testChatMjSender() {
		final String expectedMessage = "my message";

		Runnable sendTextMessage = new Runnable() {
			@Override
			public void run() {
				Message msg = new Message(expectedMessage);
				msg.addReceiver(new Player());
				getChat().talk(MessageFactory.dmMessage(expectedMessage));
			}
		};

		System.out.println("wait for last message 1");
		Message result = getLastMessage(sendTextMessage);

		assertTrue("The message is for us so we can access it",
				result.accept(CampaignClient.getInstance().getCurrentPlayer()));
	}
}
