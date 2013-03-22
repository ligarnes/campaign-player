package net.alteiar.chat.message;

import java.util.Arrays;

import net.alteiar.shared.Randomizer;

public class DiceSender implements ChatObject {

	public static String DICE_SENDER_COMMAND = "/dice";

	private final String diceCount;
	private final String dice;
	private final String modifier;

	private final String[] results;
	private final String total;

	public DiceSender(Integer diceCount, Integer diceValue, Integer modifier) {
		this.diceCount = diceCount.toString();
		this.dice = diceValue.toString();
		this.modifier = modifier.toString();

		Integer total = modifier;
		results = new String[diceCount];
		for (int i = 0; i < diceCount; i++) {
			Integer current = Randomizer.dice(diceValue);
			total += current;
			results[i] = current.toString();
		}
		this.total = total.toString();
	}

	public DiceSender(String msg) {
		String[] vals = msg.split(";");
		diceCount = vals[0];
		dice = vals[1];
		modifier = vals[2];
		total = vals[3];

		results = Arrays.copyOfRange(vals, 4, vals.length);
	}

	@Override
	public String stringFormat() {
		StringBuilder builder = new StringBuilder();
		// format as: dice count ; dice ; add ; total;dice result
		builder.append(diceCount);
		builder.append(";");
		builder.append(dice);
		builder.append(";");
		builder.append(modifier);
		builder.append(";");
		builder.append(total);
		builder.append(";");

		for (int i = 0; i < results.length; i++) {
			builder.append(results[i] + ";");
		}
		return builder.toString();
	}

	public String getDiceCount() {
		return diceCount;
	}

	public String getDiceValue() {
		return dice;
	}

	public String getModifier() {
		return modifier;
	}

	public String[] getResults() {
		return results;
	}

	public String getTotal() {
		return total;
	}

}
