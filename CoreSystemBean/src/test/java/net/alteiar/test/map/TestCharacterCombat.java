package net.alteiar.test.map;

import static org.junit.Assert.assertEquals;

import java.util.List;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.map.battle.BattleClient;
import net.alteiar.server.document.map.battle.character.CharacterCombatClient;

import org.junit.Test;

public class TestCharacterCombat extends TestMap {

	@Test
	public void testCreateCharacterCombat() {
		BattleClient battle = createBattle("testCreateCharacterCombat",
				createTransfertImage());

		CharacterClient character = createCharacter();

		List<CharacterCombatClient> charactesCombat = battle
				.getCharacterCombats();

		int previousSize = charactesCombat.size();
		Integer expectedInitiative = 12;
		CampaignClient.getInstance().createCharacterCombat(battle, character,
				expectedInitiative);

		int currentSize = charactesCombat.size();
		while (previousSize == currentSize) {
			charactesCombat = battle.getCharacterCombats();
			currentSize = charactesCombat.size();

			sleep(50);
		}

		CharacterCombatClient characterCombat = charactesCombat
				.get(previousSize);
		assertEquals("Initiative should be equals", expectedInitiative,
				characterCombat.getInitiative());

		compareCharacter(character, characterCombat.getCharacter());

		Integer expectedNewInitiative = 17;
		characterCombat.setInitiative(expectedNewInitiative);
		sleep(100);
		assertEquals("Initiative should be equals", expectedNewInitiative,
				characterCombat.getInitiative());

	}
}
