package net.alteiar.test.map;

import static org.junit.Assert.fail;

import java.io.IOException;

import net.alteiar.server.document.files.FastNoise;
import net.alteiar.test.BasicTest;

import org.junit.Test;

public class TestNoise extends BasicTest {

	@Test
	public void testFastNoise() {
		try {
			compareImage(FastNoise.generateImage(500, 500),
					FastNoise.generateImage(500, 500));
		} catch (IOException e) {
			fail("fail to compare noisy image");
		}
	}
}
