package net.alteiar.test;

import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import net.alteiar.CampaignClient;
import net.alteiar.image.ImageBean;
import net.alteiar.utils.images.SerializableImage;
import net.alteiar.utils.images.WebImage;

import org.junit.Test;

public class TestBenchmark {
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
			Long id = CampaignClient
					.getInstance()
					.addBean(
							new ImageBean(
									new WebImage(
											"http://www.alteiar.net/images/cartes/Carte_du_monde.jpg")));
			t.end("server sended");
			ImageBean bean = CampaignClient.getInstance().getBean(id,
					time10second);
			if (bean != null) {
				BufferedImage img = bean.getImage().restoreImage();
				t.end("server received  3.81 Mo (" + img.getWidth() + "x"
						+ img.getHeight() + ") ");
			} else {
				System.out.println("server take more than 10 second");
			}
			/*
			 * t.start(); CampaignClient.getInstance().addBean( new
			 * DocumentImageBuilder("./test/ressources/medium.jpg"));
			 * t.end("local sended"); doc =
			 * CampaignClient.getInstance().getDocument(id, time10second); if
			 * (doc != null) { t.end("local received"); } else {
			 * System.out.println("local take more than 1 second"); }
			 */

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
			Long id = CampaignClient.getInstance().addBean(
					new ImageBean(new WebImage(
							"http://www.alteiar.net/MyUpload/large.jpg")));
			t.end("server sended");
			ImageBean bean = CampaignClient.getInstance().getBean(id,
					time30second);
			if (bean != null) {
				BufferedImage img = bean.getImage().restoreImage();
				t.end("server received 11.8 Mo (" + img.getWidth() + "x"
						+ img.getHeight() + ") ");
			} else {
				System.out.println("server take more than 10 second");
			}

			/*
			 * t.start(); CampaignClient.getInstance().createDocument( new
			 * DocumentImageBuilder("./test/ressources/large.jpg"));
			 * t.end("local sended"); doc =
			 * CampaignClient.getInstance().getDocument(id, time30second); if
			 * (doc != null) { t.end("local received"); } else {
			 * System.out.println("local take more than 10 second"); }
			 */

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
}
