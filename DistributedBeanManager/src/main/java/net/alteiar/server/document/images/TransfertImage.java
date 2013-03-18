package net.alteiar.server.document.images;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

public interface TransfertImage extends Serializable {

	BufferedImage restoreImage() throws IOException;
}
