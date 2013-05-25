package pathfinder.bean.unit.monster;

import java.util.ArrayList;

import net.alteiar.CampaignClient;

public class MonsterManager {

	private static MonsterManager INSTANCE = new MonsterManager();

	public static MonsterManager getInstance() {
		return INSTANCE;
	}

	private final ArrayList<MonsterBuilder> allMonsters;

	private MonsterManager() {
		allMonsters = CampaignClient.getInstance().loadLocalBean(
				MonsterBuilder.class);
	}

	public void addMonsterBuilder(MonsterBuilder builder) {
		allMonsters.add(builder);
		CampaignClient.getInstance().savePerma(builder);
	}

	public ArrayList<MonsterBuilder> getMonsters() {
		return allMonsters;
	}
}
