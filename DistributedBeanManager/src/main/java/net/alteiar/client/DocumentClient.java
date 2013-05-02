package net.alteiar.client;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.alteiar.client.bean.BeanEncapsulator;
import net.alteiar.server.document.DocumentLoader;
import net.alteiar.server.document.DocumentPath;
import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.server.document.IDocumentRemoteListener;
import net.alteiar.shared.UniqueID;

public class DocumentClient implements Serializable, PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private final IDocumentRemote remote;
	private transient IDocumentRemoteListener documentListener;

	private BeanEncapsulator bean;
	private final DocumentPath path;

	public DocumentClient(IDocumentRemote remote) throws RemoteException {
		this.remote = remote;
		this.path = remote.getPath();
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

	public UniqueID getId() {
		return bean.getId();
	}

	public BeanEncapsulator getBeanEncapsulator() {
		return this.bean;
	}

	public DocumentPath getDocumentPath() {
		return this.path;
	}

	private final void remoteCloseDocument() {
		try {
			this.bean.beanRemoved();
			this.bean.removePropertyChangeListener(this);
			this.remote.removeDocumentListener(documentListener);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadDocument() throws Exception {
		DocumentPath documentPath = null;
		try {
			documentListener = new DocumentListener();

			this.remote.addDocumentListener(documentListener);
			documentPath = remote.getPath();

			File localFile = new File(documentPath.getCompletePath());
			try {
				if (localFile.exists()) {
					// load local bean
					DocumentLoader.loadDocumentLocal(localFile);
				} else {
					loadDocumentRemote();
				}
				bean.addPropertyChangeListener(this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void loadDocumentRemote() throws Exception {
		bean = new BeanEncapsulator(remote.getBean());
	}

	public void saveLocal() throws Exception {
		DocumentLoader.SaveDocument(bean.getBean(), this.path.getPath(),
				this.path.getName() + ".xml");
	}

	private class DocumentListener extends UnicastRemoteObject implements
			IDocumentRemoteListener {
		private static final long serialVersionUID = 1L;

		protected DocumentListener() throws RemoteException {
			super();
		}

		@Override
		public void beanValueChanged(String propertyName, Object newValue)
				throws RemoteException {
			remoteValueChanged(propertyName, newValue);
		}

		@Override
		public void documentClosed() throws RemoteException {
			remoteCloseDocument();
		}
	}
}
