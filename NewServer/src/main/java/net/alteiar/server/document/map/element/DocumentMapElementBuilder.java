package net.alteiar.server.document.map.element;

import java.awt.Point;

import net.alteiar.server.document.DocumentBuilder;

public abstract class DocumentMapElementBuilder extends DocumentBuilder {
	private static final long serialVersionUID = 1L;

	private final Long map;
	private final Point position;

	public DocumentMapElementBuilder(Long map, Point position) {
		this.position = position;
		this.map = map;
	}

	protected Point getPosition() {
		return position;
	}

	protected Long getMap() {
		return map;
	}
}
