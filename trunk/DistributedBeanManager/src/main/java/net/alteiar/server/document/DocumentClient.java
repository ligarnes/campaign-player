package net.alteiar.server.document;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.alteiar.client.bean.BasicBean;
import net.alteiar.client.bean.BeanEncapsulator;
import net.alteiar.shared.UniqueID;
import net.alteiar.thread.MyRunnable;
import net.alteiar.thread.ThreadPoolUtils;

import org.apache.log4j.Logger;

public class DocumentClient implements IDocumentClient, Serializable,
		PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private final IDocumentRemote remote;
	private transient IDocumentRemoteListener documentListener;

	private BeanEncapsulator bean;
	private String filename;

	public DocumentClient(IDocumentRemote remote) throws RemoteException {
		this.remote = remote;
		filename = remote.getFilename();
	}

	public void remoteValueChanged(String propertyName, Object newValue,
			Long timestamp) {
		bean.valueChange(propertyName, newValue, timestamp);
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		// Property change ask remote
		ThreadPoolUtils.getClientPool().execute(new MyRunnable() {
			@Override
			public void run() {
				try {
					remote.setBeanValue(evt.getPropertyName(),
							(Serializable) evt.getNewValue());
				} catch (RemoteException e) {
					Logger.getLogger(getClass()).error("Connexion perdu", e);
				}
			}

			@Override
			public String getTaskName() {
				return evt.getPropertyName() + " change task";
			}
		});

	}

	@Override
	public UniqueID getId() {
		return bean.getId();
	}

	@Override
	public BeanEncapsulator getBeanEncapsulator() {
		return this.bean;
	}

	protected void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public String getFilename() {
		return this.filename;
	}

	private final void remoteCloseDocument() {
		try {
			this.bean.beanRemoved();
			this.bean.removePropertyChangeListener(this);
			this.remote.removeDocumentListener(documentListener);
		} catch (RemoteException e) {
			Logger.getLogger(getClass()).error("Connexion perdu", e);
		}
	}

	@Override
	public void loadDocument(BasicBean bean) throws RemoteException {
		documentListener = new DocumentListener();

		this.remote.addDocumentListener(documentListener);

		this.bean = new BeanEncapsulator(bean);
		this.bean.addPropertyChangeListener(this);
	}

	@Override
	public void save(String path) throws Exception {
		DocumentIO.saveDocument(bean.getBean(), path, getFilename());
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
