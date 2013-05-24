package pathfinder.bean.unit.monster;

import java.util.ArrayList;

import net.alteiar.CampaignClient;

public class MonsterManager {

	private static MonsterManager INSTANCE = new MonsterManager();

	public static MonsterManager getInstance() {
		return INSTANCE;
	}

	private final ArrayList<PathfinderMonster> allMonsters;

	private MonsterManager() {
		allMonsters = CampaignClient.getInstance().loadLocalBean(PathfinderMonster.class);
	}

	public ArrayList<PathfinderMonster> getMonsters() {
		return allMonsters;
	}
}
