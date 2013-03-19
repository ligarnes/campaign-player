package net.alteiar.chat;

import java.beans.PropertyVetoException;
import java.util.List;

import net.alteiar.client.bean.BasicBeans;

public class ChatBean extends BasicBeans {
	private static final long serialVersionUID = 1L;

	public static final String PROP_MESSAGES_PROPERTY = "messages";
	public static final String PROP_ADD_MESSAGE_PROPERTY = "message";
	private List<String> messages;

	public ChatBean() {
	}

	public void addMessage(String message) {
		try {
			vetoableChangeSupport.fireVetoableChange(PROP_MESSAGES_PROPERTY,
					null, message);
			this.messages.add(message);
			propertyChangeSupport.firePropertyChange(PROP_MESSAGES_PROPERTY,
					null, message);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;

		List<String> oldValue = this.messages;
		try {
			vetoableChangeSupport.fireVetoableChange(PROP_MESSAGES_PROPERTY,
					oldValue, messages);
			this.messages = messages;
			propertyChangeSupport.firePropertyChange(PROP_MESSAGES_PROPERTY,
					oldValue, messages);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
}
