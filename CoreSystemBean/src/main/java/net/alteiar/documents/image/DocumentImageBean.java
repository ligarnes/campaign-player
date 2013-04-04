package net.alteiar.documents.image;

import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import net.alteiar.CampaignClient;
import net.alteiar.chat.Chat;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.shared.UniqueID;
import net.alteiar.utils.images.TransfertImage;
import net.alteiar.utils.images.SerializableImage;

public class DocumentImageBean extends AuthorizationBean {
	private static final long serialVersionUID = 1L;

	public static final String PROP_IMAGE_PROPERTY = "image";

	private TransfertImage image;

	public DocumentImageBean(TransfertImage image) {
		this.image = image;
	}

	public DocumentImageBean() {
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

	/**
	 * 
	 * @param id
	 * @return the buffered image or null if the document is not found
	 * @throws IOException
	 *             if we are not able to read the image
	 */
	public static BufferedImage getDocumentImage(UniqueID id)
			throws IOException {
		DocumentImageBean imageBean = CampaignClient.getInstance().getBean(id);
		if (imageBean == null) {
			return null;
		}
		return imageBean.getImage().restoreImage();
	}

	@Override
	public void save(File f) throws Exception {
	    ImageIO.write(image.restoreImage(), "png", f);
	    File ImageIDSave=new File(f.getAbsolutePath()+".xml");
	    Serializer serializer = new Persister();
		serializer.write(this, ImageIDSave);
	}

	@Override
	public void loadDocument(File f) throws IOException, Exception {
		image =new SerializableImage(f);
		File ImageIDSave=new File(f.getAbsolutePath()+".xml");
	    Serializer serializer = new Persister();
	    DocumentImageBean temp= serializer.read(DocumentImageBean.class, ImageIDSave);
	    this.setId(temp.getId());
	}
}
