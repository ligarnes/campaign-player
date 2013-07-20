package net.alteiar.chat;

import java.io.Serializable;
import java.util.HashSet;

import net.alteiar.documents.BeanDocument;
import net.alteiar.player.Player;
import net.alteiar.shared.UniqueID;
import net.alteiar.textTokenized.TokenManager;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	@ElementList
	private HashSet<UniqueID> to;

	@Element
	private String message;

	public Message() {

	}

	public Message(String message) {
		to = new HashSet<UniqueID>();
		this.message = message;
	}

	public boolean accept(Player player) {
		return to.contains(player.getId()) || to.isEmpty();
	}

	public void addReceiver(Player player) {
		to.add(player.getId());
	}

	public void addReceiver(BeanDocument doc) {
		to.add(doc.getOwner());
	}

	public String getText() {
		return message;
	}

	public String getHtmlFormat() {
		return "<html><p>" + TokenManager.INSTANCE.getHtmlFormat(message)
				+ "</p></html>";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}
}
