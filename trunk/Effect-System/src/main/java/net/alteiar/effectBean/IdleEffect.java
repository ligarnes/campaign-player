package net.alteiar.effectBean;

import java.awt.Color;
import java.awt.Point;

import pathfinder.character.PathfinderCharacter;

public class IdleEffect extends Effect{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IdleEffect(Point position, Boolean oneUse) {
		super(position, oneUse);
	}
	
	public IdleEffect(Point position, Color color, Boolean oneUse) {
		super(position, color, oneUse);
	}
	
	@Override
	public void activate(PathfinderCharacter c) {
	}
}
