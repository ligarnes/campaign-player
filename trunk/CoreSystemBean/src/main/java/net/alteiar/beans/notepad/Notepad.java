package net.alteiar.beans.notepad;

import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.textTokenized.TokenManager;

import org.simpleframework.xml.Element;

public class Notepad extends BasicBean {
	private static final long serialVersionUID = 1L;

	private final String PROP_TEXT_PROPERTY = "text";

	@Element
	private String text;

	public Notepad() {
	}

	public Notepad(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		String oldValue = this.text;
		if (notifyRemote(PROP_TEXT_PROPERTY, oldValue, text)) {
			this.text = text;
			notifyLocal(PROP_TEXT_PROPERTY, oldValue, text);
		}
	}

	public String getHtmlFormat() {
		return "<html><p>" + TokenManager.INSTANCE.getHtmlFormat(text)
				+ "</p></html>";
	}
}
