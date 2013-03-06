package net.alteiar.server.document.map.element.colored;

import java.awt.Color;
import java.awt.Point;

import net.alteiar.server.document.map.element.DocumentMapElementBuilder;

public abstract class DocumentMapElementColoredBuilder extends
		DocumentMapElementBuilder {
	private static final long serialVersionUID = 1L;

	protected final Color color;

	public DocumentMapElementColoredBuilder(Long map, Point position,
			Color color) {
		super(map, position);
		this.color = color;
	}
}
