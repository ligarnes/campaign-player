package net.alteiar.image;

import java.beans.PropertyVetoException;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.utils.images.TransfertImage;

public class ImageBean extends BasicBeans {
	private static final long serialVersionUID = 1L;

	public static final String PROP_IMAGE_PROPERTY = "image";

	private TransfertImage image;

	public ImageBean() {
	}

	public ImageBean(TransfertImage image) {
		this.image = image;
	}

	public TransfertImage getImage() {
		return image;
	}

	public void setImage(TransfertImage image) {
		TransfertImage oldValue = this.image;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(PROP_IMAGE_PROPERTY,
					oldValue, image);
			this.image = image;
			propertyChangeSupport.firePropertyChange(PROP_IMAGE_PROPERTY,
					oldValue, image);
		} catch (PropertyVetoException e) {
			// TODO do nothing, the veto is cause by the framework
			// e.printStackTrace();
		}
	}
}
