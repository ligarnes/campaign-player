package net.alteiar.documents;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Iterator;

public abstract class AuthorizationAdapter implements PropertyChangeListener {

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

		return properties;
	}

	public AuthorizationAdapter() {
		properties = initializeProperties();
	}

	public AuthorizationAdapter(AuthorizationBean bean) {
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
				authorizationChanged(evt);
			}
		}
	}

	public abstract void authorizationChanged(PropertyChangeEvent evt);
}
