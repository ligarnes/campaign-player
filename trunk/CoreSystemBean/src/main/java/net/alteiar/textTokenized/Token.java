package net.alteiar.textTokenized;

public abstract class Token {
	private final String name;

	public Token(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract String getToken();

	public abstract String replace(String input);

}
