package net.alteiar.character;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import net.alteiar.TestClient;
import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.campaign.character.ICharacterSheetClient;
import net.alteiar.pcgen.PathfinderCharacter;

import org.junit.Test;

public class TestCharacter extends TestClient {

	@Test
	public void testCreateManyCharacter() {
		// create character
		for (int i = 0; i < 500; ++i) {
			CampaignClient.INSTANCE.createCharacter(getCharacter1());
		}
		int count = CampaignClient.INSTANCE.getAllCharacter().length;
		int previousCount = -1;
		while (count != previousCount) {
			previousCount = count;
			sleep();
			count = CampaignClient.INSTANCE.getAllCharacter().length;
		}
		assertEquals("tous les personnages n'ont pas était créer", 500,
				CampaignClient.INSTANCE.getAllCharacter().length);

		removeAllCharacter();
	}

	@Test
	public void testRemoveCharacter() {
		// create character
		CampaignClient.INSTANCE.createCharacter(getCharacter1());
		sleep();

		ICharacterSheetClient found = findCharacter(getCharacter1().getName());
		assertNotNull("Le personnage n'a pas était créer", found);

		// full compare
		fullTestCharacter(getCharacter1(), found);

		// remove character
		CampaignClient.INSTANCE.removeCharacter(found);
		sleep();

		found = findCharacter(getCharacter1().getName());
		assertNull("Le personnage n'a pas était supprimer", found);

		removeAllCharacter();
	}

	@Test
	public void testDuplicate() {
		// create duplicate characters
		CampaignClient.INSTANCE.createCharacter(getCharacter1());
		CampaignClient.INSTANCE.createCharacter(getCharacter1());
		CampaignClient.INSTANCE.createCharacter(getCharacter1());
		sleep();

		ICharacterSheetClient found = findCharacter(getCharacter1().getName());
		assertNotNull("Le personnage n'a pas était créer", found);
		found = findCharacter(getCharacter1().getName() + "1");
		assertNotNull("Le personnage n'a pas était créer/renommer", found);
		found = findCharacter(getCharacter1().getName() + "2");
		assertNotNull("Le personnage n'a pas était créer/renommer", found);
		removeAllCharacter();
	}

	@Test
	public void testModify() {
		// modify character
		CampaignClient.INSTANCE.createCharacter(getCharacter1());
		sleep();
		ICharacterSheetClient found = findCharacter(getCharacter1().getName());
		assertNotNull("Le personnage n'a pas était créer", found);

		Integer newHp = found.getCurrentHp() - 5;
		found.setCurrentHp(newHp);
		sleep();
		assertEquals("les points de vie on changer ", found.getCurrentHp(),
				newHp);

		CampaignClient.INSTANCE.removeCharacter(found);
	}

	@Test
	public void testParalleleCharacter() {
		final PathfinderCharacter character1 = getCharacter1();
		// create 200 character
		Runnable create = new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					CampaignClient.INSTANCE.createCharacter(character1);
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

		try {
			tr1.join();
			tr2.join();
			tr3.join();
			tr4.join();
		} catch (InterruptedException e) {
			fail("fail to join all threads");
		}

		int count = CampaignClient.INSTANCE.getAllCharacter().length;
		int previousCount = -1;
		while (count != previousCount) {
			previousCount = count;
			sleep();
			count = CampaignClient.INSTANCE.getAllCharacter().length;
		}
		assertEquals("tous les personnages n'ont pas était créer", 40,
				CampaignClient.INSTANCE.getAllCharacter().length);

		// Remove 200 characters
		tr1 = new Thread(new Remove(0, 10));
		tr2 = new Thread(new Remove(10, 20));
		tr3 = new Thread(new Remove(20, 30));
		tr4 = new Thread(new Remove(30, 40));

		tr1.start();
		tr2.start();
		tr3.start();
		tr4.start();

		try {
			tr1.join();
			tr2.join();
			tr3.join();
			tr4.join();
		} catch (InterruptedException e) {
			fail("fail to join all threads");
		}

		count = CampaignClient.INSTANCE.getAllCharacter().length;
		previousCount = -1;
		while (count != previousCount) {
			previousCount = count;
			sleep();
			count = CampaignClient.INSTANCE.getAllCharacter().length;
		}
		assertEquals("tous les personnages n'ont pas était supprimer", 0,
				CampaignClient.INSTANCE.getAllCharacter().length);
	}

	private class Remove implements Runnable {
		private final int startIdx;
		private final int endIdx;
		private final ICharacterSheetClient[] characters;

		public Remove(int startIdx, int endIdx) {
			this.startIdx = startIdx;
			this.endIdx = endIdx;

			characters = CampaignClient.INSTANCE.getAllCharacter();
		}

		@Override
		public void run() {
			for (int i = startIdx; i < endIdx; ++i) {
				CampaignClient.INSTANCE.removeCharacter(characters[i]);
			}
		}
	}

}
