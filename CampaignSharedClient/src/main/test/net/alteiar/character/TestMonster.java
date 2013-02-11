package net.alteiar.character;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import net.alteiar.TestClient;
import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.campaign.character.ICharacterSheetClient;
import net.alteiar.pcgen.PathfinderCharacter;

import org.junit.Test;

public class TestMonster extends TestClient {

	@Test
	public void testCreateMonster() {
		final PathfinderCharacter character1 = getCharacter1();
		// create character
		CampaignClient.INSTANCE.createMonster(character1);
		sleep();

		ICharacterSheetClient found = findMonster(character1.getName());
		assertNotNull("Le personnage n'a pas était créer", found);

		// full compare
		fullTestCharacter(character1, found);

		// remove character
		CampaignClient.INSTANCE.removeMonster(found);
		sleep();

		found = findMonster(character1.getName());
		assertNull("Le personnage n'a pas était supprimer", found);

		// re create character
		CampaignClient.INSTANCE.createMonster(character1);
		sleep();

		found = findMonster(character1.getName());
		assertNotNull("Le personnage n'a pas était créer", found);

		// create duplicate character
		CampaignClient.INSTANCE.createMonster(character1);
		sleep();

		found = findMonster(character1.getName() + "1");
		assertNotNull("Le personnage n'a pas était créer/renommer", found);
		removeAllMonsters();

		Runnable create = new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 50; i++) {
					CampaignClient.INSTANCE.createMonster(character1);
				}
			}
		};

		Thread tr1 = new Thread(create);
		Thread tr2 = new Thread(create);
		Thread tr3 = new Thread(create);
		Thread tr4 = new Thread(create);

		tr1.start();
		tr2.start();
		tr3.start();
		tr4.start();

		int count = CampaignClient.INSTANCE.getAllMonster().length;
		int previousCount = -1;
		while (count != previousCount) {
			previousCount = count;
			sleep(500);
			count = CampaignClient.INSTANCE.getAllMonster().length;
		}
		assertEquals("tous les personnages n'ont pas était créer", 200,
				CampaignClient.INSTANCE.getAllMonster().length);

		tr1 = new Thread(new Remove(0, 50));
		tr2 = new Thread(new Remove(50, 100));
		tr3 = new Thread(new Remove(100, 150));
		tr4 = new Thread(new Remove(150, 200));

		tr1.start();
		tr2.start();
		tr3.start();
		tr4.start();

		count = CampaignClient.INSTANCE.getAllMonster().length;
		previousCount = -1;
		while (count != previousCount) {
			previousCount = count;
			sleep(500);
			count = CampaignClient.INSTANCE.getAllMonster().length;
		}
		assertEquals("tous les monstres n'ont pas était supprimer", 0,
				CampaignClient.INSTANCE.getAllMonster().length);
	}

	private class Remove implements Runnable {

		private final int startIdx;
		private final int endIdx;

		private final ICharacterSheetClient[] monsters;

		public Remove(int startIdx, int endIdx) {
			this.startIdx = startIdx;
			this.endIdx = endIdx;

			monsters = CampaignClient.INSTANCE.getAllMonster();
		}

		@Override
		public void run() {
			for (int i = startIdx; i < endIdx; ++i) {
				CampaignClient.INSTANCE.removeMonster(monsters[i]);
			}
		}
	}
}
