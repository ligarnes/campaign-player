package net.alteiar.chat;

import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

import net.alteiar.chat.message.ChatObject;
import net.alteiar.chat.message.MessageRemote;
import net.alteiar.client.bean.BasicBeans;

public class Chat extends BasicBeans {
	private static final long serialVersionUID = 1L;

	public static final String PROP_MESSAGES_PROPERTY = "messages";
	public static final String PROP_ADD_MESSAGE_PROPERTY = "message";
	public static final String PROP_PSEUDO_PROPERTY = "pseudo";

	// pseudo is transient because it is not shared
	private transient String pseudo;
	private List<MessageRemote> messages;

	public Chat() {
		messages = new ArrayList<>();
	}

	public void setPseudo(String pseudo) {
		String oldValue = this.pseudo;
		this.pseudo = pseudo;
		propertyChangeSupport.firePropertyChange(PROP_PSEUDO_PROPERTY,
				oldValue, this.pseudo);
	}

	public String getPseudo() {
		return this.pseudo;
	}

	public void talk(String message) {
		talk(message, MessageRemote.TEXT_MESSAGE);
	}

	public void talk(ChatObject obj, String command) {
		talk(obj.stringFormat(), command);
	}

	public void talk(String message, String command) {
		setMessage(new MessageRemote(pseudo, message, command));
	}

	public MessageRemote getMessage() {
		return null;
	}

	public void setMessage(MessageRemote message) {
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_ADD_MESSAGE_PROPERTY, null, message);
			this.messages.add(message);
			propertyChangeSupport.firePropertyChange(PROP_ADD_MESSAGE_PROPERTY,
					null, message);
		} catch (PropertyVetoException e) {
			// TODO do not care
			// e.printStackTrace();
		}
	}

	public List<MessageRemote> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageRemote> messages) {
		this.messages = messages;

		List<MessageRemote> oldValue = this.messages;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_MESSAGES_PROPERTY, oldValue, messages);
			this.messages = messages;
			propertyChangeSupport.firePropertyChange(PROP_MESSAGES_PROPERTY,
					oldValue, messages);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
}
