package net.alteiar;

import net.alteiar.client.bean.BasicBean;

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
		String tmp = text;
		// \ new line
		tmp = tmp.replaceAll("\\\\\\\\", "<br/>");

		// **bold**
		tmp = replaceTag("\\*\\*", tmp, "<b>", "</b>");

		return "<html><p>" + tmp + "</p></html>";
	}

	private static String replaceTag(String tag, String text, String beginTab,
			String endTag) {
		return text.replaceAll(tag + "([^<]*)" + tag, beginTab + "$1" + endTag);
	}
}
