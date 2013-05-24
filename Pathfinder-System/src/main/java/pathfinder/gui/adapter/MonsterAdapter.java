package pathfinder.gui.adapter;

import java.beans.Beans;
import java.util.List;

import pathfinder.bean.unit.monster.PathfinderMonster;
import pathfinder.bean.unit.monster.MonsterManager;

public class MonsterAdapter {
	private final PathfinderMonster monster;

	public MonsterAdapter(PathfinderMonster character) {
		this.monster = character;
	}

	public PathfinderMonster getMonster() {
		return monster;
	}

	@Override
	public String toString() {
		return monster.getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((monster == null) ? 0 : monster.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonsterAdapter other = (MonsterAdapter) obj;
		if (monster == null) {
			if (other.monster != null)
				return false;
		} else if (!monster.equals(other.monster))
			return false;
		return true;
	}

	public static MonsterAdapter[] getMonsters() {
		List<PathfinderMonster> character = MonsterManager.getInstance().getMonsters();
		MonsterAdapter[] characterAdapters = new MonsterAdapter[character
				.size()];

		for (int i = 0; i < characterAdapters.length; i++) {
			characterAdapters[i] = new MonsterAdapter(
					(PathfinderMonster) Beans.getInstanceOf(character.get(i),
							PathfinderMonster.class));
		}

		return characterAdapters;
	}
}