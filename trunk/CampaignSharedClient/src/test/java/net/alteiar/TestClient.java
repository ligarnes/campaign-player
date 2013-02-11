package net.alteiar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.campaign.battle.IBattleClient;
import net.alteiar.client.shared.campaign.character.ICharacterSheetClient;
import net.alteiar.client.shared.campaign.player.IPlayerClient;
import net.alteiar.pcgen.PathfinderCharacter;
import net.alteiar.thread.TaskInfoAdapter;

import org.junit.After;
import org.junit.Before;

public class TestClient {

	private PathfinderCharacter character1;

	public TestClient() {
		try {
			character1 = CharacterIO.readFile(new File(
					"./test/ressources/character.psr"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public void printInfo() {
		IPlayerClient[] players = CampaignClient.INSTANCE.getAllPlayer();
		ICharacterSheetClient[] characters = CampaignClient.INSTANCE
				.getAllCharacter();
		ICharacterSheetClient[] monsters = CampaignClient.INSTANCE
				.getAllMonster();
		IBattleClient[] battles = CampaignClient.INSTANCE.getBattles();

		System.out.println("======== PLAYERS ========");
		for (IPlayerClient player : players) {
			System.out.println("- " + player.getName());
		}
		System.out.println();
		System.out.println("======== CHARACTERS ========");
		for (ICharacterSheetClient character : characters) {
			System.out.println("- " + character.getName());
		}
		System.out.println();
		System.out.println("======== MONSTERS ========");
		for (ICharacterSheetClient monster : monsters) {
			System.out.println("- " + monster.getName());
		}
		System.out.println();
		System.out.println("======== BATTLES ========");
		for (IBattleClient battle : battles) {
			System.out.println("- " + battle.getName());
		}
		System.out.println();
	}

	@Before
	public void startClient() {
		String address = "127.0.0.1";
		String port = "1099";
		String name = "cody";
		String localAdress = "127.0.0.1";
		Boolean isMj = true;

		CampaignClient.connect(localAdress, address, port, name, isMj);

		CampaignClient.createClientCampaign(5, new TaskInfoAdapter());

	}

	@After
	public void stopClient() {
		CampaignClient.WORKER_POOL_CAMPAIGN.stopWorkers();
		CampaignClient.INSTANCE.disconnect();
	}

	public static ICharacterSheetClient findCharacter(String name) {
		ICharacterSheetClient found = null;
		ICharacterSheetClient[] characters = CampaignClient.INSTANCE
				.getAllCharacter();
		for (ICharacterSheetClient character : characters) {
			if (character.getName().equals(name)) {
				found = character;
				break;
			}
		}

		return found;
	}

	public static ICharacterSheetClient findMonster(String name) {
		ICharacterSheetClient found = null;
		ICharacterSheetClient[] monsters = CampaignClient.INSTANCE
				.getAllMonster();
		for (ICharacterSheetClient monster : monsters) {
			if (monster.getName().equals(name)) {
				found = monster;
				break;
			}
		}

		return found;
	}

	public static IBattleClient findBattle(String name) {
		IBattleClient found = null;
		IBattleClient[] battles = CampaignClient.INSTANCE.getBattles();
		for (IBattleClient battle : battles) {
			if (battle.getName().equals(name)) {
				found = battle;
				break;
			}
		}
		return found;
	}

	public static void removeAllCharacter() {
		ICharacterSheetClient[] characters = CampaignClient.INSTANCE
				.getAllCharacter();

		for (ICharacterSheetClient character : characters) {
			CampaignClient.INSTANCE.removeCharacter(character);
		}
		while (CampaignClient.INSTANCE.getAllCharacter().length > 0) {
			sleep();
		}
	}

	public static void removeAllMonsters() {
		ICharacterSheetClient[] monsters = CampaignClient.INSTANCE
				.getAllMonster();

		for (ICharacterSheetClient monster : monsters) {
			CampaignClient.INSTANCE.removeMonster(monster);
		}
		while (CampaignClient.INSTANCE.getAllMonster().length > 0) {
			sleep();
		}
	}

	public static void removeAllBattles() {
		IBattleClient[] battles = CampaignClient.INSTANCE.getBattles();

		for (IBattleClient battle : battles) {
			CampaignClient.INSTANCE.removeBattle(battle);
		}
		while (CampaignClient.INSTANCE.getBattles().length > 0) {
			sleep();
		}
	}

	public void fullTestCharacter(PathfinderCharacter character,
			ICharacterSheetClient client) {

		assertEquals("le nom doit être identique", character.getName(),
				client.getName());
		assertEquals("la ca doit être identique", character.getAc(),
				client.getAc());
		assertEquals("la ca au depourvu doit être identique",
				character.getAcFlatFooted(), client.getAcFlatFooted());
		assertEquals("la ca de contact doit être identique",
				character.getAcTouch(), client.getAcTouch());

		assertEquals("les pv actuel doivent être identique",
				character.getCurrentHp(), client.getCurrentHp());
		assertEquals("les pv totaux doivent être identique", character.getHp(),
				client.getTotalHp());

		assertEquals("la hauteur doit être identique",
				Double.valueOf(character.getHeight().doubleValue()),
				client.getHeight());
		assertEquals("la largueur doit être identique",
				Double.valueOf(character.getWidth().doubleValue()),
				client.getWidth());
		assertEquals("le modificateur d'init doit être identique",
				character.getInitMod(), client.getInitModifier());
	}

	public static void sleep(Integer time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			fail("fail to sleep");
		}
	}

	public static void sleep() {
		sleep(500);
	}

	public PathfinderCharacter getCharacter1() {
		return character1;
	}
}
