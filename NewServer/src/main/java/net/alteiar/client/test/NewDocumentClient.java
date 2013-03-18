package net.alteiar.client.test;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentPath;

public class NewDocumentClient implements PropertyChangeListener {

	private final INewDocumentRemote remote;
	private transient INewDocumentRemoteListener documentListener;

	private final DocumentPath documentPath;

	private BasicBeans bean;

	public NewDocumentClient(INewDocumentRemote remote) throws RemoteException {
		this.remote = remote;
		this.documentPath = this.remote.getPath();

	}

	public void remoteValueChanged(String propertyName, Object newValue) {
		Method setter;
		try {
			setter = new PropertyDescriptor(propertyName, bean.getClass())
					.getWriteMethod();
			setter.invoke(bean, newValue);
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		try {
			remote.setBeanValue("local" + evt.getPropertyName(),
					evt.getNewValue());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Long getId() {
		return documentPath.getId();
	}

	public BasicBeans getBean() {
		return this.bean;
	}

	protected DocumentPath getDocumentPath() {
		return this.documentPath;
	}

	private final void remoteCloseDocument() {
		try {
			this.remote.removeDocumentListener(documentListener);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadDocument() {
		try {
			documentListener = new INewDocumentRemoteListener() {
				@Override
				public void beanValueChanged(String propertyName,
						Object newValue) throws RemoteException {
					remoteValueChanged(propertyName, newValue);
				}

				@Override
				public void documentClosed() {
					remoteCloseDocument();
				}
			};

			this.remote.addDocumentListener(documentListener);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// localPath = the local scenario directory
		// path = localPath + getDocumentPath().getCompletePath();
		File localFile = new File(getDocumentPath().getCompletePath());
		try {
			if (localFile.exists()) {
				// load local bean
				loadDocumentLocal(localFile);
			} else {
				// load remote bean
				bean = this.remote.getBean();
			}
			bean.addPropertyChangeListener(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void loadDocumentLocal(File f) throws IOException {

	}

	protected void loadDocumentRemote() throws IOException {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((documentPath == null) ? 0 : documentPath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewDocumentClient other = (NewDocumentClient) obj;
		if (documentPath == null) {
			if (other.documentPath != null)
				return false;
		} else if (!documentPath.equals(other.documentPath))
			return false;
		return true;
	}
}
