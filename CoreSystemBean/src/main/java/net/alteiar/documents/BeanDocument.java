package net.alteiar.documents;

import java.beans.PropertyChangeListener;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;

public class BeanDocument extends AuthorizationBean {
	private static final long serialVersionUID = 1L;

	public static final String PROP_BEAN_PROPERTY = "bean";

	public static final String PROP_DOCUMENT_NAME_PROPERTY = "documentName";

	@Element
	private String documentName;

	@Element
	private UniqueID beanId;

	@Element
	private String documentType;

	public BeanDocument() {
	}

	public BeanDocument(String name, String type, final BasicBean bean) {
		super();
		this.documentName = name;
		this.beanId = bean.getId();
		this.documentType = type;

		CampaignClient.getInstance().addBean(bean);
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

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		String oldValue = documentName;
		if (notifyRemote(PROP_DOCUMENT_NAME_PROPERTY, oldValue, documentName)) {
			this.documentName = documentName;
			notifyLocal(PROP_DOCUMENT_NAME_PROPERTY, oldValue, documentName);
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
		return "BeanDocument [documentName=" + documentName + ", documentType="
				+ documentType + "]";
	}
}
