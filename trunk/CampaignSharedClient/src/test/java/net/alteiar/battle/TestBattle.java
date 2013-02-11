package net.alteiar.battle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.Point;
import java.io.File;

import net.alteiar.TestClient;
import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.campaign.battle.IBattleClient;
import net.alteiar.client.shared.campaign.battle.character.ICharacterCombatClient;
import net.alteiar.client.shared.campaign.character.ICharacterSheetClient;
import net.alteiar.client.shared.campaign.map.element.IMapElement;
import net.alteiar.server.shared.campaign.battle.map.Scale;
import net.alteiar.server.shared.campaign.battle.map.element.size.MapElementSizeSquare;

import org.junit.Test;

public class TestBattle extends TestClient {

	private static final File LARGE_MAP = new File(
			"./test/ressources/large.jpg");
	private static final File SMALL_MAP = new File(
			"./test/ressources/small.jpg");

	public void compareCharacters(ICharacterSheetClient character,
			ICharacterCombatClient characterCombat) {

		assertEquals("", character.getName(), characterCombat.getCharacter()
				.getName());

		assertEquals("", character.getCurrentHp(), characterCombat
				.getCharacter().getCurrentHp());
	}

	@Test
	public void testCompleteBattle() {
		CampaignClient.INSTANCE.createBattle("battle", SMALL_MAP, new Scale(25,
				1.5));
		sleep();

		IBattleClient battle = CampaignClient.INSTANCE.getBattles()[0];
		battle.addCircle(new MapElementSizeSquare(5.0), Color.RED, new Point(
				10, 10));
		sleep();

		IMapElement element = battle.getElementAt(new Point(12, 12));

		assertEquals("les positions doivent etre egals", new Point(10, 10),
				element.getPosition());
		assertEquals("les angles doivent etre egals", Double.valueOf(0.0),
				element.getAngle());

		element.setAngle(45.0);
		element.applyRotate();
		sleep();
		assertEquals("les angles doivent etre egals", Double.valueOf(45.0),
				element.getAngle());

		CampaignClient.INSTANCE.createCharacter(getCharacter1());
		sleep();

		ICharacterSheetClient character = CampaignClient.INSTANCE
				.getAllCharacter()[0];
		battle.addCharacter(character, 5, new Point(55, 55));
		sleep();

		ICharacterCombatClient characterCombat = battle.getAllCharacter()[0];
		compareCharacters(character, characterCombat);

		Integer expectedHp = character.getCurrentHp() - 5;
		characterCombat.doDamage(5);
		sleep();

		compareCharacters(character, characterCombat);
		assertEquals("", character.getCurrentHp(), expectedHp);

		battle.removeCharacter(characterCombat);

		removeAllCharacter();
		removeAllBattles();
	}

	@Test
	public void testCreateBattlesDuplicate() {
		// create duplicate battle
		CampaignClient.INSTANCE.createBattle("battle", SMALL_MAP, new Scale(25,
				1.5));
		CampaignClient.INSTANCE.createBattle("battle", SMALL_MAP, new Scale(25,
				1.5));
		CampaignClient.INSTANCE.createBattle("battle", SMALL_MAP, new Scale(25,
				1.5));
		sleep();
		sleep();
		sleep();
		sleep();
		sleep();

		IBattleClient first = findBattle("battle");
		IBattleClient duplique1 = findBattle("battle" + "1");
		IBattleClient duplique2 = findBattle("battle" + "2");
		assertNotNull("le combat n'a pas était crée", first);
		assertNotNull("le combat n'a pas était dupliqué", duplique1);
		assertNotNull("le combat n'a pas était dupliqué une seconde fois",
				duplique2);

		removeAllBattles();
	}

	@Test
	public void testRemoveBattles() {
		// create battle
		CampaignClient.INSTANCE.createBattle("battle", LARGE_MAP, new Scale(25,
				1.5));
		sleep(5000);
		IBattleClient found = findBattle("battle");
		assertNotNull("le combat n'a pas était crée", found);

		// remove battle
		CampaignClient.INSTANCE.removeBattle(found);
		sleep();
		found = findBattle("battle");
		assertNull("le combat n'a pas était supprimer", found);

		removeAllBattles();
	}

	@Test
	public void testMultipleCreateBattles() {
		Thread tr1 = new Thread(new CreateBattle());
		Thread tr2 = new Thread(new CreateBattle());
		Thread tr3 = new Thread(new CreateBattle());
		Thread tr4 = new Thread(new CreateBattle());

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

		int count = CampaignClient.INSTANCE.getBattles().length;
		int previousCount = -1;
		while (count != previousCount) {
			previousCount = count;
			sleep();
			count = CampaignClient.INSTANCE.getBattles().length;
		}
		assertEquals("tous les combats n'ont pas était créer", 40,
				CampaignClient.INSTANCE.getBattles().length);

		tr1 = new Thread(new RemoveBattle(0, 10));
		tr2 = new Thread(new RemoveBattle(10, 20));
		tr3 = new Thread(new RemoveBattle(20, 30));
		tr4 = new Thread(new RemoveBattle(30, 40));

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

		count = CampaignClient.INSTANCE.getBattles().length;
		previousCount = -1;
		while (count != previousCount) {
			previousCount = count;
			sleep(500);
			count = CampaignClient.INSTANCE.getBattles().length;
		}
		assertEquals("tous les combats n'ont pas était supprimer", 0,
				CampaignClient.INSTANCE.getBattles().length);

		removeAllBattles();
	}

	private class CreateBattle implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < 10; ++i) {
				CampaignClient.INSTANCE.createBattle("battleMultiple",
						SMALL_MAP, new Scale(25, 1.5));
			}
		}
	}

	private class RemoveBattle implements Runnable {

		private final int startIdx;
		private final int endIdx;

		private final IBattleClient[] battles;

		public RemoveBattle(int startIdx, int endIdx) {
			this.startIdx = startIdx;
			this.endIdx = endIdx;

			battles = CampaignClient.INSTANCE.getBattles();
		}

		@Override
		public void run() {
			for (int i = startIdx; i < endIdx; ++i) {
				CampaignClient.INSTANCE.removeBattle(battles[i]);
			}
		}
	}

}
