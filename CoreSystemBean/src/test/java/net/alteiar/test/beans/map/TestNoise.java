package net.alteiar.test.beans.map;

import static org.junit.Assert.fail;

import java.io.IOException;

import net.alteiar.test.BasicTest;
import net.alteiar.utils.FastNoise;

import org.junit.Test;

public class TestNoise extends BasicTest {

	@Test
	public void testFastNoise() {
		try {
			compareImage(FastNoise.generateImage(20, 20),
					FastNoise.generateImage(20, 20));
		} catch (IOException e) {
			fail("fail to compare noisy image");
		}
	}
}
