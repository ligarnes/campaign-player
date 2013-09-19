package net.alteiar.test.beans.dice;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map.Entry;

import net.alteiar.beans.dice.Dice;
import net.alteiar.beans.dice.DiceBag;
import net.alteiar.beans.dice.DiceListener;
import net.alteiar.beans.dice.DiceRoller;
import net.alteiar.beans.dice.DiceSingle;
import net.alteiar.beans.dice.visitor.DiceCountVisitor;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.test.NewCampaignTest;

import org.junit.Test;

public class TestDice extends NewCampaignTest {

	private class TestListener extends DiceListener {
		private int callCount;

		private final HashMap<Integer, Integer> diceCount;

		public TestListener(Integer dice, Integer count) {
			this(new HashMap<Integer, Integer>());
			this.diceCount.put(dice, count);
		}

		public TestListener(HashMap<Integer, Integer> diceCount) {
			callCount = 0;
			this.diceCount = diceCount;
		}

		public int getCallCount() {
			return callCount;
		}

		@Override
		public void diceRolled(Dice dice) {
			DiceCountVisitor visitor = new DiceCountVisitor();
			dice.visit(visitor);

			for (Entry<Integer, Integer> entry : diceCount.entrySet()) {
				assertEquals("it should contain " + entry.getValue() + "d"
						+ entry.getKey(), entry.getValue(),
						visitor.getDiceCount(entry.getKey()));
			}

			callCount++;
		}
	}

	@Test(timeout = 1000)
	public void testSingleDice() {
		DiceRoller roller = CampaignClient.getInstance().getDiceRoller();

		TestListener listener = new TestListener(6, 1);
		roller.addPropertyChangeListener(listener);
		roller.roll(new DiceSingle(6));

		while (listener.getCallCount() < 1) {
			sleep(5);
		}
		roller.removePropertyChangeListener(listener);
		assertEquals("Must have 1 call to listener", 1, listener.getCallCount());
	}

	@Test(timeout = 1000)
	public void testDiceBag() {
		DiceRoller roller = CampaignClient.getInstance().getDiceRoller();

		HashMap<Integer, Integer> dices = new HashMap<Integer, Integer>();
		dices.put(6, 3);
		dices.put(8, 2);

		TestListener listener = new TestListener(dices);
		roller.addPropertyChangeListener(listener);

		DiceBag bag = new DiceBag();
		for (Entry<Integer, Integer> entry : dices.entrySet()) {
			for (int i = 0; i < entry.getValue(); ++i) {
				bag.addDice(new DiceSingle(entry.getKey()));
			}
		}
		roller.roll(bag);

		while (listener.getCallCount() < 1) {
			sleep(5);
		}
		roller.removePropertyChangeListener(listener);
		assertEquals("Must have 1 call to listener", 1, listener.getCallCount());
	}

	@Test(timeout = 1000)
	public void testMultipleRoll() {
		DiceRoller roller = CampaignClient.getInstance().getDiceRoller();

		TestListener listener = new TestListener(6, 1);
		roller.addPropertyChangeListener(listener);
		roller.roll(new DiceSingle(6));
		roller.roll(new DiceSingle(6));
		roller.roll(new DiceSingle(6));

		while (listener.getCallCount() < 3) {
			sleep(5);
		}
		roller.removePropertyChangeListener(listener);
		assertEquals("Must have 3 call to listener", 3, listener.getCallCount());
	}

}
