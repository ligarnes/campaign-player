package net.alteiar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.alteiar.client.DocumentClient;
import net.alteiar.client.DocumentManagerClient;
import net.alteiar.image.ImageBean;
import net.alteiar.server.ServerDocuments;
import net.alteiar.utils.images.SerializableImage;

public class MainTest {

	/**
	 * @param args
	 * @throws PropertyVetoException
	 * @throws IOException
	 */
	public static void main(String[] args) throws PropertyVetoException,
			IOException {

		ServerDocuments.startServer("127.0.0.1", 1099);

		DocumentManagerClient.connect("127.0.0.1", "127.0.0.1", "1099", "", "",
				true);

		ImageBean imageBean = new ImageBean();
		Long idDoc = DocumentManagerClient.INSTANCE.createDocument(imageBean);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DocumentClient clientImage = DocumentManagerClient.INSTANCE
				.getDocument(idDoc);

		ImageBean image = (ImageBean) clientImage.getBean().getBean();

		System.out.println(image.getImage());
		image.setImage(new SerializableImage(new File(
				"./test/ressources/guerrier.jpg")));

		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		testImage(image.getImage().restoreImage());
	}

	protected static void testImage(BufferedImage image) {
		JFrame frm = new JFrame();

		JLabel lbl1 = new JLabel(new ImageIcon(image));
		lbl1.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		frm.add(lbl1, BorderLayout.WEST);
		frm.pack();
		frm.setVisible(true);

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
}
