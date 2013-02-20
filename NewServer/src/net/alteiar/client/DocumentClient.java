package net.alteiar.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.alteiar.server.document.IDocumentListener;
import net.alteiar.server.document.IDocumentRemote;

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

		System.out.println("initialize transient DocumentClient");
		try {
			documentListener = new LocalDocumentListener();

			this.getRemote().addDocumentListener(documentListener);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class LocalDocumentListener extends IDocumentListener implements
			Remote {
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
