package net.alteiar.effectBean;

import java.awt.Color;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.map.elements.ColoredShape;


import pathfinder.character.PathfinderCharacter;


public class ConditionalEffect extends EffectSuite implements PropertyChangeListener{

	private static final long serialVersionUID = 1L;
	
	public ConditionalEffect(ColoredShape shape, Boolean oneUse, Class<? extends BasicBeans> typeBean) throws ClassNotFoundException {
		super(shape, oneUse,typeBean);
	}
	
	public void activate(Boolean isFullfilled)
	{
		if(isFullfilled)
		{
			effects.get(0).activate();
		}else
		{
			effects.get(1).activate();
		}
	}

	public void propertyChange(PropertyChangeEvent arg0) {
		Boolean fullfillment=(Boolean) arg0.getNewValue();
		this.activate(fullfillment);
	}

}
