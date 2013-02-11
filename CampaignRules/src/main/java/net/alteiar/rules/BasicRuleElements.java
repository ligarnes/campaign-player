package net.alteiar.rules;

public class BasicRuleElements implements Rule {
	private final String name;
	private final String description;

	public BasicRuleElements(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
