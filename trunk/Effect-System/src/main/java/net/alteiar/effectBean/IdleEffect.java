package net.alteiar.effectBean;

import java.awt.Color;
import java.awt.Point;

import net.alteiar.client.DocumentClient;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.map.elements.ColoredShape;

import pathfinder.character.PathfinderCharacter;

public class IdleEffect extends Effect{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IdleEffect(ColoredShape shape, Boolean oneUse, Class<? extends BasicBeans> typeBean) throws ClassNotFoundException  {
		super(shape, oneUse,typeBean);
	}
	
	@Override
	public void activate() {
	}

	@Override
	public void desactivate() {
	}

	
}
