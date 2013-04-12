package net.alteiar.shared;

import java.awt.Color;

import org.simpleframework.xml.Attribute;

public final class MyColor extends Color {
	@Attribute
	private static final long serialVersionUID = 1L;

	public MyColor() {
		super(0);
	}

	public MyColor(Color color) {
		super(color.getRed(), color.getGreen(), color.getBlue(), color
				.getAlpha());
	}

}
