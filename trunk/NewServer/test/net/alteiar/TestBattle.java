package net.alteiar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.images.SerializableImage;
import net.alteiar.server.document.map.battle.BattleClient;

import org.junit.Test;

public class TestBattle extends BasicTest {

	@Test(timeout = 10000)
	public void testCreateBattle() {
		String targetName = "test battle";

		List<BattleClient> current = CampaignClient.getInstance().getBattles();

		int previousSize = current.size();
		try {
			CampaignClient.getInstance().createBattle(
					targetName,
					new SerializableImage(new File(
							"./test/ressources/guerrier.jpg")));
		} catch (IOException e) {
			fail("cannot read file guerrier.jpg");
		}

		int currentSize = current.size();
		while (previousSize == currentSize) {
			current = CampaignClient.getInstance().getBattles();
			currentSize = current.size();

			// Do not remove the force java to evaluate
			System.out.print("");
		}

		BattleClient created = CampaignClient.getInstance().getBattles()
				.get(previousSize);
		assertEquals("Battle name have a wrong name", targetName,
				created.getName());

		// TODO need some more test
	}
}
