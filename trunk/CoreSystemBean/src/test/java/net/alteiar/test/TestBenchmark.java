package net.alteiar.test;

import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.media.ImageBean;
import net.alteiar.shared.UniqueID;
import net.alteiar.utils.file.images.SerializableImage;
import net.alteiar.utils.file.images.WebImage;

import org.junit.After;
import org.junit.Test;

public class TestBenchmark extends NewCampaignTest {
	private class Timer {
		private Long begin;

		public void start() {
			begin = System.currentTimeMillis();
		}

		public void end(String msg) {
			System.out.println(msg + " Take :"
					+ (System.currentTimeMillis() - begin) + "ms");
		}
	}

	private static long time10second = 10000l;
	private static long time30second = 30000l;

	@Test
	public void testBenchmarkMediumImages() {
		try {
			Timer t = new Timer();
			t.start();
			ImageBean bean = new ImageBean(new WebImage(new URL(
					"http://www.alteiar.net/images/cartes/Carte_du_monde.jpg")));
			UniqueID id = bean.getId();

			CampaignClient.getInstance().addBean(bean);
			t.end("server sended");
			bean = CampaignClient.getInstance().getBean(id, time10second);
			if (bean != null) {
				BufferedImage img = bean.getImage().restoreImage();
				t.end("server received  3.81 Mo (" + img.getWidth() + "x"
						+ img.getHeight() + ") ");
			} else {
				System.out.println("server take more than 10 second");
			}

			t.start();
			CampaignClient.getInstance().addBean(
					new ImageBean(new SerializableImage(new File(
							"./test/ressources/medium.jpg"))));
			t.end("transfert sended");
			bean = CampaignClient.getInstance().getBean(id, time10second);
			if (bean != null) {
				BufferedImage img = bean.getImage().restoreImage();
				t.end("transfert received 3.81 Mo (" + img.getWidth() + "x"
						+ img.getHeight() + ") ");
			} else {
				System.out.println("transfert take more than 10 second");
			}
		} catch (IOException e) {
			fail("file not found");
		}
	}

	@Test
	public void testBenchmarkLargeImages() {
		try {
			Timer t = new Timer();
			t.start();
			ImageBean bean = new ImageBean(new WebImage(new URL(
					"http://www.alteiar.net/MyUpload/large.jpg")));
			UniqueID id = bean.getId();

			CampaignClient.getInstance().addBean(bean);
			t.end("server sended");
			bean = CampaignClient.getInstance().getBean(id, time30second);
			if (bean != null) {
				BufferedImage img = bean.getImage().restoreImage();
				t.end("server received 11.8 Mo (" + img.getWidth() + "x"
						+ img.getHeight() + ") ");
			} else {
				System.out.println("server take more than 10 second");
			}

			t.start();
			CampaignClient.getInstance().addBean(
					new ImageBean(new SerializableImage(new File(
							"./test/ressources/large.jpg"))));
			t.end("transfert sended");
			bean = CampaignClient.getInstance().getBean(id, time30second);
			if (bean != null) {
				BufferedImage img = bean.getImage().restoreImage();
				t.end("transfert received 11.8Mo (" + img.getWidth() + "x"
						+ img.getHeight() + ") ");
			} else {
				System.out.println("transfert take more than 10 second");
			}
		} catch (IOException e) {
			fail("file not found");
		}
	}

	@Override
	@After
	public void afterTest() {
		// DO NOT SAVE TO HEAVY IMAGES
		// CampaignFactory.leaveGame();
		System.out.println("tearing down");
	}
}
