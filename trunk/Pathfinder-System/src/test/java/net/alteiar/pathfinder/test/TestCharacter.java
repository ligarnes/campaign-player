package net.alteiar.pathfinder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import net.alteiar.beans.media.ImageBean;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.documents.BeanDocument;
import net.alteiar.utils.file.images.SerializableImage;
import net.alteiar.utils.file.images.TransfertImage;

import org.junit.Test;

import pathfinder.DocumentTypeConstant;
import pathfinder.bean.unit.PathfinderCharacter;

public class TestCharacter extends NewCampaignTest {

	public static TransfertImage createTransfertImage(String path) {
		TransfertImage battleImages = null;
		try {
			battleImages = new SerializableImage(new File(path));
		} catch (IOException e) {
			fail("cannot read file " + path);
		}

		return battleImages;
	}

	public static TransfertImage createTransfertImage() {
		return createTransfertImage("./test/ressources/guerrier.jpg");
	}

	public static File getDefaultImage() {
		return new File("./test/ressources/guerrier.jpg");
	}

	public static ImageBean createBeanImage() {
		return new ImageBean(
				createTransfertImage("./test/ressources/guerrier.jpg"));
	}

	@Test
	public void testAttributs() {
		ImageBean img = new ImageBean(createTransfertImage());

		CampaignClient.getInstance().addBean(img);

		Integer totalHp = 32;
		Integer currentHp = 28;
		Integer ac = 15;
		Integer acFlat = 13;
		Integer acTouch = 12;
		Integer initMod = 2;
		PathfinderCharacter character = new PathfinderCharacter("test-name",
				totalHp, currentHp, ac, acTouch, acFlat, initMod, img.getId());

		BeanDocument doc = new BeanDocument(CampaignClient.getInstance()
				.getRootDirectory(), character.getName(),
				DocumentTypeConstant.CHARACTER, character);

		doc = addBean(doc);

		character = doc.getBean();

		assertEquals("Total hp should be same", totalHp, character.getTotalHp());
		assertEquals("Current hp should be same", currentHp,
				character.getCurrentHp());
		assertEquals("ac should be same", ac, character.getAc());
		assertEquals("ac flat footed should be same", acFlat,
				character.getAcFlatFooted());
		assertEquals("ac touch should be same", acTouch, character.getAcTouch());
		assertEquals("init modifier should be same", initMod,
				character.getInitMod());

		totalHp = totalHp - 4;
		currentHp = currentHp - 13;
		ac = ac - 1;
		acFlat = acFlat + 1;
		acTouch = acTouch - 2;
		initMod = initMod - 2;

		character.setTotalHp(totalHp);
		character.setCurrentHp(currentHp);
		character.setAc(ac);
		character.setAcFlatFooted(acFlat);
		character.setAcTouch(acTouch);
		character.setInitMod(initMod);

		sleep(300);

		assertEquals("Total hp should be same", totalHp, character.getTotalHp());
		assertEquals("Current hp should be same", currentHp,
				character.getCurrentHp());
		assertEquals("ac should be same", ac, character.getAc());
		assertEquals("ac flat footed should be same", acFlat,
				character.getAcFlatFooted());
		assertEquals("ac touch should be same", acTouch, character.getAcTouch());
		assertEquals("init modifier should be same", initMod,
				character.getInitMod());
	}
}
