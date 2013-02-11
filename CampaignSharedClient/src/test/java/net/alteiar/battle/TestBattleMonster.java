package net.alteiar.battle;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.Point;
import java.io.File;

import net.alteiar.TestClient;
import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.campaign.battle.IBattleClient;
import net.alteiar.client.shared.campaign.character.ICharacterSheetClient;
import net.alteiar.server.shared.campaign.battle.map.Scale;
import net.alteiar.server.shared.campaign.battle.map.element.size.MapElementSizeSquare;

import org.junit.Test;

public class TestBattleMonster extends TestClient {

	private static final File SMALL_MAP = new File(
			"./test/ressources/small.jpg");

	@Test
	public void testAddManyMonster() {
		CampaignClient.INSTANCE.createBattle("battle", SMALL_MAP, new Scale(25,
				1.5));
		sleep(5000);

		IBattleClient battle = CampaignClient.INSTANCE.getBattles()[0];
		battle.addCircle(new MapElementSizeSquare(5.0), Color.RED, new Point(
				10, 10));
		sleep();

		CampaignClient.INSTANCE.createMonster(getCharacter1());
		sleep();

		ICharacterSheetClient monster = CampaignClient.INSTANCE.getAllMonster()[0];

		for (int i = 0; i < 500; i++) {
			battle.addMonster(monster, 5, true, new Point(55, 55));
		}
		sleep();

		int count = battle.getAllCharacter().length;
		int previousCount = -1;
		while (count != previousCount) {
			previousCount = count;
			sleep();
			count = battle.getAllCharacter().length;
		}
		assertEquals("tous les personnages n'ont pas était ajouter", 500,
				battle.getAllCharacter().length);

		removeAllCharacter();
		removeAllBattles();
	}

	@Test
	public void testConcurrentAddMonster() {

	}
}
