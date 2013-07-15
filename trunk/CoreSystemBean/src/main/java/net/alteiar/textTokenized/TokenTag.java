package net.alteiar.textTokenized;

import java.util.regex.Pattern;

public class TokenTag extends Token {

	private final String token;
	private final int repeat;

	private final Pattern p;

	private final String beginTag;
	private final String endTag;

	public TokenTag(String name, String token, int repeat, String beginToken,
			String endToken) {
		super(name);
		this.token = token;
		this.repeat = repeat;

		this.beginTag = beginToken;
		this.endTag = endToken;

		String quoteToken = Pattern.quote(token);
		String totalQuoteToken = "";

		for (int i = 0; i < repeat; ++i) {
			totalQuoteToken += quoteToken;
		}

		totalQuoteToken = totalQuoteToken + "(.+?)" + totalQuoteToken;
		p = Pattern.compile(totalQuoteToken);
	}

	@Override
	public String getToken() {
		String totalQuoteToken = "";

		for (int i = 0; i < repeat; ++i) {
			totalQuoteToken += token;
		}
		return totalQuoteToken;
	}

	@Override
	public String replace(String input) {
		return p.matcher(input).replaceAll(beginTag + "$1" + endTag);
	}
}
