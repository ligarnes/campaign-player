package net.alteiar.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import net.alteiar.image.ImageBean;
import net.alteiar.shared.ImageUtil;
import net.alteiar.utils.images.WebImage;

import org.junit.Test;

public class TestImageUtils extends BasicTest {

	@Test
	public void testImage() {
		ImageBean image = new ImageBean();
		image.setImage(new WebImage(
				"http://www.alteiar.net/wiki/lib/exe/fetch.php?cache=&media=applications:chat.jpg"));
		try {
			BufferedImage target = ImageIO
					.read(new URL(
							"http://www.alteiar.net/wiki/lib/exe/fetch.php?cache=&media=applications:chat.jpg"));

			compareImage(target, image.getImage().restoreImage());
		} catch (MalformedURLException e) {
			fail("problem with the url");
		} catch (IOException e) {
			fail("fail to read images");
		}
	}

	@Test
	public void testImageResize() {
		try {
			// very small
			BufferedImage firstReduce = ImageUtil.resizeImage(
					ImageIO.read(new File("./test/ressources/guerrier.jpg")),
					5, 5);
			BufferedImage secondReduce = ImageUtil.resizeImage(
					ImageIO.read(new File("./test/ressources/guerrier.jpg")),
					5, 5);

			assertTrue("rezising the same image should be same",
					compareImage(firstReduce, secondReduce));

			// large
			BufferedImage firstEnlarge = ImageUtil.resizeImage(
					ImageIO.read(new File("./test/ressources/guerrier.jpg")),
					555, 555);
			BufferedImage secondEnlarge = ImageUtil.resizeImage(
					ImageIO.read(new File("./test/ressources/guerrier.jpg")),
					555, 555);

			assertTrue("resizing the same image should be same",
					compareImage(firstEnlarge, secondEnlarge));

			assertTrue("different resize of an image should be different",
					!compareImage(firstEnlarge, secondReduce));
		} catch (IOException e) {
			fail("fail to resize image");
		}
	}
}
