package net.alteiar.client;

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

import net.alteiar.client.bean.BeanEncapsulator;
import net.alteiar.client.bean.image.ImageBean;
import net.alteiar.server.document.DocumentRemote;
import net.alteiar.utils.images.SerializableImage;

public class MainTest {

	/**
	 * @param args
	 * @throws PropertyVetoException
	 * @throws IOException
	 */
	public static void main(String[] args) throws PropertyVetoException,
			IOException {
		ImageBean imageBean = new ImageBean();
		DocumentRemote remoteBeanImage = new DocumentRemote(
				new BeanEncapsulator(imageBean));

		DocumentClient clientImage = remoteBeanImage.buildProxy();
		clientImage.loadDocument();

		ImageBean image = (ImageBean) clientImage.getBean().getBean();
		image.setImage(new SerializableImage(new File(
				"./test/ressources/guerrier.jpg")));

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
