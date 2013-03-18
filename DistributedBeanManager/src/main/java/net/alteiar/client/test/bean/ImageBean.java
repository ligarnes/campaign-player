package net.alteiar.client.test.bean;

import java.beans.PropertyVetoException;

import net.alteiar.server.document.images.TransfertImage;

public class ImageBean extends BasicBeans {
	private static final long serialVersionUID = 1L;

	public static final String PROP_IMAGE_PROPERTY = "image";
    private  TransfertImage image;

	public ImageBean() {
	}

	public TransfertImage getImage() {
		return image;
	}

	public void setImage(TransfertImage image) {
		TransfertImage oldValue = this.image;
		try {
			vetoableChangeSupport.fireVetoableChange(PROP_IMAGE_PROPERTY, oldValue, image);
			this.image = image;
			propertyChangeSupport.firePropertyChange(PROP_IMAGE_PROPERTY, oldValue, image);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
}
