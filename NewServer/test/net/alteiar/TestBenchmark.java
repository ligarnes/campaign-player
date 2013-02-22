package net.alteiar;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.files.DocumentImageBuilder;
import net.alteiar.shared.SerializableImage;
import net.alteiar.shared.WebImage;

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
			DocumentClient<?> doc = null;
			Timer t = new Timer();
			t.start();
			Long id = CampaignClient
					.getInstance()
					.createDocument(
							new DocumentImageBuilder(
									new WebImage(
											"http://www.alteiar.net/images/cartes/Carte_du_monde.jpg")));
			doc = CampaignClient.getInstance().getDocument(id, time10second);
			if (doc != null) {
				t.end("server");
			} else {
				System.out.println("server take more than 10 second");
			}

			t.start();
			CampaignClient.getInstance().createDocument(
					new DocumentImageBuilder("./test/ressources/medium.jpg"));
			doc = CampaignClient.getInstance().getDocument(id, time10second);
			if (doc != null) {
				t.end("local");
			} else {
				System.out.println("local take more than 1 second");
			}

			t.start();
			CampaignClient.getInstance().createDocument(
					new DocumentImageBuilder(new SerializableImage(new File(
							"./test/ressources/medium.jpg"))));
			doc = CampaignClient.getInstance().getDocument(id, time10second);
			if (doc != null) {
				t.end("transfert");
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
			DocumentClient<?> doc = null;
			Timer t = new Timer();
			t.start();
			Long id = CampaignClient.getInstance().createDocument(
					new DocumentImageBuilder(new WebImage(
							"http://www.alteiar.net/MyUpload/large.jpg")));
			doc = CampaignClient.getInstance().getDocument(id, time30second);
			if (doc != null) {
				t.end("server");
			} else {
				System.out.println("server take more than 10 second");
			}

			t.start();
			CampaignClient.getInstance().createDocument(
					new DocumentImageBuilder("./test/ressources/large.jpg"));
			doc = CampaignClient.getInstance().getDocument(id, time30second);
			if (doc != null) {
				t.end("local");
			} else {
				System.out.println("local take more than 10 second");
			}

			t.start();
			CampaignClient.getInstance().createDocument(
					new DocumentImageBuilder(new SerializableImage(new File(
							"./test/ressources/large.jpg"))));
			doc = CampaignClient.getInstance().getDocument(id, time30second);
			if (doc != null) {
				t.end("transfert");
			} else {
				System.out.println("transfert take more than 10 second");
			}
		} catch (IOException e) {
			fail("file not found");
		}
	}
}
