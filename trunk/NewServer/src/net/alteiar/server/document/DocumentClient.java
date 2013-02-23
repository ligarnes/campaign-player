package net.alteiar.server.document;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.alteiar.client.BaseObservableClient;

public abstract class DocumentClient<E extends IDocumentRemote> extends
		BaseObservableClient {
	private static final long serialVersionUID = 1L;

	private E remote;
	private transient LocalDocumentListener documentListener;

	private DocumentPath documentPath;

	public DocumentClient(E remote) {
		try {
			this.remote = remote;
			this.documentPath = this.remote.getPath();
		} catch (RemoteException e) {
			// TODO
			e.printStackTrace();
		}
	}

	public Long getId() {
		return documentPath.getId();
	}

	protected DocumentPath getDocumentPath() {
		return this.documentPath;
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
	public void loadDocument() {
		super.loadDocument();
		try {
			documentListener = new LocalDocumentListener();

			this.getRemote().addDocumentListener(documentListener);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// localPath = the local scenario directory
		// path = localPath + getDocumentPath().getCompletePath();
		File localFile = new File(getDocumentPath().getCompletePath());
		try {
			if (localFile.exists()) {
				loadDocumentLocal(localFile);
			} else {
				loadDocumentRemote();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected abstract void loadDocumentLocal(File f) throws IOException;

	protected abstract void loadDocumentRemote() throws IOException;

	public void saveDocument() {
		// localPath = the local scenario directory
		// path = localPath + getDocumentPath().getCompletePath();
	}

	// protected abstract void saveLocal(File localFile);

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
