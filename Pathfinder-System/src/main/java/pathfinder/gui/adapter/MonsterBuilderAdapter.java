package pathfinder.gui.adapter;

import java.beans.Beans;
import java.util.List;

import pathfinder.bean.unit.monster.MonsterBuilder;
import pathfinder.bean.unit.monster.MonsterManager;

public class MonsterBuilderAdapter {
	private final MonsterBuilder monster;

	public MonsterBuilderAdapter(MonsterBuilder character) {
		this.monster = character;
	}

	public MonsterBuilder getMonster() {
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
		MonsterBuilderAdapter other = (MonsterBuilderAdapter) obj;
		if (monster == null) {
			if (other.monster != null)
				return false;
		} else if (!monster.equals(other.monster))
			return false;
		return true;
	}

	public static MonsterBuilderAdapter[] getMonsters() {
		List<MonsterBuilder> character = MonsterManager.getInstance()
				.getMonsters();
		MonsterBuilderAdapter[] characterAdapters = new MonsterBuilderAdapter[character
				.size()];

		for (int i = 0; i < characterAdapters.length; i++) {
			characterAdapters[i] = new MonsterBuilderAdapter(
					(MonsterBuilder) Beans.getInstanceOf(character.get(i),
							MonsterBuilder.class));
		}

		return characterAdapters;
	}
}