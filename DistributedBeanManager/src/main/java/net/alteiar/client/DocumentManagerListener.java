package net.alteiar.client;

public interface DocumentManagerListener {
	void documentAdded(DocumentClient document);

	void documentRemoved(DocumentClient document);
}
