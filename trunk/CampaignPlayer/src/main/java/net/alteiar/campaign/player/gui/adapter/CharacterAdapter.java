package net.alteiar.campaign.player.gui.adapter;

import java.util.List;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.character.CharacterClient;

public class CharacterAdapter {
	private final CharacterClient character;

	public CharacterAdapter(CharacterClient character) {
		this.character = character;
	}

	public CharacterClient getCharacter() {
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
		List<CharacterClient> character = CampaignClient.getInstance()
				.getCharacters();
		CharacterAdapter[] characterAdapters = new CharacterAdapter[character
				.size()];

		for (int i = 0; i < characterAdapters.length; i++) {
			characterAdapters[i] = new CharacterAdapter(character.get(i));
		}

		return characterAdapters;
	}
}