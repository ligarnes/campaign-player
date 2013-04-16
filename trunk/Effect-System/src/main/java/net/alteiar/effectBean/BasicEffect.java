package net.alteiar.effectBean;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import net.alteiar.map.elements.CircleElement;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.shared.MyColor;
import net.alteiar.utils.map.element.MapElementSize;
import net.alteiar.utils.map.element.MapElementSizeSquare;

import pathfinder.character.PathfinderCharacter;
import pathfinder.mapElement.character.PathfinderCharacterElement;

public class BasicEffect extends Effect{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BasicEffect(Point position, Boolean oneUse) {
		super(position, oneUse);
		// TODO Auto-generated constructor stub
	}
	
	public BasicEffect(Point position,Color color, Boolean oneUse) {
		super(position, color,oneUse);
		
	}

	@Override
	public void activate(PathfinderCharacter c) {
	
		System.out.println("Basic effect activated");
	}

	
}
