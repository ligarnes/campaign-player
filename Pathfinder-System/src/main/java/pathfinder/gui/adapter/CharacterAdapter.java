package pathfinder.gui.adapter;

import java.beans.Beans;
import java.util.ArrayList;
import java.util.List;

import net.alteiar.CampaignClient;
import net.alteiar.documents.character.Character;
import net.alteiar.shared.UniqueID;
import pathfinder.bean.unit.PathfinderCharacter;

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
		List<Character> character = CampaignClient.getInstance()
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

	public static ArrayList<CharacterAdapter> getCharacters(
			ArrayList<UniqueID> ignoreList) {
		ArrayList<CharacterAdapter> adapters = new ArrayList<CharacterAdapter>();

		List<Character> characters = CampaignClient.getInstance()
				.getCharacters();

		for (Character character : characters) {
			if (!ignoreList.contains(character.getId())) {
				adapters.add(new CharacterAdapter((PathfinderCharacter) Beans
						.getInstanceOf(character, PathfinderCharacter.class)));
			}
		}

		return adapters;
	}
}