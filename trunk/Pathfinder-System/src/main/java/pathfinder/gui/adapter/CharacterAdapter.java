package pathfinder.gui.adapter;

import java.util.ArrayList;

import net.alteiar.CampaignClient;
import net.alteiar.documents.BeanDocument;
import net.alteiar.documents.DocumentType;
import net.alteiar.shared.UniqueID;

public class CharacterAdapter {
	private final BeanDocument character;

	public CharacterAdapter(BeanDocument character) {
		this.character = character;
	}

	public BeanDocument getCharacter() {
		return character;
	}

	@Override
	public String toString() {
		return character.getDocumentName();
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

	public static ArrayList<CharacterAdapter> getCharacters() {
		return getCharacters(new ArrayList<UniqueID>());
	}

	public static ArrayList<CharacterAdapter> getCharacters(
			ArrayList<UniqueID> ignoreList) {
		ArrayList<CharacterAdapter> adapters = new ArrayList<CharacterAdapter>();

		for (BeanDocument character : CampaignClient.getInstance()
				.getDocuments()) {
			if (!ignoreList.contains(character.getId())
					&& character.getDocumentType().equals(
							DocumentType.CHARACTER)) {
				adapters.add(new CharacterAdapter(character));
			}
		}

		return adapters;
	}
}