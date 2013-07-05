package net.alteiar.notepad;

import java.util.regex.Pattern;

public class ReplaceToken extends Token {

	private final String token;
	private final String tag;

	private final Pattern pattern;

	public ReplaceToken(String name, String token, String tag) {
		super(name);
		this.token = token;
		this.tag = tag;

		this.pattern = Pattern.compile(token);
	}

	@Override
	public String getToken() {
		return token;
	}

	@Override
	public String replace(String input) {
		return pattern.matcher(input).replaceAll(tag);
	}

}
