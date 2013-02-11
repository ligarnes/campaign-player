package net.alteiar.rules;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BasicRuleElements el = new BasicRuleElements("Aider quelqu’un",
				"description");

		RuleElements charge = new RuleElements("Charge");
		charge.addRule(new BasicRuleElements("Déplacement durant une charge",
				"info sur déplacement"));
		charge.addRule(new BasicRuleElements("Attaque lors d’une charge",
				"info sur attaque"));

		// combobox
		System.out.println(charge.getName());
		for (Rule r : charge.getRules()) {
			System.out.println("- " + r.getName());
		}
	}

	public static String printRules(Rule rule) {
		return "";
	}

	public static String printRules(RuleElements rule, int deep) {
		StringBuilder builder = new StringBuilder();
		builder.append(rule.getName());
		for (Rule r : rule.getRules()) {
			builder.append("-" + printRules(r));
			System.out.println("- " + r.getName());
		}

		return builder.toString();
	}

	public static String printRules(BasicRuleElements rule, int deep) {
		return rule.getName();
	}

}
