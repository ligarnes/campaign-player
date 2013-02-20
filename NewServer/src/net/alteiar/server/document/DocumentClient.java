package net.alteiar.server.document;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.alteiar.client.BaseObservableClient;

public abstract class DocumentClient<E extends IDocumentRemote> extends
		BaseObservableClient {
	private static final long serialVersionUID = 1L;

	private E remote;
	private transient LocalDocumentListener documentListener;

	private Long guid;

	public DocumentClient(E remote) {
		try {
			this.remote = remote;
			guid = this.remote.getId();
		} catch (RemoteException e) {
			// TODO
			e.printStackTrace();
		}
	}

	public Long getId() {
		return guid;
	}

	protected final E getRemote() {
		return this.remote;
	}

	private final void remoteCloseDocument() {
		try {
			this.remote.removeDocumentListener(documentListener);
			closeDocument();
		} catch (RemoteException e) {
			// TODO
			e.printStackTrace();
		}
	}

	protected abstract void closeDocument() throws RemoteException;

	@Override
	public void initializeTransient() {
		super.initializeTransient();

		try {
			documentListener = new LocalDocumentListener();

			this.getRemote().addDocumentListener(documentListener);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class LocalDocumentListener extends UnicastRemoteObject implements
			IDocumentListener {
		private static final long serialVersionUID = 1L;

		protected LocalDocumentListener() throws RemoteException {
			super();
		}

		@Override
		public void documentClosed() throws RemoteException {
			remoteCloseDocument();
		}
	}
}
