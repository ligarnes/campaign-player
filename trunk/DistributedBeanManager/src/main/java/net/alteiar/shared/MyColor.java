package net.alteiar.shared;

import java.awt.Color;
import java.io.Serializable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public final class MyColor implements Serializable {
	@Attribute
	private static final long serialVersionUID = 1L;

	@Element
	private int r;
	@Element
	private int g;
	@Element
	private int b;
	@Element
	private int a;

	protected MyColor() {
		super();
		r = 0;
		g = 0;
		b = 0;
		a = 0;
	}

	public MyColor(Color color) {
		r = color.getRed();
		g = color.getGreen();
		b = color.getBlue();
		a = color.getAlpha();
	}

	public Color getColor() {
		return new Color(r, g, b, a);
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}
}
