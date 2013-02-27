package net.alteiar.server.document.map.element;

import java.awt.Point;

import net.alteiar.server.document.DocumentBuilder;

public abstract class DocumentMapElementBuilder extends DocumentBuilder {
	private static final long serialVersionUID = 1L;

	protected final Point position;
	protected final Double angle;

	public DocumentMapElementBuilder(Point position, Double angle) {
		this.position = position;
		this.angle = angle;
	}
}
