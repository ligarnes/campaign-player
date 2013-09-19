package net.alteiar.beans.chat;

import java.util.ArrayList;

import net.alteiar.newversion.shared.bean.BasicBean;

import org.simpleframework.xml.ElementList;

public class Chat extends BasicBean {
	private static final long serialVersionUID = 1L;

	public static final String PROP_MESSAGES_PROPERTY = "messages";

	public static final String METH_ADD_MESSAGE_METHOD = "addMessage";

	// pseudo is transient because it is not shared
	@ElementList
	private ArrayList<Message> messages;

	public Chat() {
		messages = new ArrayList<Message>();
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
