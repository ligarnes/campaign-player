package net.alteiar.beans.chat;

import java.util.Iterator;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.player.Player;
import net.alteiar.textTokenized.TokenManager;

public class MessageFactory {

	public static Message connectMessage(Player player) {
		String token = TokenManager.getTokenByName(TokenManager.BOLD)
				.getToken();
		return new Message(token + player.getName()
				+ " viens de rejoindre la partie" + token);
	}

	public static Message currentPlayer(String message) {
		return textMessage(CampaignClient.getInstance().getCurrentPlayer(),
				message);
	}

	public static Message textMessage(Player sender, String message) {
		return textMessage(sender.getName(), message);
	}

	public static Message textMessage(String sender, String message) {
		return new Message(coloredMessage(boldText(sender + ": ") + message));
	}

	public static Message privateMessage(String receiver, String message) {
		return privateMessage(CampaignClient.getInstance().getCurrentPlayer(),
				receiver, message);
	}

	public static Message privateMessage(Player sender, String receiver,
			String message) {
		return privateMessage(sender.getName(), receiver, message);
	}

	public static Message privateMessage(String sender, String receiver,
			String message) {
		Player rec = null;

		Iterator<Player> players = CampaignClient.getInstance().getPlayers()
				.iterator();

		while (rec == null && players.hasNext()) {
			Player current = players.next();
			if (current.getName().equalsIgnoreCase(receiver)) {
				rec = current;
			}
		}

		Message privateMsg = null;
		if (rec != null) {
			privateMsg = privateMessage(sender, rec, message);
		} else {
			privateMsg = selfMessage("Le joueur " + receiver + " n'existe pas");
		}

		return privateMsg;
	}

	public static Message selfMessage(String message) {
		String text = message;
		Message privateMsg = new Message(text);
		privateMsg.addReceiver(CampaignClient.getInstance().getCurrentPlayer());

		return privateMsg;
	}

	public static Message privateMessage(String sender, Player receiver,
			String message) {
		String text = italicText(boldText(sender + ": ") + message);
		Message privateMsg = new Message(text);
		privateMsg.addReceiver(receiver);
		privateMsg.addReceiver(CampaignClient.getInstance().getCurrentPlayer());

		return privateMsg;
	}

	public static Message dmMessage(String message) {
		return dmMessage(CampaignClient.getInstance().getCurrentPlayer(),
				message);
	}

	public static Message dmMessage(Player sender, String message) {
		return dmMessage(sender.getName(), message);
	}

	public static Message dmMessage(String sender, String message) {
		return privateMessage(sender, CampaignClient.getInstance().getDm(),
				message);
	}

	private static String boldText(String text) {
		String token = TokenManager.getTokenByName(TokenManager.BOLD)
				.getToken();
		return token + text + token;
	}

	private static String italicText(String text) {
		String token = TokenManager.getTokenByName(TokenManager.ITALIC)
				.getToken();
		return token + text + token;
	}

	private static String coloredMessage(String text) {
		// FIXME NOT IMPLEMENTED
		return text;
	}
}
