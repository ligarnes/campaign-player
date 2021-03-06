package net.alteiar.beans.media;

import java.io.IOException;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.shared.UniqueID;
import net.alteiar.utils.file.images.TransfertImage;

import org.simpleframework.xml.Element;

public class ImageBean extends BasicBean {
	private static final long serialVersionUID = 1L;

	public static final String PROP_IMAGE_PROPERTY = "image";

	@Element
	private TransfertImage image;

	public ImageBean(TransfertImage image) {
		this.image = image;
	}

	public ImageBean() {
	}

	public TransfertImage getImage() {
		return image;
	}

	public void setImage(TransfertImage image) {
		TransfertImage oldValue = this.image;
		if (notifyRemote(PROP_IMAGE_PROPERTY, oldValue, image)) {
			this.image = image;
			notifyLocal(PROP_IMAGE_PROPERTY, oldValue, image);
		}
	}

	/**
	 * 
	 * @param id
	 * @return the buffered image or null if the document is not found
	 * @throws IOException
	 *             if we are not able to read the image
	 */
	public static TransfertImage getImage(UniqueID id) {
		ImageBean imageBean = CampaignClient.getInstance().getBean(id);
		if (imageBean == null) {
			return null;
		}
		return imageBean.getImage();
	}

}
