package net.alteiar.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBException;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.character.CompleteCharacter;
import net.alteiar.server.document.character.DocumentCharacterBuilder;
import net.alteiar.server.document.chat.ChatRoomClient;
import net.alteiar.server.document.files.DocumentImageBuilder;
import net.alteiar.server.document.images.WebImage;
import net.alteiar.server.document.player.PlayerClient;

import org.junit.Test;

public class TestCreatePlayer extends BasicTest {
	@Test
	public void testCompareDocument() {
		PlayerClient current = CampaignClient.getInstance().getCurrentPlayer();

		assertTrue("Documents should be equals", current.equals(current));
		assertTrue("Documents should be equals", !current.equals(null));

		ChatRoomClient chat = CampaignClient.getInstance().getChat();
		assertTrue("Documents should not be equals", !current.equals(chat));
	}

	@Test
	public void testCreatePlayer() {
		PlayerClient current = CampaignClient.getInstance().getCurrentPlayer();
		assertNotNull("Current player must exist", current);

		assertEquals("Player names should be same", current.getName(),
				AllTests.getPlayerName());

		assertEquals(CampaignClient.getInstance().getPlayers().size(), 1);
	}

	@Test(timeout = 2000)
	public void testCreateCharacter() {
		try {
			CompleteCharacter character = new CompleteCharacter(new File(
					"./test/ressources/character.psr"));
			int characterCount = CampaignClient.getInstance().getCharacters()
					.size();
			CampaignClient.getInstance().createCharacter(character);

			int currentCount = CampaignClient.getInstance().getCharacters()
					.size();
			while (characterCount == currentCount) {
				sleep(50);
				currentCount = CampaignClient.getInstance().getCharacters()
						.size();
			}
			List<CharacterClient> charaters = CampaignClient.getInstance()
					.getCharacters();
			CharacterClient loaded = charaters.get(characterCount);

			assertEquals("the character NAME should be the same", character
					.getCharacter().getName(), loaded.getName());
			assertEquals("the character AC should be the same", character
					.getCharacter().getAc(), loaded.getAc());

			BufferedImage targetImage;
			try {
				targetImage = character.getImage().restoreImage();
				assertTrue("the character image should be the same",
						compareImage(targetImage, loaded.getImage()));
			} catch (IOException e) {
				fail("enable to load image");
			}

			CampaignClient.getInstance().removeDocument(loaded);
		} catch (FileNotFoundException e) {
			fail("file not found");
		} catch (JAXBException e) {
			fail("not able to parse the character file");
		}
	}

	@Test(timeout = 2000)
	public void testCreateCharacterWebImage() {
		try {
			CompleteCharacter character = new CompleteCharacter(
					new File("./test/ressources/character.psr"),
					new WebImage(
							"http://drakonis.org/uploads/7/v/0/7v0ng7m0e3//2011/10/09/20111009004148-12c8e068.jpg"));
			int characterCount = CampaignClient.getInstance().getCharacters()
					.size();
			CampaignClient.getInstance().createCharacter(character);

			int currentCount = CampaignClient.getInstance().getCharacters()
					.size();
			while (characterCount == currentCount) {
				sleep(100);
				currentCount = CampaignClient.getInstance().getCharacters()
						.size();
			}
			List<CharacterClient> charaters = CampaignClient.getInstance()
					.getCharacters();
			CharacterClient loaded = charaters.get(characterCount);

			assertNotNull("element should be loaded", loaded);

			assertEquals("the character NAME should be the same", character
					.getCharacter().getName(), loaded.getName());
			assertEquals("the character AC should be the same", character
					.getCharacter().getAc(), loaded.getAc());

			assertNotNull("image should be loaded", loaded.getImage());
			BufferedImage targetImage;
			try {
				targetImage = character.getImage().restoreImage();
				assertTrue("the character image should be the same",
						compareImage(targetImage, loaded.getImage()));
			} catch (IOException e) {
				fail("enable to load image");
			}
		} catch (FileNotFoundException e) {
			fail("file not found");
		} catch (JAXBException e) {
			fail("not able to parse the character file");
		}
	}

	@Test(timeout = 2000)
	public void testCreateCharacterLocalImage() {
		try {
			CompleteCharacter character = new CompleteCharacter(new File(
					"./test/ressources/character.psr"));

			Long imageId = CampaignClient.getInstance().createDocument(
					new DocumentImageBuilder("./test/ressources/guerrier.jpg"));

			int characterCount = CampaignClient.getInstance().getCharacters()
					.size();
			CampaignClient.getInstance().createDocument(
					new DocumentCharacterBuilder(character.getCharacter(),
							imageId));

			int currentCount = CampaignClient.getInstance().getCharacters()
					.size();
			while (characterCount == currentCount) {
				sleep(100);
				currentCount = CampaignClient.getInstance().getCharacters()
						.size();
			}
			List<CharacterClient> charaters = CampaignClient.getInstance()
					.getCharacters();
			CharacterClient loaded = charaters.get(characterCount);

			assertEquals("the character NAME should be the same", character
					.getCharacter().getName(), loaded.getName());
			assertEquals("the character AC should be the same", character
					.getCharacter().getAc(), loaded.getAc());

			BufferedImage targetImage;
			try {
				targetImage = ImageIO.read(new File(
						"./test/ressources/guerrier.jpg"));

				assertTrue("the character image should be the same",
						compareImage(targetImage, loaded.getImage()));
			} catch (IOException e) {
				fail("enable to load image");
			}
		} catch (IOException e) {
			fail("file not found");
		} catch (JAXBException e) {
			fail("not able to parse the character file");
		}
	}
}
