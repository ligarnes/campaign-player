package pathfinder.map;

import java.util.ArrayList;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.shared.UniqueID;
import pathfinder.character.PathfinderCharacter;

public class Battle extends BasicBeans {
	private static final long serialVersionUID = 1L;

	private Integer turn;

	private final ArrayList<UniqueID> characters;
	private int current;

	public Battle() {
		characters = new ArrayList<UniqueID>();
	}

	public void next() {
		current++;
		if (current >= characters.size()) {
			current = 0;
			turn++;
		}
	}

	public Boolean isMyTurn() {
		PathfinderCharacter character = CampaignClient.getInstance().getBean(
				characters.get(current));
		return character.getOwner().equals(
				CampaignClient.getInstance().getCurrentPlayer().getId());
	}
}
