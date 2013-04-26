package net.alteiar.effectBean;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.shared.UniqueID;

import pathfinder.character.PathfinderCharacter;

public class EffectSuite extends Effect{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	protected ArrayList<Effect> effects;
	

	public EffectSuite(ColoredShape shape, Boolean oneUse, Class<? extends BasicBeans> typeBean) throws ClassNotFoundException {
		super(shape, oneUse,typeBean);
		effects=new ArrayList<Effect>();
	}
	
	public void addEffect(Effect e)
	{
		effects.add(e);
	}
	
	public void removeEffect(Effect e)
	{
		effects.remove(e);
	}
	
	public void setMapId(UniqueID mapId)
	{
		super.setMapId(mapId);
		for(Effect e:effects)
		{
			e.setMapId(mapId);
		}
	}
	@Override
	public void activate() {
		for(Effect effect:effects)
		{
			effect.activate();
			if(effect.isOneUse())
			{
					effects.remove(effect);
			}
		}
	}

	
}
	
