package net.alteiar.server.document.map.element.colored;

import java.awt.Color;
import java.awt.Point;

import net.alteiar.server.document.map.element.DocumentMapElementBuilder;

public abstract class DocumentMapElementColoredBuilder extends
		DocumentMapElementBuilder {
	private static final long serialVersionUID = 1L;

	protected final Color color;

	public DocumentMapElementColoredBuilder(Point position,
			Color color) {
		super(position);
		this.color = color;
	}
}
