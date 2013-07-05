package net.alteiar.notepad;

import net.alteiar.client.bean.BasicBean;

import org.simpleframework.xml.Element;

public class Notepad extends BasicBean {
	private static final long serialVersionUID = 1L;

	private final String PROP_TEXT_PROPERTY = "text";

	public static String NEW_LINE = "\\\\";
	public static String BOLD = "**";
	public static String ITALIC = "##";
	public static String UNDERLINE = "__";

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

		for (Token token : Tokens.INSTANCE.getTokens()) {
			tmp = token.replace(tmp);
		}

		return "<html><p>" + tmp + "</p></html>";
	}
}
