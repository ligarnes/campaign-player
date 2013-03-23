package net.alteiar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.alteiar.chat.message.MessageRemote;
import net.alteiar.image.ImageBean;
import net.alteiar.utils.images.SerializableImage;

public class MainTest {

	/**
	 * @param args
	 * @throws PropertyVetoException
	 * @throws IOException
	 */
	public static void main(String[] args) throws PropertyVetoException,
			IOException {

		CampaignClient.startServer("127.0.0.1", "1099");

		CampaignClient.connect("127.0.0.1", "127.0.0.1", "1099", "", "pseudo",
				true);

		CampaignClient.getInstance().getChat().talk("hello world");
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<MessageRemote> msg = CampaignClient.getInstance().getChat()
				.getMessages();
		System.out.println("last message received: "
				+ msg.get(msg.size() - 1).getMessage());
		System.out.println("exit");
		System.exit(0);

		ImageBean imageBean = new ImageBean();
		Long idDoc = CampaignClient.getInstance().addBean(imageBean);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ImageBean image = CampaignClient.getInstance().getBean(idDoc);

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
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
