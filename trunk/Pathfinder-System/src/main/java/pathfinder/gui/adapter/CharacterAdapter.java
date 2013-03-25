package pathfinder.gui.adapter;

import java.beans.Beans;
import java.util.List;

import net.alteiar.CampaignClient;
import net.alteiar.character.CharacterBean;
import pathfinder.character.PathfinderCharacter;

public class CharacterAdapter {
	private final PathfinderCharacter character;

	public CharacterAdapter(PathfinderCharacter character) {
		this.character = character;
	}

	public PathfinderCharacter getCharacter() {
		return character;
	}

	@Override
	public String toString() {
		return character.getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((character == null) ? 0 : character.hashCode());
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
		CharacterAdapter other = (CharacterAdapter) obj;
		if (character == null) {
			if (other.character != null)
				return false;
		} else if (!character.equals(other.character))
			return false;
		return true;
	}

	public static CharacterAdapter[] getCharacters() {
		List<CharacterBean> character = CampaignClient.getInstance()
				.getCharacters();
		CharacterAdapter[] characterAdapters = new CharacterAdapter[character
				.size()];

		for (int i = 0; i < characterAdapters.length; i++) {
			characterAdapters[i] = new CharacterAdapter(
					(PathfinderCharacter) Beans.getInstanceOf(character.get(i),
							PathfinderCharacter.class));
		}

		return characterAdapters;
	}
}