package net.alteiar.effectBean;

import java.awt.Color;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


import pathfinder.character.PathfinderCharacter;


public class ConditionalEffect extends EffectSuite implements PropertyChangeListener{

	private static final long serialVersionUID = 1L;
	
	public ConditionalEffect(Point position, Boolean oneUse) {
		super(position, oneUse);
	}
	
	public ConditionalEffect(Point position, Color color, Boolean oneUse) {
		super(position, color, oneUse);
	}
	
	public void activate(PathfinderCharacter c,Boolean isFullfilled)
	{
		if(isFullfilled)
		{
			effects.get(0).activate(c);
		}else
		{
			effects.get(1).activate(c);
		}
	}

	public void propertyChange(PropertyChangeEvent arg0) {
		ArrayList<Object> b=(ArrayList<Object>)arg0.getNewValue();
		PathfinderCharacter c=(PathfinderCharacter) b.get(0);
		Boolean fullfillment=(Boolean) b.get(1);
		this.activate(c,fullfillment);
	}

}
