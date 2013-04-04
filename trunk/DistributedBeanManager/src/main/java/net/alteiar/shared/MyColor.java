package net.alteiar.shared;

import java.awt.Color;

public class MyColor extends Color{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyColor() {
		super(0);
	}
	
	public MyColor(Color color)
	{
		super(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
	}

}
