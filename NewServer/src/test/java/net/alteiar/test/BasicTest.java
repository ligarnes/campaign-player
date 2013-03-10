package net.alteiar.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.bind.JAXBException;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.character.CompleteCharacter;

public class BasicTest {

	protected CharacterClient createCharacter() {
		List<CharacterClient> current = CampaignClient.getInstance()
				.getCharacters();

		int previousSize = current.size();
		CompleteCharacter character = null;
		try {
			character = new CompleteCharacter(new File(
					"./test/ressources/character.psr"));
		} catch (FileNotFoundException e) {
			fail("character file not found");
		} catch (JAXBException e) {
			fail("character file cannot be parsed");
		}
		CampaignClient.getInstance().createCharacter(character);

		int currentSize = current.size();
		while (previousSize == currentSize) {
			current = CampaignClient.getInstance().getCharacters();
			currentSize = current.size();

			sleep(50);
		}

		return CampaignClient.getInstance().getCharacters().get(previousSize);
	}

	protected void compareCharacter(CharacterClient c1, CharacterClient c2) {
		assertEquals(c1.getAc(), c2.getAc());
		assertEquals(c1.getAcFlatFooted(), c2.getAcFlatFooted());
		assertEquals(c1.getAcTouch(), c2.getAcTouch());
		assertEquals(c1.getCurrentHp(), c2.getCurrentHp());
		assertEquals(c1.getTotalHp(), c2.getTotalHp());
		assertEquals(c1.getName(), c2.getName());
		assertEquals(c1.getWidth(), c2.getWidth());
		assertEquals(c1.getHeight(), c2.getHeight());

		try {
			assertTrue(compareImage(c1.getImage(), c2.getImage()));
		} catch (IOException e) {
			fail("io exception");
		}
	}

	protected Boolean compareImage(BufferedImage img1, BufferedImage img2)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img1, "jpg", baos);
		baos.flush();
		byte[] targetBytes = baos.toByteArray();
		baos.close();

		baos = new ByteArrayOutputStream();
		ImageIO.write(img2, "jpg", baos);
		baos.flush();
		byte[] resultBytes = baos.toByteArray();
		baos.close();

		return Arrays.equals(targetBytes, resultBytes);
	}

	protected void testImage(BufferedImage image, BufferedImage image2) {
		JFrame frm = new JFrame();

		JLabel lbl1 = new JLabel(new ImageIcon(image));
		lbl1.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		JLabel lbl2 = new JLabel(new ImageIcon(image2));
		lbl2.setBorder(BorderFactory.createLineBorder(Color.black, 2));

		frm.add(lbl1, BorderLayout.WEST);
		frm.add(lbl2, BorderLayout.CENTER);
		frm.pack();
		frm.setVisible(true);

		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			fail("not able to sleep");
		}
	}
}
