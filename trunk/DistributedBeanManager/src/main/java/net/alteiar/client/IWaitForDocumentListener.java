package net.alteiar.client;


public interface IWaitForDocumentListener {
	Long getDocument();

	void documentReceived(DocumentClient doc);
}
