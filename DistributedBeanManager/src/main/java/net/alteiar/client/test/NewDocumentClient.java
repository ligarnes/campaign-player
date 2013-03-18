package net.alteiar.client.test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentPath;

public class NewDocumentClient implements PropertyChangeListener {

	private final INewDocumentRemote remote;
	private transient INewDocumentRemoteListener documentListener;

	private final DocumentPath documentPath;

	private BeanEncapsulator bean;

	public NewDocumentClient(INewDocumentRemote remote) throws RemoteException {
		this.remote = remote;
		this.documentPath = this.remote.getPath();

	}

	public void remoteValueChanged(String propertyName, Object newValue) {
		bean.valueChange(propertyName, newValue);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		try {
			remote.setBeanValue(evt.getPropertyName(), evt.getNewValue());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Long getId() {
		return documentPath.getId();
	}

	public BeanEncapsulator getBean() {
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
