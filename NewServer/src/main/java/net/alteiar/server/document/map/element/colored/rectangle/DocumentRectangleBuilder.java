package net.alteiar.server.document.map.element.colored.rectangle;

import java.awt.Color;
import java.awt.Point;
import java.rmi.RemoteException;

import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.server.document.map.element.colored.DocumentMapElementColoredBuilder;
import net.alteiar.server.document.map.element.size.MapElementSize;

public  class DocumentRectangleBuilder extends
		DocumentMapElementColoredBuilder {
	private static final long serialVersionUID = 1L;

	protected final MapElementSize width;
	protected final MapElementSize height;
 
	public DocumentRectangleBuilder(Point position,
			Color color, MapElementSize width, MapElementSize height) {
		super(position, color);
		this.width = width;
		this.height = height;
	}

	@Override
	public IDocumentRemote buildMainDocument() throws RemoteException {
		return new RectangleRemote(position, color, width, height); 
	}
}
