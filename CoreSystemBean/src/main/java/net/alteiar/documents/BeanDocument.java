package net.alteiar.documents;

import java.beans.PropertyChangeListener;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;

public class BeanDocument extends BeanBasicDocument {
	private static final long serialVersionUID = 1L;

	public static final String PROP_BEAN_PROPERTY = "bean";

	@Element
	private UniqueID beanId;

	@Element
	private String documentType;

	public BeanDocument() {
	}

	public BeanDocument(BeanDirectory directory, String name, String type,
			final BasicBean bean) {
		this(directory.getId(), name, type, bean);
	}

	public BeanDocument(UniqueID directory, String name, String type,
			final BasicBean bean) {
		super(directory, name);
		this.beanId = bean.getId();
		this.documentType = type;

		CampaignClient.getInstance().addBean(bean);
	}

	@Override
	public final boolean isDirectory() {
		return false;
	}

	@Override
	public void beanRemoved() {
		for (PropertyChangeListener listener : propertyChangeSupport
				.getPropertyChangeListeners()) {
			this.removePropertyChangeListener(listener);
		}
		CampaignClient.getInstance().removeBean(getBeanId());
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		String oldValue = this.documentType;
		if (notifyRemote(PROP_DOCUMENT_TYPE_PROPERTY, oldValue, documentType)) {
			this.documentType = documentType;
			notifyLocal(PROP_DOCUMENT_TYPE_PROPERTY, oldValue, documentType);
		}
	}

	public UniqueID getBeanId() {
		return beanId;
	}

	public <E extends BasicBean> E getBean() {
		return CampaignClient.getInstance().getBean(beanId);
	}

	@Override
	public String toString() {
		return "BeanDocument [documentName=" + getDocumentName()
				+ ", documentType=" + documentType + "]";
	}
}
