package generic.bean.combat;

import generic.bean.unit.Unit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class CombatTracker {
	// TODO move this class to the right place ????

	private final TreeMap<Initiative, Unit> units;

	public CombatTracker() {
		units = new TreeMap<Initiative, Unit>();

	}

	public void addUnit(Unit unit, Initiative init) {
		units.put(init, unit);
	}

	public Collection<Unit> getUnits() {
		return units.values();
	}

	private static Set<Integer> lucks = new HashSet<Integer>();

	public class Initiative implements Comparable<Initiative> {

		private final Integer value;
		private final Integer mod;
		private Integer luck;

		public Initiative(Unit character, Integer value) {
			this(value, character.getInitMod());
		}

		public Initiative(Integer value, Integer mod) {
			this.value = value;
			this.mod = mod;

			Random rand = new Random();
			luck = rand.nextInt();
			boolean alreadyContain = lucks.add(luck);
			while (alreadyContain) {
				luck = rand.nextInt();
				alreadyContain = lucks.add(luck);
			}
		}

		@Override
		public int compareTo(Initiative o) {
			int diff = value - o.value;
			if (diff == 0) {
				diff = mod - o.mod;
			}
			if (diff == 0) {
				diff = luck - o.luck;
			}
			return diff;
		}
	}
}
