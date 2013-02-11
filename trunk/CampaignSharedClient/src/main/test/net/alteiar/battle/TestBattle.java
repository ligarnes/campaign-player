package net.alteiar.battle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;

import net.alteiar.TestClient;
import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.campaign.battle.IBattleClient;
import net.alteiar.server.shared.campaign.battle.map.Scale;

import org.junit.Test;

public class TestBattle extends TestClient {

	private static final File LARGE_MAP = new File(
			"./test/ressources/large.jpg");
	private static final File SMALL_MAP = new File(
			"./test/ressources/small.jpg");

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
		sleep(7000);
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
		Thread tr1 = new Thread(new createBattle());
		Thread tr2 = new Thread(new createBattle());
		Thread tr3 = new Thread(new createBattle());
		Thread tr4 = new Thread(new createBattle());

		tr1.start();
		tr2.start();
		tr3.start();
		tr4.start();

		int count = CampaignClient.INSTANCE.getBattles().length;
		int previousCount = -1;
		while (count != previousCount) {
			previousCount = count;
			sleep();
			count = CampaignClient.INSTANCE.getBattles().length;
		}
		assertEquals("tous les combats n'ont pas était créer", 200,
				CampaignClient.INSTANCE.getBattles().length);

		removeAllBattles();
	}

	private class createBattle implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < 50; ++i) {
				CampaignClient.INSTANCE.createBattle("battleMultiple",
						SMALL_MAP, new Scale(25, 1.5));
			}
		}

	}

}
