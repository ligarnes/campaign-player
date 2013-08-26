package net.alteiar.campaign.player.gui.centerViews.explorer;

import net.alteiar.documents.BeanBasicDocument;

public class DocumentAdapter {

	private final BeanBasicDocument doc;

	public DocumentAdapter(BeanBasicDocument doc) {
		this.doc = doc;
	}

	public BeanBasicDocument getDocument() {
		return this.doc;
	}

	@Override
	public String toString() {
		return doc.getDocumentName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((doc == null) ? 0 : doc.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentAdapter other = (DocumentAdapter) obj;
		if (doc == null) {
			if (other.doc != null)
				return false;
		} else if (!doc.equals(other.doc))
			return false;
		return true;
	}
}
