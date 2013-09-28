package net.alteiar.utils.file.images;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface TransfertImage {

	BufferedImage restoreImage() throws IOException;

	void clearCache();
}
