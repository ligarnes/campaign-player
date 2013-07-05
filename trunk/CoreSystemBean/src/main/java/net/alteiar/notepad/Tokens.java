package net.alteiar.notepad;

import java.util.Collection;
import java.util.HashMap;

public class Tokens {
	public static Tokens INSTANCE = new Tokens();

	public static final String NEW_LINE = "new_line";

	public static final String BOLD = "bold";
	public static final String ITALIC = "italic";
	public static final String UNDERLINE = "underline";

	private final HashMap<String, Token> tokens;

	private Tokens() {
		tokens = new HashMap<String, Token>();

		tokens.put(BOLD, new TokenTag("Bold", "*", 2, "<b>", "</b>"));
		tokens.put(ITALIC, new TokenTag("Italic", "#", 2, "<i>", "</i>"));
		tokens.put(UNDERLINE, new TokenTag("Underline", "_", 2, "<u>", "</u>"));

		tokens.put(NEW_LINE, new ReplaceToken("New line", "\\\\", "<br/>"));
	}

	public Collection<Token> getTokens() {
		return tokens.values();
	}

	public Token getToken(String name) {
		return tokens.get(name);
	}
}
