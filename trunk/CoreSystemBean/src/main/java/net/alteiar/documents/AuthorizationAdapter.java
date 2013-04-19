package net.alteiar.documents;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class AuthorizationAdapter implements PropertyChangeListener {

	@Override
	public final void propertyChange(PropertyChangeEvent evt) {
		if (AuthorizationBean.PROP_PUBLIC_PROPERTY
				.equals(evt.getPropertyName())) {
			authorizationChanged(evt);
		} else if (AuthorizationBean.PROP_OWNER_PROPERTY.equals(evt
				.getPropertyName())) {
			authorizationChanged(evt);
		} else if (AuthorizationBean.PROP_MODIFIERS_PROPERTY.equals(evt
				.getPropertyName())) {
			authorizationChanged(evt);
		} else if (AuthorizationBean.PROP_USERS_PROPERTY.equals(evt
				.getPropertyName())) {
			authorizationChanged(evt);
		} else if (AuthorizationBean.METH_ADD_MODIFIER_METHOD.equals(evt
				.getPropertyName())) {
			authorizationChanged(evt);
		} else if (AuthorizationBean.METH_ADD_USER_METHOD.equals(evt
				.getPropertyName())) {
			authorizationChanged(evt);
		} else if (AuthorizationBean.METH_REMOVE_MODIFIER_METHOD.equals(evt
				.getPropertyName())) {
			authorizationChanged(evt);
		} else if (AuthorizationBean.METH_REMOVE_USER_METHOD.equals(evt
				.getPropertyName())) {
			authorizationChanged(evt);
		}
	}

	public void authorizationChanged(PropertyChangeEvent evt) {
	}
}
