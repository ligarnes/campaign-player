package net.alteiar.server.document.map.element;

import java.awt.Point;

import net.alteiar.server.document.DocumentBuilder;

public abstract class DocumentMapElementBuilder extends DocumentBuilder {
	private static final long serialVersionUID = 1L;

	protected final Point position;

	public DocumentMapElementBuilder(Point position) {
		this.position = position;
	}
}
