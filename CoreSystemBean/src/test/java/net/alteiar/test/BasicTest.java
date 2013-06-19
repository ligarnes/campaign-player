package net.alteiar.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.documents.BeanDocument;

public abstract class BasicTest {
	/**
	 * timeout is used in test when we try to get a bean
	 */
	protected Long timeout = 500L;

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

	public String getCampaignName() {
		return "general-test";
	}

	public final String getCampaignDirectory() {
		return "./test/ressources/campaign/" + getCampaignName();
	}

	public final String getGlobalDirectory() {
		return "./test/ressources/global/";
	}

	public String getPlayerName() {
		return "player-name";
	}

	public static void deleteRecursive(File base) {
		if (base.listFiles() != null) {
			for (File f : base.listFiles()) {
				deleteRecursive(f);
			}
		}
		base.delete();
	}

	public int countObjectFile(Class<?> classe) {
		String campaignPath = getCampaignDirectory();
		File objectDir = new File(campaignPath, classe.getCanonicalName());

		String[] files = objectDir.list();
		return files != null ? files.length : 0;
	}

	protected Long getTimeout() {
		return timeout;
	}

	protected <E extends BasicBean> E addBean(E bean) {
		CampaignClient.getInstance().addBean(bean);
		return getBeans(bean);
	}

	protected BeanDocument addBean(BeanDocument bean) {
		CampaignClient.getInstance().addBean(bean);
		BeanDocument b = getBeans(bean);
		assertNotNull("The bean should'nt be null", b);

		long begin = System.currentTimeMillis();
		long end = System.currentTimeMillis();
		BasicBean beanRec = b.getBean();
		while (beanRec == null && (end - begin) < getTimeout()) {
			beanRec = b.getBean();
			end = System.currentTimeMillis();
			sleep(50);
		}
		assertNotNull("The internal bean should'nt be null", beanRec);

		return b;
	}

	protected <E extends BasicBean> E getBeans(E bean) {
		return CampaignClient.getInstance().getBean(bean.getId(), getTimeout());
	}
}
