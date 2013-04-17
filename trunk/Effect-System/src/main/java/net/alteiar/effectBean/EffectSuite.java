package net.alteiar.effectBean;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;

import net.alteiar.shared.UniqueID;

import pathfinder.character.PathfinderCharacter;

public class EffectSuite extends Effect{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	protected ArrayList<Effect> effects;
	

	public EffectSuite(Point position, Boolean oneUse) {
		super(position, oneUse);
		effects=new ArrayList<Effect>();
	}
	
	public EffectSuite(Point position, Color color, Boolean oneUse) {
		super(position, color, oneUse);
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
	public void activate(PathfinderCharacter c) {
		for(Effect effect:effects)
		{
			effect.activate(c);
			if(effect.isOneUse())
			{
					effects.remove(effect);
			}
		}
	}
}
	
