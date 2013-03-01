package net.alteiar.server.document.map;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import net.alteiar.server.document.DocumentBuilder;
import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.server.document.files.DocumentImageBuilder;
import net.alteiar.server.document.images.TransfertImage;
import net.alteiar.server.document.map.filter.DocumentMapFilterBuilder;

public abstract class DocumentMapBuilder extends DocumentBuilder {
	private static final long serialVersionUID = 1L;

	protected final String mapName;
	protected final TransfertImage image;
	protected final Scale scale;

	protected transient Long imageId;
	protected transient Long filterId;
	protected transient int width;
	protected transient int height;

	public DocumentMapBuilder(String mapName, TransfertImage image, Scale scale) {
		this.mapName = mapName;
		this.image = image;
		this.scale = scale;
	}

	@Override
	public List<IDocumentRemote> buildDocuments() throws RemoteException {
		List<IDocumentRemote> documents = super.buildDocuments();

		BufferedImage imgs;
		try {
			imgs = image.restoreImage();

			width = imgs.getWidth();
			height = imgs.getHeight();

			DocumentMapFilterBuilder filterBuilder = new DocumentMapFilterBuilder(
					width, height);
			DocumentImageBuilder imageBuilder = new DocumentImageBuilder(image);

			documents.addAll(imageBuilder.buildDocuments());
			IDocumentRemote img = imageBuilder.buildMainDocument();
			imageId = img.getPath().getId();
			documents.add(img);

			documents.addAll(filterBuilder.buildDocuments());
			IDocumentRemote filter = filterBuilder.buildMainDocument();
			filterId = filter.getPath().getId();
			documents.add(filter);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return documents;
	}
}
