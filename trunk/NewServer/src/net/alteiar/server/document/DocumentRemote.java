package net.alteiar.server.document;

import java.rmi.RemoteException;
import java.util.ArrayList;

import net.alteiar.server.BaseObservableRemote;

public abstract class DocumentRemote extends BaseObservableRemote implements
		IDocumentRemote {
	private static final long serialVersionUID = 1L;

	public DocumentRemote() throws RemoteException {
		super();
	}

	@Override
	public void closeDocument() throws RemoteException {
		ArrayList<IDocumentListener> listeners = this
				.getListener(IDocumentListener.class);

		// TODO do it in a task
		for (IDocumentListener listener : listeners) {
			listener.documentClosed();
		}
	}

	@Override
	public void addDocumentListener(IDocumentListener listener)
			throws RemoteException {
		this.addListener(IDocumentListener.class, listener);
	}

	@Override
	public void removeDocumentListener(IDocumentListener listener)
			throws RemoteException {
		this.removeListener(IDocumentListener.class, listener);
	}
}
