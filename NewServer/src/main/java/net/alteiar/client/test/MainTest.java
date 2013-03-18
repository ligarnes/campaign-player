package net.alteiar.client.test;

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

import net.alteiar.server.document.images.SerializableImage;

public class MainTest {

	/**
	 * @param args
	 * @throws PropertyVetoException
	 * @throws IOException
	 */
	public static void main(String[] args) throws PropertyVetoException,
			IOException {

		MyFirstTestBean bean = new MyFirstTestBean();
		NewDocumentRemote remote = new NewDocumentRemote(bean);

		NewDocumentClient client = remote.buildProxy();
		client.loadDocument();

		MyFirstTestBean clientBean = (MyFirstTestBean) client.getBean();
		clientBean.setName("test");

		System.out.println("Remote bean value: " + bean.getName()
				+ " | client bean value: " + clientBean.getName());

		ImageBean imageBean = new ImageBean();
		NewDocumentRemote remoteBeanImage = new NewDocumentRemote(imageBean);

		NewDocumentClient clientImage = remoteBeanImage.buildProxy();
		clientImage.loadDocument();

		ImageBean image = (ImageBean) clientImage.getBean();
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
