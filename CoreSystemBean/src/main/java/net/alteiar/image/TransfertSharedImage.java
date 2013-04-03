package net.alteiar.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.alteiar.documents.image.DocumentImageBean;
import net.alteiar.shared.UniqueID;
import net.alteiar.utils.images.TransfertImage;

public class TransfertSharedImage implements TransfertImage {
	private static final long serialVersionUID = 1L;

	private UniqueID documentImageId;

	public UniqueID getDocumentImageId() {
		return documentImageId;
	}

	public void setDocumentImageId(UniqueID documentImageId) {
		this.documentImageId = documentImageId;
	}

	@Override
	public BufferedImage restoreImage() throws IOException {
		return DocumentImageBean.getDocumentImage(documentImageId);
	}
}
