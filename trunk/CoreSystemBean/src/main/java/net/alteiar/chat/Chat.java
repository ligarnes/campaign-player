package net.alteiar.chat;

import java.util.ArrayList;

import net.alteiar.chat.message.ChatObject;
import net.alteiar.chat.message.MessageRemote;
import net.alteiar.client.bean.BasicBean;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class Chat extends BasicBean {
	@Attribute
	private static final long serialVersionUID = 1L;

	public static final String PROP_MESSAGES_PROPERTY = "messages";
	public static final String PROP_PSEUDO_PROPERTY = "pseudo";

	public static final String METH_ADD_MESSAGE_METHOD = "addMessage";

	// pseudo is transient because it is not shared
	@Element
	private transient String pseudo;
	@ElementList
	private ArrayList<MessageRemote> messages;

	public Chat() {
		messages = new ArrayList<MessageRemote>();
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
		addMessage(new MessageRemote(pseudo, message, command));
	}

	public void addMessage(MessageRemote message) {
		if (notifyRemote(METH_ADD_MESSAGE_METHOD, null, message)) {
			synchronized (messages) {
				this.messages.add(message);
			}
			propertyChangeSupport.firePropertyChange(METH_ADD_MESSAGE_METHOD,
					null, message);
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<MessageRemote> getMessages() {
		ArrayList<MessageRemote> copy = new ArrayList<MessageRemote>();
		synchronized (messages) {
			copy = (ArrayList<MessageRemote>) messages.clone();
		}

		return copy;
	}

	public void setMessages(ArrayList<MessageRemote> messages) {
		ArrayList<MessageRemote> oldValue = this.messages;
		if (notifyRemote(PROP_MESSAGES_PROPERTY, oldValue, messages)) {
			synchronized (messages) {
				this.messages = messages;
			}
			propertyChangeSupport.firePropertyChange(PROP_MESSAGES_PROPERTY,
					oldValue, messages);
		}
	}
}
