package net.alteiar.rules;

import java.util.ArrayList;
import java.util.List;

public class RuleElements implements Rule {

	private final String name;

	private final List<BasicRuleElements> elements;

	public RuleElements(String name) {
		this.name = name;
		elements = new ArrayList<BasicRuleElements>();
	}

	@Override
	public String getName() {
		return name;
	}

	public void addRule(BasicRuleElements rule) {
		this.elements.add(rule);
	}

	public List<BasicRuleElements> getRules() {
		return elements;
	}

	@Override
	public String getDescription() {
		StringBuilder builder = new StringBuilder();
		for (Rule rule : elements) {
			builder.append("- " + rule.getName() + ":");
			builder.append("\n");
			builder.append(rule.getDescription());
			builder.append("\n");
			builder.append("----------------------------");
			builder.append("\n");
		}

		return builder.toString();
	}

}
