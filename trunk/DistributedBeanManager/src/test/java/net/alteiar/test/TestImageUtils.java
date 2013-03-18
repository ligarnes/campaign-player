package net.alteiar.test;

import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.alteiar.shared.ImageUtil;

import org.junit.Test;

public class TestImageUtils extends BasicTest {

	@Test
	public void testImageResize() {
		try {
			// very small
			BufferedImage first = ImageUtil.resizeImage(
					ImageIO.read(new File("./test/ressources/guerrier.jpg")),
					5, 5);
			BufferedImage second = ImageUtil.resizeImage(
					ImageIO.read(new File("./test/ressources/guerrier.jpg")),
					5, 5);

			compareImage(first, second);

			// large
			first = ImageUtil.resizeImage(
					ImageIO.read(new File("./test/ressources/guerrier.jpg")),
					555, 555);
			second = ImageUtil.resizeImage(
					ImageIO.read(new File("./test/ressources/guerrier.jpg")),
					555, 555);

			compareImage(first, second);
		} catch (IOException e) {
			fail("fail to resize image");
		}
	}
}
