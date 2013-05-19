package net.alteiar.server.document;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.alteiar.client.DocumentManager;
import net.alteiar.client.bean.BeanEncapsulator;
import net.alteiar.shared.UniqueID;

public class DocumentClient implements Serializable, PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private final IDocumentRemote remote;
	private final DocumentManager manager;
	private transient IDocumentRemoteListener documentListener;

	private BeanEncapsulator bean;
	private final String filename;

	public DocumentClient(IDocumentRemote remote, DocumentManager manager)
			throws RemoteException {

		this.manager = manager;
		this.remote = remote;
		this.filename = remote.getFilename();
	}

	public void remoteValueChanged(String propertyName, Object newValue,
			Long timestamp) {
		bean.valueChange(propertyName, newValue, timestamp);
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

	public String getFilename() {
		return this.filename;
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
		try {
			documentListener = new DocumentListener();

			this.remote.addDocumentListener(documentListener);

			File localFile = new File(manager.getSpecificPath(), filename);
			File globalFile = new File(manager.getGlobalPath(), filename);
			try {
				if (localFile.exists()) {
					// load local bean
					DocumentIO.loadDocumentLocal(localFile);
				} else if (globalFile.exists()) {
					// load global bean
					DocumentIO.loadDocumentLocal(globalFile);
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
		DocumentIO.saveDocument(bean.getBean(), manager.getSpecificPath(),
				this.filename + ".xml");
	}

	private class DocumentListener extends UnicastRemoteObject implements
			IDocumentRemoteListener {
		private static final long serialVersionUID = 1L;

		protected DocumentListener() throws RemoteException {
			super();
		}

		@Override
		public void beanValueChanged(String propertyName, Object newValue,
				long timestamp) throws RemoteException {
			remoteValueChanged(propertyName, newValue, timestamp);
		}

		@Override
		public void documentClosed() throws RemoteException {
			remoteCloseDocument();
		}
	}
}
