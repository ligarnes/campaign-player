package net.alteiar.documents;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Iterator;

public abstract class DocumentAdapter implements PropertyChangeListener {

	private final HashSet<String> properties;

	private HashSet<String> initializeProperties() {
		HashSet<String> properties = new HashSet<String>();

		properties.add(AuthorizationBean.PROP_PUBLIC_PROPERTY);
		properties.add(AuthorizationBean.PROP_OWNER_PROPERTY);
		properties.add(AuthorizationBean.PROP_MODIFIERS_PROPERTY);
		properties.add(AuthorizationBean.PROP_USERS_PROPERTY);
		properties.add(AuthorizationBean.METH_ADD_MODIFIER_METHOD);
		properties.add(AuthorizationBean.METH_ADD_USER_METHOD);
		properties.add(AuthorizationBean.METH_REMOVE_MODIFIER_METHOD);
		properties.add(AuthorizationBean.METH_REMOVE_USER_METHOD);

		properties.add(BeanBasicDocument.PROP_DOCUMENT_NAME_PROPERTY);
		properties.add(BeanBasicDocument.PROP_PARENT_PROPERTY);
		properties.add(BeanDirectory.METH_ADD_DOCUMENT_METHOD);
		properties.add(BeanDirectory.METH_REMOVE_DOCUMENT_METHOD);

		return properties;
	}

	public DocumentAdapter() {
		properties = initializeProperties();
	}

	public DocumentAdapter(AuthorizationBean bean) {
		properties = initializeProperties();

		for (String prop : properties) {
			bean.addPropertyChangeListener(prop, this);
		}
	}

	@Override
	public final void propertyChange(PropertyChangeEvent evt) {
		boolean auth = false;

		Iterator<String> itt = properties.iterator();
		while (!auth && itt.hasNext()) {
			if (itt.next().equals(evt.getPropertyName())) {
				auth = true;
			}
		}

		if (auth) {
			documentChanged(evt);
		}
	}

	public abstract void documentChanged(PropertyChangeEvent evt);
}
