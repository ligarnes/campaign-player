package net.alteiar.chat;

import java.util.ArrayList;

import net.alteiar.client.bean.BasicBean;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class Chat extends BasicBean {
	private static final long serialVersionUID = 1L;

	public static final String PROP_MESSAGES_PROPERTY = "messages";
	public static final String PROP_PSEUDO_PROPERTY = "pseudo";

	public static final String METH_ADD_MESSAGE_METHOD = "addMessage";

	// pseudo is transient because it is not shared
	@Element
	private transient String pseudo;
	@ElementList
	private ArrayList<Message> messages;

	public Chat() {
		messages = new ArrayList<Message>();
	}

	public void setPseudo(String pseudo) {
		String oldValue = this.pseudo;
		this.pseudo = pseudo;
		notifyLocal(PROP_PSEUDO_PROPERTY, oldValue, this.pseudo);
	}

	public String getPseudo() {
		return this.pseudo;
	}

	public void talk(Message message) {
		addMessage(message);
	}

	public void addMessage(Message message) {
		if (notifyRemote(METH_ADD_MESSAGE_METHOD, null, message)) {
			synchronized (messages) {
				this.messages.add(message);
			}
			notifyLocal(METH_ADD_MESSAGE_METHOD, null, message);
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Message> getMessages() {
		ArrayList<Message> copy = new ArrayList<Message>();
		synchronized (messages) {
			copy = (ArrayList<Message>) messages.clone();
		}

		return copy;
	}

	public void setMessages(ArrayList<Message> messages) {
		ArrayList<Message> oldValue = this.messages;
		if (notifyRemote(PROP_MESSAGES_PROPERTY, oldValue, messages)) {
			synchronized (messages) {
				this.messages = messages;
			}
			notifyLocal(PROP_MESSAGES_PROPERTY, oldValue, messages);
		}
	}
}
