package net.alteiar.client.test;

import net.alteiar.server.document.images.TransfertImage;

public class ImageBean extends BasicBeans {
	private static final long serialVersionUID = 1L;

	private transient TransfertImage image;

	public ImageBean() {
	}

	public TransfertImage getImage() {
		return image;
	}

	public void setImage(TransfertImage image) {
		this.propertyChangeSupportRemote.firePropertyChange("image",
				getImage(), image);
	}

	public TransfertImage getLocalimage() {
		return getImage();
	}

	public void setLocalimage(TransfertImage image) {
		TransfertImage oldImage = this.image;
		this.image = image;
		propertyChangeSupportClient.firePropertyChange("image", oldImage,
				this.image);
	}
}
