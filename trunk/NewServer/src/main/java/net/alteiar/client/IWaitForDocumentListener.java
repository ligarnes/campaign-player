package net.alteiar.client;

import net.alteiar.server.document.DocumentClient;

public interface IWaitForDocumentListener {
	Long getDocument();

	void documentReceived(DocumentClient<?> doc);
}
