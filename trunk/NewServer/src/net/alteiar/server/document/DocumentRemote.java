package net.alteiar.server.document;

import java.rmi.RemoteException;
import java.util.ArrayList;

import net.alteiar.server.BaseObservableRemote;

public abstract class DocumentRemote extends BaseObservableRemote implements
		IDocumentRemote {
	private static final long serialVersionUID = 1L;

	private final DocumentPath path;

	public DocumentRemote() throws RemoteException {
		super();
		this.path = new DocumentPath("", "");
	}

	@Override
	public DocumentPath getPath() {
		return path;
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

	// LISTENERS METHODS
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentRemote other = (DocumentRemote) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
}
